package com.inditex.challenge.core.infraestructure.persistance.repository;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.BrandJpaEntity;
import com.inditex.challenge.core.infraestructure.persistance.jpa.repository.BrandJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Brand Repository Tests")
class BrandRepositoryImplTest {

    private BrandJpaRepository jpaRepository;
    private BrandRepositoryImpl brandRepository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(BrandJpaRepository.class);
        brandRepository = new BrandRepositoryImpl(jpaRepository);
    }

    @Test
    @DisplayName("Should return an instance of brand when Found")
    void shouldReturnBrandWhenFound() {
        Long id = 1L;
        BrandJpaEntity entity = BrandJpaEntity.builder()
                .id(id)
                .name("Zara")
                .build();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<Brand> result = brandRepository.findById(id);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getName()).isEqualTo("Zara");

        verify(jpaRepository).findById(id);
    }

    @Test
    @DisplayName("Should return empty when the brand is not Found")
    void shouldReturnEmptyWhenBrandNotFound() {
        Long id = 2L;

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Brand> result = brandRepository.findById(id);

        assertThat(result).isNotPresent();

        verify(jpaRepository).findById(id);
    }
}
