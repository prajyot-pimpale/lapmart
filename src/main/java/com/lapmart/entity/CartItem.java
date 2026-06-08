package com.lapmart.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_id", nullable = false)
	private Laptop laptop;
	
    @Column(nullable = false)
	private Integer quantity;
	
	public CartItem() {
		// TODO Auto-generated constructor stub
	}

	public CartItem(Cart cart, Laptop laptop, Integer quantity) {
		super();
		this.cart = cart;
		this.laptop = laptop;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Laptop getLaptop() {
		return laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
