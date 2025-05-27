package com.inditex.challenge.pricing.infraestructure.persistance.repository;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.domain.dto.FindPriceByProductAndDateDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import com.inditex.challenge.pricing.infraestructure.mapper.PriceMapper;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.entity.PriceJpaEntity;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.repository.PriceJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Price Repository Implementation Tests")
class PriceRepositoryImplTest {

    private PriceJpaRepository priceJpaRepository;
    private PriceRepositoryImpl priceRepository;

    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        priceJpaRepository = mock(PriceJpaRepository.class);
        priceRepository = new PriceRepositoryImpl(priceJpaRepository);
    }

    private PriceJpaEntity buildJpaEntity() {
        return PriceJpaEntity.builder()
                .id(1L)
                .brand(com.inditex.challenge.core.infraestructure.persistance.jpa.entity.BrandJpaEntity.builder().id(1L).name("Zara").build())
                .product(com.inditex.challenge.core.infraestructure.persistance.jpa.entity.ProductJpaEntity.builder().id(1L).name("T-Shirt").sku("SKU123").build())
                .price(BigDecimal.valueOf(99.99))
                .priority(1)
                .priceList(1)
                .currency(Currency.EUR)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .build();
    }

    @Test
    @DisplayName("Should return a price instance when Found")
    void shouldFindById() {
        when(priceJpaRepository.findById(1L)).thenReturn(Optional.of(buildJpaEntity()));

        Optional<Price> result = priceRepository.findById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(priceJpaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should find a price instance by the productId and the date when Found")
    void shouldFindByProductAndDate() {
        FindPriceByProductAndDateDto dto = FindPriceByProductAndDateDto.builder()
                .productId(1L)
                .date(now)
                .build();

        when(priceJpaRepository.findFirstByProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(1L, now, now))
                .thenReturn(Optional.of(buildJpaEntity()));

        Optional<Price> result = priceRepository.findByProductAndDate(dto);

        assertThat(result).isPresent();
        verify(priceJpaRepository).findFirstByProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(1L, now, now);
    }

    @Test
    @DisplayName("Should find all price instances by the productId and the date")
    void shouldFindAllByProductAndDate() {
        FindPriceByProductAndDateDto dto = FindPriceByProductAndDateDto.builder()
                .productId(1L)
                .date(now)
                .build();

        when(priceJpaRepository.findAllByProductIdAndStartDateBeforeAndEndDateAfter(1L, now, now))
                .thenReturn(List.of(buildJpaEntity()));

        List<Price> results = priceRepository.findAllByProductAndDate(dto);

        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("Should find a price instance by brandId, productId and date when Found")
    void shouldFindByBrandAndProductAndDate() {
        FindPriceByBrandAndProductAndDateDto dto = FindPriceByBrandAndProductAndDateDto.builder()
                .brandId(1L)
                .productId(1L)
                .date(now)
                .build();

        when(priceJpaRepository.findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(1L, 1L, now, now))
                .thenReturn(Optional.of(buildJpaEntity()));

        Optional<Price> result = priceRepository.findByBrandAndProductAndDate(dto);

        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("Should find all price instances by brandId, productId and date when Found")
    void shouldFindAllByBrandAndProductAndDate() {
        FindPriceByBrandAndProductAndDateDto dto = FindPriceByBrandAndProductAndDateDto.builder()
                .brandId(1L)
                .productId(1L)
                .date(now)
                .build();

        when(priceJpaRepository.findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(1L, 1L, now, now))
                .thenReturn(List.of(buildJpaEntity()));

        List<Price> results = priceRepository.findAllByBrandAndProductAndDate(dto);

        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("Should find all price instances")
    void shouldFindAll() {
        when(priceJpaRepository.findAll()).thenReturn(List.of(buildJpaEntity()));

        List<Price> results = priceRepository.findAll();

        assertThat(results).hasSize(1);
        verify(priceJpaRepository).findAll();
    }

    @Test
    @DisplayName("Should save a new price instance")
    void shouldSavePrice() {
        Price domain = Price.builder()
                .id(1L)
                .brand(Brand.builder().id(1L).name("Zara").build())
                .product(Product.builder().id(1L).name("T-Shirt").sku("SKU123").build())
                .price(BigDecimal.valueOf(99.99))
                .priority(1)
                .priceList(1)
                .currency(Currency.EUR)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .build();

        when(priceJpaRepository.save(any())).thenReturn(buildJpaEntity());

        Price saved = priceRepository.save(domain);

        assertThat(saved).isNotNull();
        assertThat(saved.getPrice()).isEqualTo(BigDecimal.valueOf(99.99));
        verify(priceJpaRepository).save(any());
    }

}
