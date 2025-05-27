package com.inditex.challenge.pricing.application.useCase.command;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.core.domain.repository.BrandRepository;
import com.inditex.challenge.core.domain.repository.ProductRepository;
import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Save Price Command Tests")
class SavePriceCommandTest {

    private PriceRepository priceRepository;
    private BrandRepository brandRepository;
    private ProductRepository productRepository;

    private SavePriceCommand savePriceCommand;

    @BeforeEach
    void setUp() {
        priceRepository = mock(PriceRepository.class);
        brandRepository = mock(BrandRepository.class);
        productRepository = mock(ProductRepository.class);
        savePriceCommand = new SavePriceCommand(priceRepository, brandRepository, productRepository);
    }

    @Test
    @DisplayName("Should save a new Price instance")
    void shouldSavePriceSuccessfully() {
        // Arrange
        Long brandId = 1L;
        Long productId = 2L;
        SavePriceDto dto = SavePriceDto.builder()
                .id(10L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .priority(1)
                .priceList(100)
                .price(BigDecimal.valueOf(59.99))
                .currency(Currency.EUR)
                .build();

        Brand brand = new Brand(brandId, "ZARA");
        Product product = new Product(productId, "Product 1", "Product 1 description", "product-1-sku");

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(priceRepository.save(any(Price.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Price savedPrice = savePriceCommand.handle(dto);

        // Assert
        assertNotNull(savedPrice);
        assertEquals(dto.getId(), savedPrice.getId());
        assertEquals(brand, savedPrice.getBrand());
        assertEquals(product, savedPrice.getProduct());
        assertEquals(dto.getPrice(), savedPrice.getPrice());
        assertEquals(dto.getCurrency(), savedPrice.getCurrency());

        verify(brandRepository).findById(brandId);
        verify(productRepository).findById(productId);
        verify(priceRepository).save(any(Price.class));
    }

    @Test
    @DisplayName("Should throw an exception if the product does not exist")
    void shouldThrowWhenProductNotFound() {
        // Arrange
        SavePriceDto dto = SavePriceDto.builder()
                .brandId(1L)
                .productId(999L) // not found
                .build();

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            savePriceCommand.handle(dto);
        });

        assertEquals("Product not found", ex.getMessage());
        verify(productRepository).findById(999L);
        verifyNoInteractions(brandRepository, priceRepository);
    }

    @Test
    @DisplayName("Should throw an exception if the brand does not exist")
    void shouldThrowWhenBrandNotFound() {
        // Arrange
        Long brandId = 999L;
        Long productId = 2L;

        SavePriceDto dto = SavePriceDto.builder()
                .brandId(brandId)
                .productId(productId)
                .build();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(
                        new Product(productId, "Product 1", "Product 1 description", "product-1-sku"))
                );
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> {
            savePriceCommand.handle(dto);
        });

        assertEquals("Brand not found", ex.getMessage());
        verify(productRepository).findById(productId);
        verify(brandRepository).findById(brandId);
        verifyNoInteractions(priceRepository);
    }

}
