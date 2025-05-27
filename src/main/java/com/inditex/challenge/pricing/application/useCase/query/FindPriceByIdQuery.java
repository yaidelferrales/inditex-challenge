package com.inditex.challenge.pricing.application.useCase.query;

import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindPriceByIdQuery {

    private final PriceRepository priceRepository;

    public Optional<Price> handle(final Long id) {
        return this.priceRepository.findById(id);
    }

}
