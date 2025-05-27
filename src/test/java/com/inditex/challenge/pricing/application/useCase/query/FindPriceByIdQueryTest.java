package com.inditex.challenge.pricing.application.useCase.query;

import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Find Price by Id Query Tests")
class FindPriceByIdQueryTest {

    private PriceRepository priceRepository;
    private FindPriceByIdQuery findPriceByIdQuery;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        findPriceByIdQuery = new FindPriceByIdQuery(priceRepository);
    }

    @Test
    @DisplayName("Should return a price instance when Found")
    void shouldReturnPriceWhenIdExists() {
        // Given
        Long id = 1L;
        Price mockPrice = Price.builder()
                .id(id)
                .price(BigDecimal.valueOf(49.99))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .currency(Currency.EUR)
                .build();

        when(priceRepository.findById(id)).thenReturn(Optional.of(mockPrice));

        // When
        Optional<Price> result = findPriceByIdQuery.handle(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(mockPrice, result.get());
        verify(priceRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Should return empty when the price is not Found")
    void shouldReturnEmptyWhenIdDoesNotExist() {
        // Given
        Long id = 99L;
        when(priceRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Price> result = findPriceByIdQuery.handle(id);

        // Then
        assertTrue(result.isEmpty());
        verify(priceRepository, times(1)).findById(id);
    }
}
