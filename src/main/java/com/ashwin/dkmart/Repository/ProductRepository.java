package com.ashwin.dkmart.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashwin.dkmart.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByName(String name);

	List<Product> findByBrandAndCategoryName(String brand, String category);

	Long countByBrandAndName(String brand, String name);




}
