package com.inditex.challenge.pricing.infraestructure.persistance.web.controller.priceController.utils;

import com.inditex.challenge.pricing.application.useCase.command.SavePriceCommand;
import com.inditex.challenge.pricing.application.useCase.query.FindAllPricesQuery;
import com.inditex.challenge.pricing.application.useCase.query.FindPriceByBrandProductAndDateQuery;
import com.inditex.challenge.pricing.application.useCase.query.FindPriceByIdQuery;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockBeansConfig {

    @Bean
    public SavePriceCommand savePriceCommand() {
        return Mockito.mock(SavePriceCommand.class);
    }

    @Bean
    public FindAllPricesQuery findAllPricesQuery() {
        return Mockito.mock(FindAllPricesQuery.class);
    }

    @Bean
    public FindPriceByIdQuery findPriceByIdQuery() {
        return Mockito.mock(FindPriceByIdQuery.class);
    }

    @Bean
    public FindPriceByBrandProductAndDateQuery findPriceByBrandProductAndDateQuery() {
        return Mockito.mock(FindPriceByBrandProductAndDateQuery.class);
    }
}
