package com.optimuscreations.optimus.repository;


import com.optimuscreations.optimus.entity.ProductImages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImagesRepository extends JpaRepository<ProductImages, Long> {
    // Add custom query methods if needed
}