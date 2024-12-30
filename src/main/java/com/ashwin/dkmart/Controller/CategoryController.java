package com.ashwin.dkmart.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.dkmart.Entity.Category;
import com.ashwin.dkmart.Exception.AlreadyExisting;
import com.ashwin.dkmart.Exception.ResourseNotFound;
import com.ashwin.dkmart.Response.ApiResponse;
import com.ashwin.dkmart.Service.Category.ICategeoyService;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

	@Autowired
	private ICategeoyService iCategeoyService;
	
	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getCategories(){
		try {

			List<Category> category = iCategeoyService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Success!", category));
		}catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
		@GetMapping("/{Id}/Categories")
		public ResponseEntity<ApiResponse> getCategoryById(Long Id){
			try {
				Category category = iCategeoyService.getByCategoryId(Id);
				return ResponseEntity.ok(new ApiResponse("Found", category));

			}catch (ResourseNotFound e) {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
			}
		}
	
		 @GetMapping("/category/{name}/category")
		 public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		        try { 

		        	Category theCategory = iCategeoyService.getByCategoryName(name);
		            return  ResponseEntity.ok(new ApiResponse("Found", theCategory));

		        } catch (ResourseNotFound e) {

		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		        }
		    }
		 
		 @PostMapping("/add")
		 public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {
		        try {
		        	Category theCategory = iCategeoyService.addCategory(name);
		            return  ResponseEntity.ok(new ApiResponse("Success", theCategory));

		        } catch (AlreadyExisting e) {
		           return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));

		        }
		    }
	
		 @DeleteMapping("/category/{id}/delete")
		 public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
		        try {
		        	iCategeoyService.deleteCategoryById(id);
		            return  ResponseEntity.ok(new ApiResponse("Found", null));

		        } catch (ResourseNotFound e) {
		            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		        }
		    }

	    @PutMapping("/category/{id}/update")
	    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
	        try {
	        	Category updatedCategory = iCategeoyService.updateCategory(category, id);
	            return ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));

	        } catch (ResourseNotFound e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
				
	        }
	    }
	
	
}
