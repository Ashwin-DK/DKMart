package com.ashwin.dkmart.Service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ashwin.dkmart.Entity.Category;
import com.ashwin.dkmart.Entity.Product;
import com.ashwin.dkmart.Model.AddProductModel;
import com.ashwin.dkmart.Repository.CategoryRepository;
import com.ashwin.dkmart.Repository.ProductRepository;
import com.ashwin.dkmart.Service.Product.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
	
	@InjectMocks
	ProductService productService;

	@Mock
	ProductRepository productRepository;

	@Mock
	CategoryRepository categoryRepository;
	
	
	public Product createProduct() {
		
			Category category2 = new Category();
			category2.setId(Long.valueOf(1));
			category2.setName("Electronics");

			Product productdt = new Product();
			productdt.setName("TV");
			productdt.setBrand("Apple");
			productdt.setPrice(BigDecimal.valueOf(400.00));
			productdt.setInventory(30);
			productdt.setDescription("TV items");
			productdt.setCategory(category2);
		
		return productdt;
	}

	@Test
	void addProductTest (){
		System.out.println("Test case addProduct");

			Category category2 = new Category();
			category2.setId(Long.valueOf(1));
			category2.setName("Electronics");

			Product productdt = new Product();
			productdt.setId(Long.valueOf(1));
			productdt.setName("TV");
			productdt.setBrand("Apple");
			productdt.setPrice(BigDecimal.valueOf(400.00));
			productdt.setInventory(30);
			productdt.setDescription("TV items");
			productdt.setCategory(category2);

		// Product pdata = createProduct();
		
		// System.out.println(pdata);
		Mockito.when(productRepository.save(productdt)).thenReturn(productdt);
		Product addProduct = productRepository.save(productdt);

		Assertions.assertEquals(productdt.getId(), addProduct.getId());
		Assertions.assertNotNull(addProduct);
		Assertions.assertEquals(productdt.getName(), addProduct.getName());
		Assertions.assertTrue(productdt.getId()==1);

		
	}

	@Test
	void deleteproductTest(){
		System.out.println("Delete product");
		doNothing().when(productRepository).deleteById(Long.valueOf(1));
		productService.deleteProductById(Long.valueOf(1));
		Mockito.verify(productRepository, times(1)).deleteById(Long.valueOf(1));
	}

}
