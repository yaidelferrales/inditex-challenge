package com.inditex.challenge.pricing.application.useCase.query;

import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Find All Prices Query Tests")
class FindAllPricesQueryTest {

    private PriceRepository priceRepository;
    private FindAllPricesQuery findAllPricesQuery;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        findAllPricesQuery = new FindAllPricesQuery(priceRepository);
    }

    @Test
    @DisplayName("Should return the list of prices")
    void shouldReturnAllPrices() {
        // Arrange
        Price price1 = Price.builder()
                .id(1L)
                .price(BigDecimal.valueOf(100.00))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .currency(Currency.EUR)
                .build();

        Price price2 = Price.builder()
                .id(2L)
                .price(BigDecimal.valueOf(200.00))
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .currency(Currency.USD)
                .build();

        List<Price> mockPrices = List.of(price1, price2);
        when(priceRepository.findAll()).thenReturn(mockPrices);

        // Act
        List<Price> result = findAllPricesQuery.handle();

        // Assert
        assertEquals(mockPrices.size(), result.size());
        assertEquals(mockPrices, result);
        verify(priceRepository, times(1)).findAll();
    }
}
