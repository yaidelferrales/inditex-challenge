package com.inditex.challenge.pricing.application.useCase.command;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.core.domain.repository.BrandRepository;
import com.inditex.challenge.core.domain.repository.ProductRepository;
import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SavePriceCommand {

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public Price handle(final SavePriceDto savePriceDto) {
        Product product = productRepository.findById(savePriceDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Brand brand = brandRepository.findById(savePriceDto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        Price price = Price.builder()
                .id(savePriceDto.getId())
                .brand(brand)
                .product(product)
                .startDate(savePriceDto.getStartDate())
                .endDate(savePriceDto.getEndDate())
                .priority(savePriceDto.getPriority())
                .priceList(savePriceDto.getPriceList())
                .price(savePriceDto.getPrice())
                .currency(savePriceDto.getCurrency())
                .build();

        return this.priceRepository.save(price);
    }

}
