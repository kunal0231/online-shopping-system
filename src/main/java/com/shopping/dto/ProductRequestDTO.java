package com.shopping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {

	@NotBlank
	private String name;

	private String description;

	@NotNull
	@Positive
	private Double price;

	@NotNull
	@Positive
	private Integer stock;

	public ProductRequestDTO(@NotBlank String name, String description, @NotNull @Positive Double price,
			@NotNull @Positive Integer stock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

}
