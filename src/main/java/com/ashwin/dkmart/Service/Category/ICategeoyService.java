package com.ashwin.dkmart.Service.Category;

import java.util.List;

import com.ashwin.dkmart.Entity.Category;

public interface ICategeoyService {

	Category addCategory(Category name);
	Category updateCategory(Category category, Long id);
	Category getByCategoryId(Long CategoryID);
	Category getByCategoryName(String name);
	List<Category> getAllCategories();
	void deleteCategoryById(Long Id);
	
	 
}
