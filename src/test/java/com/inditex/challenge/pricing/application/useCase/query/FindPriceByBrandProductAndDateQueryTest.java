package com.inditex.challenge.pricing.application.useCase.query;

import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Find Price by Brand, Product and Date Query Tests")
class FindPriceByBrandProductAndDateQueryTest {

    private PriceRepository priceRepository;
    private FindPriceByBrandProductAndDateQuery findPriceQuery;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        findPriceQuery = new FindPriceByBrandProductAndDateQuery(priceRepository);
    }

    @Test
    @DisplayName("Should return a price instance when Found")
    void shouldReturnPriceWhenFound() {
        // Arrange
        FindPriceByBrandAndProductAndDateDto dto = new FindPriceByBrandAndProductAndDateDto(1L, 35455L, LocalDateTime.now());
        Price expectedPrice = new Price(); // Populate fields as needed
        when(priceRepository.findByBrandAndProductAndDate(dto)).thenReturn(Optional.of(expectedPrice));

        // Act
        Optional<Price> result = findPriceQuery.handle(dto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
        verify(priceRepository, times(1)).findByBrandAndProductAndDate(dto);
    }

    @Test
    @DisplayName("Should return empty when the price is not Found")
    void shouldReturnEmptyWhenNotFound() {
        // Arrange
        FindPriceByBrandAndProductAndDateDto dto = new FindPriceByBrandAndProductAndDateDto(1L, 35455L, LocalDateTime.now());
        when(priceRepository.findByBrandAndProductAndDate(dto)).thenReturn(Optional.empty());

        // Act
        Optional<Price> result = findPriceQuery.handle(dto);

        // Assert
        assertFalse(result.isPresent());
        verify(priceRepository, times(1)).findByBrandAndProductAndDate(dto);
    }
}
