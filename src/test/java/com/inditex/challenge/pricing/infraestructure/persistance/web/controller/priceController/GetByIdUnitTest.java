package com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController;

import com.inditex.challenge.pricing.application.useCase.query.FindPriceByBrandProductAndDateQuery;
import com.inditex.challenge.pricing.application.useCase.query.FindPriceByIdQuery;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController.utils.MockBeansConfig;
import com.inditex.challenge.pricing.infraestructure.web.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(MockBeansConfig.class)
@DisplayName("Price Controller - Get By Id Tests")
class GetByIdUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindPriceByIdQuery findPriceByIdQuery;

    @Autowired
    private FindPriceByBrandProductAndDateQuery findPriceByBrandProductAndDateQuery;

    @BeforeEach
    void setUp() {
        Mockito.reset(findPriceByIdQuery, findPriceByBrandProductAndDateQuery);
    }

    @Test
    @DisplayName("Should return a Price instance by it's id")
    void shouldGetPriceById() throws Exception {
        Price price = Price.builder().id(1L).price(BigDecimal.valueOf(99.99)).build();
        Mockito.when(findPriceByIdQuery.handle(1L)).thenReturn(Optional.of(price));

        mockMvc.perform(get("/api/prices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("Should return a NotFound when the id does not exist")
    void shouldReturnNotFoundWhenTheIdDoesNotExist() throws Exception {
        Mockito.when(findPriceByIdQuery.handle(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/prices/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return an Internal server error if there is an unexpected exception")
    void shouldReturnInternalServerErrorWhenGetByIdFails() throws Exception {
        Mockito.when(findPriceByIdQuery.handle(1L)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/prices/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

}
