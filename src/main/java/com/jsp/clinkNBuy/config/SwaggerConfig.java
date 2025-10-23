package com.jsp.clinkNBuy.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "clickNBuy", version = "0.0.1", description = "A REST API for the ecommerce project named ClickNBuy having three modules: Admin, Seller, and Customer."), security = {
		@SecurityRequirement(name = "bearerAuth") })
@SecurityScheme(name = "bearerAuth", description = "JWT Authorization header using the Bearer scheme. Example: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfig {

}
