package com.inditex.challenge.core.infraestructure.persistance.jpa.repository;

import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, Long> {
}
