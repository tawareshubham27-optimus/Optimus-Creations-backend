package com.optimuscreations.optimus.entity;

import java.math.BigDecimal;

public class ProductDto {
    
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long categoryId;

 
    private String imageUrl;

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

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Boolean getInStock() { return inStock; }
    public void setInStock(Boolean inStock) { this.inStock = inStock; }

    public Boolean getFeatured() { return featured; }
    public void setFeatured(Boolean featured) { this.featured = featured; }

    public Integer getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(Integer printTimeHours) { this.deliveryTime = printTimeHours; }
}
