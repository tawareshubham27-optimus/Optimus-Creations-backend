package com.optimuscreations.optimus.service;

import com.optimuscreations.optimus.entity.Category;
import com.optimuscreations.optimus.entity.Product;
import com.optimuscreations.optimus.entity.ProductDto;

import com.optimuscreations.optimus.repository.ProductRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService; 

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }

    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setCategory(productDetails.getCategory());
            product.setImageUrls(productDetails.getImageUrls());
            product.setPrintTimeHours(productDetails.getPrintTimeHours());
            product.setMaterialType(productDetails.getMaterialType());
            product.setInStock(productDetails.getInStock());
            product.setFeatured(productDetails.getFeatured());
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product addProduct(ProductDto productDto) {
         Product product = new Product();
       if (productDto.getCategoryId() != null && productDto.getCategoryId() > 0) {
           Category category = categoryService.getCategoryById(productDto.getCategoryId())
               .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
               product.setCategory(category);
         }

           product.setName(productDto.getName());
           product.setDescription(productDto.getDescription());
           product.setPrice(productDto.getPrice());
           product.setImageUrls(List.of(productDto.getImageUrl()));
           product.setPrintTimeHours(productDto.getDeliveryTime());
           product.setImageUrls(List.of(productDto.getImageUrl()));
           product.setInStock(productDto.getInStock());
           product.setFeatured(productDto.getFeatured());
           Product createdProduct = productRepository.save(product);


           return createdProduct;
    }
}