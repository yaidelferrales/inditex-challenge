package com.inditex.challenge.core.infraestructure.persistance.repository;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.repository.BrandRepository;
import com.inditex.challenge.core.infraestructure.persistance.jpa.repository.BrandJpaRepository;
import com.inditex.challenge.core.infraestructure.mapper.BrandMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BrandRepositoryImpl implements BrandRepository {

    private final BrandJpaRepository jpaRepository;

    public BrandRepositoryImpl(BrandJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return jpaRepository.findById(id)
                .map(BrandMapper.INSTANCE::fromJpaEntity);
    }

}
