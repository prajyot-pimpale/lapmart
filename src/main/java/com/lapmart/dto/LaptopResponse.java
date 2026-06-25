package com.lapmart.dto;

import java.math.BigDecimal;

public class LaptopResponse {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private String categoryName; // just the name, not the whole Category object
    
	public LaptopResponse() {
		// TODO Auto-generated constructor stub
	}    
    
	public LaptopResponse(Long id, String name, String brand, BigDecimal price, Integer stock, String description,
			String imageUrl, String categoryName) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
		this.description = description;
		this.imageUrl = imageUrl;
		this.categoryName = categoryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}