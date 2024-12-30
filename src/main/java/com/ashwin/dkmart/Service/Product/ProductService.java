package com.ashwin.dkmart.Service.Product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashwin.dkmart.Entity.Product;
import com.ashwin.dkmart.Controller.ProductController;
import com.ashwin.dkmart.DTO.ImageDTO;
import com.ashwin.dkmart.DTO.ProductDto;
import com.ashwin.dkmart.Entity.Category;
import com.ashwin.dkmart.Entity.Image;
import com.ashwin.dkmart.Exception.ProductNotFoundException;
import com.ashwin.dkmart.Model.AddProductModel;
import com.ashwin.dkmart.Model.UpdateProductModel;
import com.ashwin.dkmart.Repository.ProductRepository;
import com.ashwin.dkmart.Repository.CategoryRepository;
import com.ashwin.dkmart.Repository.ImageRepository;

@Service
public class ProductService implements IProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ImageRepository imageRepository;

	private final ModelMapper modelMapper;

	@Autowired
    public ProductService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
	
	@Override
	public Product addProduct(AddProductModel addProductModel) {
		// to add the product we create the model with product and category alone 
		// then we have method with model and category 
		// we below case 
		
		
		// if category in DB 
		// then add the product
		// if category not in DB add category 
		// add the product DB
		Category category = Optional.ofNullable(categoryRepository.findByName(addProductModel.getCategory().getName()))
				.orElseGet(
						() -> {  
					Category newcategory = new Category(addProductModel.getCategory().getName());
					return  categoryRepository.save(newcategory);
				});
		
		addProductModel.setCategory(category);

		return productRepository.save(createProduct(addProductModel, category));
	}

	public Product createProduct(AddProductModel addProductModel, Category category) {
		Product product = new Product(
				addProductModel.getName(),
				addProductModel.getBrand(),
				addProductModel.getPrice(),
				addProductModel.getInventory(),
				addProductModel.getDescription(),
				category
				);
		
		return product;
	}
	
	@Override
	public Product updateProduct(UpdateProductModel productdetail, Long Id) {
			
		return productRepository.findById(Id)
				.map(existingproduct -> UpdateProductDetail(productdetail,existingproduct))
				.map(a -> productRepository.save(a))
				.orElseThrow(() -> new ProductNotFoundException("Product Not Fount!"));
	}

	
	public Product UpdateProductDetail(UpdateProductModel existingproduct, Product product) {
		
		Category categorydetail = categoryRepository.findByName(existingproduct.getCategory().getName());
		product.setCategory(categorydetail);
		
		product.setBrand(existingproduct.getBrand());
		product.setDescription(existingproduct.getDescription());
		product.setName(existingproduct.getName());
		product.setInventory(existingproduct.getInventory());
		product.setPrice(existingproduct.getPrice());

		return product;
	}

	@Override
	public Product productById(Long Id) {
		
		return productRepository.findById(Id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
	}

	@Override
	public void deleteProductById(Long Id) {
	
		productRepository.findById(Id)
			.ifPresentOrElse(productRepository::delete, 
					() -> {throw new ProductNotFoundException("Product not found!");} );
		
	}

	@Override
	public List<Product> getProductByBrand(String brand) {

		logger.info("Product By brand");
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductByBrandAndCategory(String brand, String category) {

		return productRepository.findByBrandAndCategoryName(brand, category);
	}

	@Override
	public List<Product> getProductByName(String name) {

		return productRepository.findByName(name);
	}

	@Override
	public Long getProductCountByBrandAndName(String brand, String name) {
		
		return productRepository.countByBrandAndName(brand,name);
	}
	
	// convert list of product to product DTO
	@Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
      return products.stream().map(this::convertToDto).toList();
    }

	// we convert the product to productDTO
    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());

        List<ImageDTO> imageDtos = images.stream()
                	.map(image -> modelMapper.map(image, ImageDTO.class))
                	.toList();

        productDto.setImageList(imageDtos);
        return productDto;
    }
	
	

}
