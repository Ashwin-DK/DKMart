package com.ashwin.dkmart.Repository;

import com.ashwin.dkmart.Entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String category);

	boolean existsByName(String name);

	

}
