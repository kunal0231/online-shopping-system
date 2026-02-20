package com.shopping.service;

import java.util.List;

import com.shopping.dto.ProductRequestDTO;
import com.shopping.dto.ProductResponseDTO;

public interface ProductService {

	ProductResponseDTO createProduct(ProductRequestDTO request);

	List<ProductResponseDTO> createProducts(List<ProductRequestDTO> requests);

	List<ProductResponseDTO> getAllProducts();

	ProductResponseDTO getProductById(Long id);

	ProductResponseDTO updateProduct(String id, ProductRequestDTO request);

	void deleteProduct(Long id);
}
