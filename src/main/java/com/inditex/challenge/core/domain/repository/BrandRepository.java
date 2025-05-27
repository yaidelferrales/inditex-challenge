package com.inditex.challenge.core.domain.repository;

import com.inditex.challenge.core.domain.model.Brand;

import java.util.Optional;

public interface BrandRepository {

    Optional<Brand> findById(Long id);

}
