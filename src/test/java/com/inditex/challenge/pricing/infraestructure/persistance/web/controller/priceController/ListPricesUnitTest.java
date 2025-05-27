package com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController;

import com.inditex.challenge.pricing.application.useCase.query.FindAllPricesQuery;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceController.class)
@Import(MockBeansConfig.class)
@DisplayName("Price Controller - List Prices Tests")
class ListPricesUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FindAllPricesQuery findAllPricesQuery;

    @BeforeEach
    void setUp() {
        Mockito.reset(findAllPricesQuery);
    }

    @Test
    @DisplayName("Should all price instances")
    void shouldListAllPrices() throws Exception {
        Price price = Price.builder().id(1L).price(BigDecimal.valueOf(99.99)).build();
        Mockito.when(findAllPricesQuery.handle()).thenReturn(List.of(price));

        mockMvc.perform(get("/api/prices/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("Should return an Internal server error if there is an unexpected exception")
    void shouldReturnInternalServerErrorWhenListPricesThrowsException() throws Exception {
        Mockito.when(findAllPricesQuery.handle()).thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(get("/api/prices/"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }

}
