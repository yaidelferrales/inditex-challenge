package com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController;

import com.inditex.challenge.pricing.application.useCase.query.FindPriceByBrandProductAndDateQuery;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController.utils.MockBeansConfig;
import com.inditex.challenge.pricing.infraestructure.web.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(MockBeansConfig.class)
@DisplayName("Price Controller - Search Price Tests")
class SearchPriceUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindPriceByBrandProductAndDateQuery findPriceByBrandProductAndDateQuery;

    @BeforeEach
    void setUp() {
        Mockito.reset(findPriceByBrandProductAndDateQuery);
    }

    @Test
    @DisplayName("Should search a Price instance by Brand, Product and Date")
    void shouldSearchPriceByBrandProductAndDate() throws Exception {
        Price price = Price.builder().id(1L).price(BigDecimal.valueOf(99.99)).build();
        Mockito.when(findPriceByBrandProductAndDateQuery.handle(any())).thenReturn(Optional.of(price));

        mockMvc.perform(get("/api/prices/search")
                        .param("brandId", "1")
                        .param("productId", "2")
                        .param("date", LocalDateTime.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @ParameterizedTest
    @MethodSource("missingParamsProvider")
    @DisplayName("Should return a BadRequest error if any params is missing")
    void shouldReturnBadRequestWhenParamsAreMissing(Map<String, String> params) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/api/prices/search");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBuilder = requestBuilder.param(entry.getKey(), entry.getValue());
        }

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest());
    }

    static Stream<Map<String, String>> missingParamsProvider() {
        String now = LocalDateTime.now().toString();

        return Stream.of(
                Map.of("productId", "2", "date", now),           // missing brandId
                Map.of("brandId", "1", "date", now),             // missing productId
                Map.of("brandId", "1", "productId", "2"),         // missing date
                Map.of()                                          // all missing
        );
    }

    @Test
    @DisplayName("Should return an Internal server error if there is an unexpected exception")
    void shouldReturnInternalServerErrorWhenSearchFails() throws Exception {
        Mockito.when(findPriceByBrandProductAndDateQuery.handle(any())).thenThrow(new RuntimeException("Service unavailable"));

        mockMvc.perform(get("/api/prices/search")
                        .param("brandId", "1")
                        .param("productId", "2")
                        .param("date", LocalDateTime.now().toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

}
