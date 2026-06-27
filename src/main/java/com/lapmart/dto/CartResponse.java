package com.lapmart.dto;

import java.math.BigDecimal;
import java.util.List;


public class CartResponse {
    private Long cartId;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;   // sum of all subtotals
    private Integer totalItems;       // total number of items
    
    public CartResponse() {
	}

	public CartResponse(Long cartId, List<CartItemResponse> items, BigDecimal totalAmount, Integer totalItems) {
		super();
		this.cartId = cartId;
		this.items = items;
		this.totalAmount = totalAmount;
		this.totalItems = totalItems;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public List<CartItemResponse> getItems() {
		return items;
	}

	public void setItems(List<CartItemResponse> items) {
		this.items = items;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
}