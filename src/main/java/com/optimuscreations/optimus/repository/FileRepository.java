package com.optimuscreations.optimus.repository;

import com.optimuscreations.optimus.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    // Add custom query methods if needed
}