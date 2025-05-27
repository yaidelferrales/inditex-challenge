package com.inditex.challenge.core.infraestructure.persistance.repository;

import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.core.domain.repository.ProductRepository;
import com.inditex.challenge.core.infraestructure.persistance.jpa.repository.ProductJpaRepository;
import com.inditex.challenge.core.infraestructure.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ProductMapper.INSTANCE::fromJpaEntity);
    }

}
