package com.lapmart.dto;

import java.math.BigDecimal;

public class CartItemResponse{
    private Long cartItemId;
    private Long laptopId;
    private String laptopName;
    private String brand;
    private BigDecimal price;        // price per unit
    private Integer quantity;
    private BigDecimal subtotal;     // price × quantity
    private String imageUrl;
    
    public CartItemResponse() {
	}

	public CartItemResponse(Long cartItemId, Long laptopId, String laptopName, String brand, BigDecimal price,
			Integer quantity, BigDecimal subtotal, String imageUrl) {
		super();
		this.cartItemId = cartItemId;
		this.laptopId = laptopId;
		this.laptopName = laptopName;
		this.brand = brand;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.imageUrl = imageUrl;
	}

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Long getLaptopId() {
		return laptopId;
	}

	public void setLaptopId(Long laptopId) {
		this.laptopId = laptopId;
	}

	public String getLaptopName() {
		return laptopName;
	}

	public void setLaptopName(String laptopName) {
		this.laptopName = laptopName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
