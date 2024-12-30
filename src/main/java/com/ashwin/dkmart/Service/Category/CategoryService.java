package com.ashwin.dkmart.Service.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashwin.dkmart.Entity.Category;
import com.ashwin.dkmart.Exception.AlreadyExisting;
import com.ashwin.dkmart.Exception.ResourseNotFound;
import com.ashwin.dkmart.Repository.CategoryRepository;

@Service
public class CategoryService implements ICategeoyService{

	@Autowired 
	CategoryRepository categoryRepository;
	
	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return Optional.of(category).filter(v -> !categoryRepository.existsByName(v.getName()))
				.map(categoryRepository::save)
				.orElseThrow(() -> new AlreadyExisting(category.getName() +" already exists"));
	}

	@Override
	public Category updateCategory(Category category, Long Id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(getByCategoryId(Id))
				.map(oldcategory -> {oldcategory.setName(category.getName()); 
									 return categoryRepository.save(oldcategory); })
				.orElseThrow(() -> new ResourseNotFound("Resource Not Found!"));
				
				
				
		// will work but will check with utube code
				//		categoryRepository.findById(Id).map(categoryRepository::save).orElseThrow(() -> new ResourseNotFound("Resoures Not Found!"));
	}

	@Override
	public Category getByCategoryId(Long CategoryID) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(CategoryID).orElseThrow(() -> new ResourseNotFound("Resource Not Found!"));
	}

	@Override
	public Category getByCategoryName(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public void deleteCategoryById(Long Id) {
		// TODO Auto-generated method stub
		categoryRepository.findById(Id)
			.ifPresentOrElse(categoryRepository::delete, () -> new ResourseNotFound("Resource Not Found!"));
			
		
	}


}
