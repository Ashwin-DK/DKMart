package com.ashwin.dkmart.DTO;

import java.math.BigDecimal;
import java.util.List;

import com.ashwin.dkmart.Entity.Category;
import com.ashwin.dkmart.Entity.Image;

public class ProductDto {
    private Long Id;
	private String name;
	private String brand;
	private BigDecimal price;
	private int Inventory;
	private String Description;
    private Category category; 
    private List<ImageDTO> imageList;
    
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public int getInventory() {
        return Inventory;
    }
    public void setInventory(int inventory) {
        Inventory = inventory;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public List<ImageDTO> getImageList() {
        return imageList;
    }
    public void setImageList(List<ImageDTO> imageList) {
        this.imageList = imageList;
    }
    



}
