package com.inditex.challenge.core.infraestructure.persistance.repository;

import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.ProductJpaEntity;
import com.inditex.challenge.core.infraestructure.persistance.jpa.repository.ProductJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Product Repository Tests")
class ProductRepositoryImplTest {

    private ProductJpaRepository jpaRepository;
    private ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(ProductJpaRepository.class);
        productRepository = new ProductRepositoryImpl(jpaRepository);
    }

    @Test
    @DisplayName("Should return an instance of product when Found")
    void shouldReturnProductWhenFound() {
        Long id = 1L;
        ProductJpaEntity entity = ProductJpaEntity.builder()
                .id(id)
                .name("T-shirt")
                .description("Blue cotton T-shirt")
                .sku("SKU123456")
                .build();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Product> result = productRepository.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("T-shirt");

        verify(jpaRepository).findById(id);
    }

    @Test
    @DisplayName("Should return empty when the product is not Found")
    void shouldReturnEmptyWhenProductNotFound() {
        Long id = 2L;

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productRepository.findById(id);

        assertThat(result).isNotPresent();

        verify(jpaRepository).findById(id);
    }
}
