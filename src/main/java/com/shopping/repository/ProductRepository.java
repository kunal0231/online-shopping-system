package com.shopping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByActiveTrue();

	Optional<Product> findTopByOrderByIdDesc();

	Optional<Product> findByProductId(String id);

}
