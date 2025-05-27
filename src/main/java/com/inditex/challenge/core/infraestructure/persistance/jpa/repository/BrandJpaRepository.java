package com.inditex.challenge.core.infraestructure.persistance.jpa.repository;

import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.BrandJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends JpaRepository<BrandJpaEntity, Long> {
}
