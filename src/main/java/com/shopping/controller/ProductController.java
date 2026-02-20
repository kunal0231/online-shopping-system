package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.ProductRequestDTO;
import com.shopping.dto.ProductResponseDTO;
import com.shopping.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/add/product")
	public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO request) {

		return ResponseEntity.ok(productService.createProduct(request));
	}

	@PostMapping("/add/products")
	public ResponseEntity<List<ProductResponseDTO>> createProducts(
			@Valid @RequestBody List<ProductRequestDTO> requests) {

		return ResponseEntity.ok(productService.createProducts(requests));
	}

	@GetMapping("/get/all/products")
	public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/getby/{id}")
	public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {

		return ResponseEntity.ok(productService.getProductById(id));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable String id,
			@Valid @RequestBody ProductRequestDTO request) {

		return ResponseEntity.ok(productService.updateProduct(id, request));
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);
		return ResponseEntity.ok("Product deleted successfully");
	}
}
