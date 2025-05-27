package com.inditex.challenge.pricing.infraestructure.persistance.web.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Price Controller - Integration Tests")
class PriceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String URL = "/api/prices/search";

    @ParameterizedTest
    @MethodSource("searchPricesProvider")
    @DisplayName("Should return the right price given the query:")
    void shouldReturnTheRightPriceForEachQuery(Map<String, String> params) throws Exception {
        mockMvc.perform(get(URL)
                        .param("brandId", params.get("brandId"))
                        .param("productId", params.get("productId"))
                        .param("date", params.get("date")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product.id").value(Long.parseLong(params.get("productId"))))
                .andExpect(jsonPath("$.brand.id").value(Long.parseLong(params.get("brandId"))))
                .andExpect(jsonPath("$.price").value(Double.parseDouble(params.get("expectedPrice"))));
    }

    static Stream<Map<String, String>> searchPricesProvider() {
        String now = LocalDateTime.now().toString();

        return Stream.of(
                Map.of("brandId", "1", "productId", "35455", "date", "2020-06-14T10:00:00", "expectedPrice", "35.5"),
                Map.of("brandId", "1", "productId", "35455", "date", "2020-06-14T16:00:00", "expectedPrice", "25.45"),
                Map.of("brandId", "1", "productId", "35455", "date", "2020-06-14T21:00:00", "expectedPrice", "35.5"),
                Map.of("brandId", "1", "productId", "35455", "date", "2020-06-15T10:00:00", "expectedPrice", "30.5"),
                Map.of("brandId", "1", "productId", "35455", "date", "2020-06-16T21:00:00", "expectedPrice", "38.95")
        );
    }

}

