package com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.application.useCase.command.SavePriceCommand;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController.utils.MockBeansConfig;
import com.inditex.challenge.pricing.infraestructure.web.controller.PriceController;
import com.inditex.challenge.pricing.infraestructure.web.dto.SavePriceRequestDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(MockBeansConfig.class)
@DisplayName("Price Controller - Save Price Instance Tests")
class SavePricesUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SavePriceCommand savePriceCommand;

    @BeforeEach
    void setUp() {
        Mockito.reset(savePriceCommand);
    }

    @Test
    @DisplayName("Should save a Price instance")
    void shouldSavePriceSuccessfully() throws Exception {
        // Given
        SavePriceRequestDto requestDto = SavePriceRequestDto.builder()
                .brandId(1L)
                .productId(35455L)
                .priority(1)
                .startDate(LocalDateTime.of(2025, 5, 27, 0, 0))
                .endDate(LocalDateTime.of(2025, 6, 1, 0, 0))
                .price(new BigDecimal("35.5"))
                .priceList(1)
                .currency(Currency.EUR)
                .build();

        Price savedPrice = Price.builder()
                .id(100L)
                .brand(Brand.builder().id(1L).name("Example Brand").build())
                .product(Product.builder().id(35455L).name("Example Product").description("Example description").sku("testsku").build())
                .priority(1)
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .price(requestDto.getPrice())
                .priceList(requestDto.getPriceList())
                .currency(requestDto.getCurrency())
                .build();

        Mockito.when(savePriceCommand.handle(any(SavePriceDto.class))).thenReturn(savedPrice);

        String requestBody = """
        {
            "brandId": 1,
            "productId": 35455,
            "priority": 1,
            "startDate": "2025-05-27T00:00:00",
            "endDate": "2025-06-01T00:00:00",
            "price": 35.5,
            "priceList": 1,
            "currency": "EUR"
        }
        """;

        // When & Then
        mockMvc.perform(post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.brand.id").value(1))
                .andExpect(jsonPath("$.product.id").value(35455))
                .andExpect(jsonPath("$.price").value(35.5))
                .andExpect(jsonPath("$.currency").value("EUR"));
    }

    @ParameterizedTest
    @MethodSource("invalidSavePriceRequestProvider")
    @DisplayName("Should return a BadRequest error if any params is missing")
    void shouldReturnBadRequestWhenRequiredFieldsAreMissing(String invalidJson) throws Exception {
        mockMvc.perform(post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    static Stream<String> invalidSavePriceRequestProvider() {
        String validJson = """
        {
            "brandId": 1,
            "productId": 35455,
            "priority": 1,
            "startDate": "2025-05-27T00:00:00",
            "endDate": "2025-06-01T00:00:00",
            "price": 35.5,
            "priceList": 1,
            "currency": "EUR"
        }
        """;

        return Stream.of(
                validJson.replace("\"brandId\": 1,", ""), // missing brandId
                validJson.replace("\"productId\": 35455,", ""), // missing productId
                validJson.replace("\"price\": 35.5,", ""), // missing price
                validJson.replace("\"currency\": \"EUR\"", "") // missing currency
        );
    }

    @Test
    @DisplayName("Should return a BadRequest error when the id does not exist while saving")
    void shouldReturnBadRequestWhenEntityNotFoundOnSave() throws Exception {
        Mockito.when(savePriceCommand.handle(any())).thenThrow(new EntityNotFoundException("Brand not found"));

        String requestBody = """
        {
            "brandId": 1,
            "productId": 35455,
            "priority": 1,
            "startDate": "2025-05-27T00:00:00",
            "endDate": "2025-06-01T00:00:00",
            "price": 35.5,
            "priceList": 1,
            "currency": "EUR"
        }
        """;

        mockMvc.perform(post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Brand not found"));
    }


    @Test
    @DisplayName("Should return an Internal server error if there is an unexpected exception")
    void shouldReturnInternalServerErrorWhenUnexpectedErrorOnSave() throws Exception {
        Mockito.when(savePriceCommand.handle(any())).thenThrow(new RuntimeException("Unexpected error"));

        String requestBody = """
        {
            "brandId": 1,
            "productId": 35455,
            "priority": 1,
            "startDate": "2025-05-27T00:00:00",
            "endDate": "2025-06-01T00:00:00",
            "price": 35.5,
            "priceList": 1,
            "currency": "EUR"
        }
        """;

        mockMvc.perform(post("/api/prices/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

}
