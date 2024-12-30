package com.ashwin.dkmart.Controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashwin.dkmart.DTO.ProductDto;
import com.ashwin.dkmart.Entity.Product;
import com.ashwin.dkmart.Exception.ProductNotFoundException;
import com.ashwin.dkmart.Model.AddProductModel;
import com.ashwin.dkmart.Model.UpdateProductModel;
import com.ashwin.dkmart.Response.ApiResponse;
import com.ashwin.dkmart.Service.Product.IProductService;

@RestController
@RequestMapping("${api.prefix}/products")

public class ProductController {
	
	@Autowired
	private IProductService iProductService;
	
	


	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> products = iProductService.getAllProducts();
		List<ProductDto> productDtos = iProductService.getConvertedProducts(products);
		return ResponseEntity.ok(new ApiResponse("Sccuess", productDtos));
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductModel product){
		try {
			Product product2 = iProductService.addProduct(product);
			ProductDto productDto = iProductService.convertToDto(product2);
			return ResponseEntity.ok(new ApiResponse("Add Product Success", productDto));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@PutMapping("/{Id}/update")
	public ResponseEntity<ApiResponse> updateProduct(UpdateProductModel product, Long Id){
		try {
			Product updateProduct = iProductService.updateProduct(product, Id);
			ProductDto productDto = iProductService.convertToDto(updateProduct);
			return ResponseEntity.ok(new ApiResponse("Update Product Success", productDto));

		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/delete/{Id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long Id){
		try {
			iProductService.deleteProductById(Id);
			return ResponseEntity.ok(new ApiResponse("Delete Product Success", Id));

		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/by/Brand-and-CategoryName")
	public ResponseEntity<ApiResponse> byBrandandCategory(@RequestParam String Brand, @RequestParam String CategoryName){
		try {
			List<Product> productdata = iProductService.getProductByBrandAndCategory(Brand, CategoryName);
			if(productdata.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> productDtos = iProductService.getConvertedProducts(productdata);
			return ResponseEntity.ok(new ApiResponse("Success", productDtos));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error", e.getMessage()));
		}
		
	}
	
	@GetMapping("/by-brand/and-CategoryName")
	public ResponseEntity<ApiResponse> getcountProductsByBrandAndName(@RequestParam String brand, @RequestParam String CategoryName){
		try {
			Long productdata = iProductService.getProductCountByBrandAndName(brand, CategoryName);
			return ResponseEntity.ok(new ApiResponse("Success", productdata));

		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
		}
		
	}
	
	
	@GetMapping("/product/{Name}/products")
	public ResponseEntity<ApiResponse>  getProductByName(@PathVariable String Name) {
		try {
			List<Product> product = iProductService.getProductByName(Name);
			if(product.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> productDtos = iProductService.getConvertedProducts(product);
			return ResponseEntity.ok(new ApiResponse("Found", productDtos));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/by-brand")
	public ResponseEntity<ApiResponse>  getProductByBrand(@RequestParam String brand) {
		try {
			List<Product> product = iProductService.getProductByBrand(brand);
			if(product.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> productDtos = iProductService.getConvertedProducts(product);
			return ResponseEntity.ok(new ApiResponse("Found", productDtos));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/product/{CategoryName}/all/products")
	public ResponseEntity<ApiResponse>  getProductByCategory(@PathVariable String CategoryName) {
		try {
			List<Product> product = iProductService.getProductByCategory(CategoryName);
			if(product.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> productDtos = iProductService.getConvertedProducts(product);
			return ResponseEntity.ok(new ApiResponse("Success", productDtos));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	
	@GetMapping("/{Id}/product")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long Id) {
		try {
			Product product = iProductService.productById(Id);
			ProductDto productDto = iProductService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Found", productDto));

		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
			
		}
	}

	
	

}
