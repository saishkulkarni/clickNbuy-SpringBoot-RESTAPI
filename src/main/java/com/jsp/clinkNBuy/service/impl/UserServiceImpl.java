package com.jsp.clinkNBuy.service.impl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jsp.clinkNBuy.dao.CartItemDao;
import com.jsp.clinkNBuy.dao.OrderDao;
import com.jsp.clinkNBuy.dao.ProductDao;
import com.jsp.clinkNBuy.dao.UserDao;
import com.jsp.clinkNBuy.dto.AddressDto;
import com.jsp.clinkNBuy.dto.CartItemDto;
import com.jsp.clinkNBuy.dto.ResponseDto;
import com.jsp.clinkNBuy.entity.Address;
import com.jsp.clinkNBuy.entity.CartItem;
import com.jsp.clinkNBuy.entity.OrderItem;
import com.jsp.clinkNBuy.entity.Product;
import com.jsp.clinkNBuy.entity.User;
import com.jsp.clinkNBuy.entity.UserOrder;
import com.jsp.clinkNBuy.exception.DataNotFoundException;
import com.jsp.clinkNBuy.exception.OutOfStockException;
import com.jsp.clinkNBuy.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	ProductDao productDao;
	UserDao userDao;
	CartItemDao itemDao;
	OrderDao orderDao;

	public UserServiceImpl(ProductDao productDao, UserDao userDao, CartItemDao itemDao, OrderDao orderDao) {
		super();
		this.productDao = productDao;
		this.userDao = userDao;
		this.itemDao = itemDao;
		this.orderDao = orderDao;
	}

	@Value("${razorpay_key}")
	private String key;
	@Value("${razorpay_secret}")
	private String secret;

	@Override
	public ResponseDto getAllApprovedProducts(int page, int size, String sortBy, boolean desc) {
		Sort sort = desc ? Sort.by(sortBy).descending() : Sort.by(sortBy);
		Pageable pageable = PageRequest.of(page - 1, size, sort);
		List<Product> products = productDao.findApprovedProducts(pageable);
		return new ResponseDto("Products Found", products);
	}

	@Override
	public ResponseDto addProductToCart(CartItemDto dto, Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		Product product = productDao.findProductByIdAndApprovedTrue(dto.getId());
		if (dto.getQuantity() > product.getStock())
			throw new OutOfStockException(product.getName() + " is Not in Stock");
		else {
			CartItem exItem = itemDao.getIfExists(user, product);
			if (exItem != null) {
				exItem.setQuantity(exItem.getQuantity() + dto.getQuantity());
				itemDao.save(exItem);
				return new ResponseDto("Item Increased Success", exItem);
			} else {
				CartItem item = new CartItem(null, product, dto.getQuantity(), user);
				itemDao.save(item);
				return new ResponseDto("Item Added Success", item);
			}
		}
	}

	@Override
	public ResponseDto removeFromCart(CartItemDto dto, Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		Product product = productDao.findProductByIdAndApprovedTrue(dto.getId());
		CartItem exItem = itemDao.getIfExists(user, product);
		if (exItem == null)
			throw new DataNotFoundException("Item is Not Present in Cart");
		else {
			if (exItem.getQuantity() < dto.getQuantity())
				throw new DataNotFoundException("In Cart only " + exItem.getQuantity() + " item is there");
			else {
				if (exItem.getQuantity() == dto.getQuantity()) {
					itemDao.deleteById(exItem.getId());
					return new ResponseDto("Item Removed Success", exItem);
				} else {
					exItem.setQuantity(exItem.getQuantity() - dto.getQuantity());
					itemDao.save(exItem);
					return new ResponseDto("Item Removed Success", exItem);
				}
			}
		}
	}

	@Override
	public ResponseDto viewCart(Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		List<CartItem> items = itemDao.getCartItems(user);
		return new ResponseDto("Data Found", items);
	}

	@Override
	@Transactional
	public ResponseDto createOrder(AddressDto dto, Principal principal) throws RazorpayException {
		User user = userDao.findByEmail(principal.getName());
		List<CartItem> items = itemDao.getCartItems(user);

		for (CartItem cartItem : items) {
			if (cartItem.getQuantity() > cartItem.getProduct().getStock()) {
				throw new OutOfStockException("Out of Stock");
			}
		}

		double total = items.stream().map(x -> x.getProduct().getPrice() * x.getQuantity()).reduce(0.0, Double::sum);

		UserOrder userOrder = new UserOrder();
		userOrder.setAddress(new Address(null, dto.getHouseNo(), dto.getLandmark(), dto.getStreet(), dto.getArea(),
				dto.getCity(), dto.getPincode()));
		userOrder.setPaymentStatus("pending");
		userOrder.setTotalCost(total);
		userOrder.setUser(user);
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (CartItem item : items) {
			OrderItem orderItem = new OrderItem(null, item.getProduct(), item.getQuantity(), item.getUser());
			orderItems.add(orderItem);
		}
		userOrder.setItems(orderItems);

		RazorpayClient razorpay = new RazorpayClient(key, secret);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", (int) (total * 100));
		orderRequest.put("currency", "INR");

		Order order = razorpay.orders.create(orderRequest);
		userOrder.setOrderId(order.get("id"));

		orderDao.save(userOrder);
		return new ResponseDto("Order Placed, Waiting for Payment", userOrder);
	}

	@Override
	public ResponseDto confirmOrder(String razorpay_order_id, String razorpay_payment_id) {
		UserOrder order = orderDao.findByOrderId(razorpay_order_id);
		order.setPaymentId(razorpay_payment_id);
		order.setPaymentStatus("success");
		orderDao.save(order);

		List<CartItem> items = itemDao.getCartItems(order.getUser());
		items.forEach(x -> {
			Product p = x.getProduct();
			p.setStock(p.getStock() - x.getQuantity());
			productDao.saveProduct(p);
		});

		itemDao.deleteAll(items);

		return new ResponseDto("Payment Success, Order Placed", order);
	}

	@Override
	public ResponseDto fetchAllOrders(Principal principal) {
		User user = userDao.findByEmail(principal.getName());
		List<UserOrder> orders = orderDao.fetchAllOrders(user);
		return new ResponseDto("Orders Found", orders);
	}

}
