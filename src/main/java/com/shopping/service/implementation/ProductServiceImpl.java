package com.shopping.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.dto.ProductRequestDTO;
import com.shopping.dto.ProductResponseDTO;
import com.shopping.entity.Product;
import com.shopping.exception.EmptyDtoException;
import com.shopping.repository.ProductRepository;
import com.shopping.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	// =========================
	// CREATE PRODUCT (ADMIN)
	// =========================
	@Override
	public ProductResponseDTO createProduct(ProductRequestDTO request) {

		if (request == null) {
			throw new EmptyDtoException();
		}

		Product product = new Product();

		// -------- Generate Custom Product ID (PROD001) --------
		Optional<Product> lastProduct = productRepository.findTopByOrderByIdDesc();

		int nextNum = 1;

		if (lastProduct.isPresent() && lastProduct.get().getProductId() != null) {
			String lastId = lastProduct.get().getProductId(); // PROD005
			int lastNum = Integer.parseInt(lastId.replace("PROD", ""));
			nextNum = lastNum + 1;
		}

		String newProductId = String.format("PROD%03d", nextNum);

		// -------- Set Data --------
		product.setProductId(newProductId);
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setActive(true);

		Product savedProduct = productRepository.save(product);

		return mapToResponse(savedProduct);
	}

	@Override
	public List<ProductResponseDTO> createProducts(List<ProductRequestDTO> requests) {

		if (requests == null || requests.isEmpty()) {
			throw new EmptyDtoException();
		}

		Optional<Product> lastProduct = productRepository.findTopByOrderByIdDesc();

		int nextNum = 1;

		if (lastProduct.isPresent() && lastProduct.get().getProductId() != null) {
			String lastId = lastProduct.get().getProductId();
			int lastNum = Integer.parseInt(lastId.replace("PROD", ""));
			nextNum = lastNum + 1;
		}

		int counter = nextNum;

		List<Product> products = new java.util.ArrayList<>();

		for (ProductRequestDTO request : requests) {

			Product product = new Product();

			String newProductId = String.format("PROD%03d", counter++);
			product.setProductId(newProductId);

			product.setName(request.getName());
			product.setDescription(request.getDescription());
			product.setPrice(request.getPrice());
			product.setStock(request.getStock());
			product.setActive(true);

			products.add(product);
		}

		List<Product> savedProducts = productRepository.saveAll(products);

		return savedProducts.stream().map(this::mapToResponse).toList();
	}

	// =========================
	// GET ALL ACTIVE PRODUCTS
	// =========================
	@Override
	@Transactional(readOnly = true)
	public List<ProductResponseDTO> getAllProducts() {

		return productRepository.findByActiveTrue().stream().map(this::mapToResponse).toList();
	}

	// =========================
	// GET PRODUCT BY ID
	// =========================
	@Override
	@Transactional(readOnly = true)
	public ProductResponseDTO getProductById(Long id) {

		Product product = productRepository.findById(id).filter(Product::getActive)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		return mapToResponse(product);
	}

	// =========================
	// UPDATE PRODUCT
	// =========================
	@Override
	public ProductResponseDTO updateProduct(String id, ProductRequestDTO request) {

		Optional<Product> byProductId = Optional.ofNullable(
				productRepository.findByProductId(id).orElseThrow(() -> new RuntimeException("Product not found")));
//		Product  = productRepository.findByProductId(id)
//				.orElseThrow(() -> new RuntimeException("Product not found"));
		Product existing;
		if (byProductId.isPresent()) {
			existing = byProductId.get();
			existing.setName(request.getName());
			existing.setDescription(request.getDescription());
			existing.setPrice(request.getPrice());
			existing.setStock(request.getStock());
			Product updated = productRepository.save(existing);
			return mapToResponse(updated);
		}
		return new ProductResponseDTO("NO PRODUCT AVAILABLE WITH ID::" + id);

	}

	// =========================
	// SOFT DELETE PRODUCT
	// =========================
	@Override
	public void deleteProduct(Long id) {

		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

		product.setActive(false);

		productRepository.save(product);
	}

	// =========================
	// ENTITY → RESPONSE DTO
	// =========================
	private ProductResponseDTO mapToResponse(Product product) {

		ProductResponseDTO dto = new ProductResponseDTO();

		dto.setProductId(product.getProductId());
		dto.setProductId(product.getProductId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());

		return dto;
	}
}
