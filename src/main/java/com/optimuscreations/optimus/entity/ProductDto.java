package com.optimuscreations.optimus.entity;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {
    
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

    private List<String> imageUrls;

    private Boolean inStock = true;

    private Boolean featured = false;
  
    private Integer deliveryTime;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public List<String> getImageUrls() { return imageUrls; }
    public void setImageUrls(List<String> imageUrls) { this.imageUrls = imageUrls; }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public Integer getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(Integer printTimeHours) { this.deliveryTime = printTimeHours; }
}
