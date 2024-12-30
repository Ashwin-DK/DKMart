package com.ashwin.dkmart.Service.Product;

import java.util.List;
import java.util.Optional;

import com.ashwin.dkmart.DTO.ProductDto;
import com.ashwin.dkmart.Entity.Product;
import com.ashwin.dkmart.Model.AddProductModel;
import com.ashwin.dkmart.Model.UpdateProductModel;

public interface IProductService {

	Product addProduct(AddProductModel addProductModel);
	Product updateProduct(UpdateProductModel product, Long Id);
	Long getProductCountByBrandAndName(String brand, String name);
	Product productById(Long Id);
	void deleteProductById(Long Id);
	List<Product> getProductByBrand(String brand);
	List<Product> getProductByCategory(String category);
	List<Product> getAllProducts();
	List<Product> getProductByBrandAndCategory(String brand,String category);
	List<Product> getProductByName(String name);
	List<ProductDto> getConvertedProducts(List<Product> products);
	ProductDto convertToDto(Product product);
	
	
	
	
	
	
	
	
	
	
	
}
