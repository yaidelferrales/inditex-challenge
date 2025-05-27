package com.inditex.challenge.pricing.domain.repository;

import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.domain.dto.FindPriceByProductAndDateDto;
import com.inditex.challenge.pricing.domain.model.Price;

import java.util.List;
import java.util.Optional;

/**
 * Price repository definition
 *
 * It includes some of the basic operations over the Price model.
 * It may include some other filtering functionalities like:
 *     List<Price> findAllByDate(LocalDateTime date);
 *     List<Price> findAllByProduct(Product product);
 */
public interface PriceRepository {

    Optional<Price> findById(Long id);

    Optional<Price> findByProductAndDate(FindPriceByProductAndDateDto findPriceByProductAndDateDto);

    List<Price> findAllByProductAndDate(FindPriceByProductAndDateDto findPriceByProductAndDateDto);

    Optional<Price> findByBrandAndProductAndDate(FindPriceByBrandAndProductAndDateDto findPriceByBrandAndProductAndDateDto);

    List<Price> findAllByBrandAndProductAndDate(FindPriceByBrandAndProductAndDateDto findPriceByBrandAndProductAndDateDto);

    List<Price> findAll();

    Price save(Price price);

}
