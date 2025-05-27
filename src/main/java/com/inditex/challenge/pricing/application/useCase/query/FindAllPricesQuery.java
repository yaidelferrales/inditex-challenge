package com.inditex.challenge.pricing.application.useCase.query;

import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindAllPricesQuery {

    private final PriceRepository priceRepository;

    public List<Price> handle() {
        return this.priceRepository.findAll();
    }

}
