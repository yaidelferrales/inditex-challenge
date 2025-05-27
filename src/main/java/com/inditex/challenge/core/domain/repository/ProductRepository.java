package com.inditex.challenge.core.domain.repository;

import com.inditex.challenge.core.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

}
