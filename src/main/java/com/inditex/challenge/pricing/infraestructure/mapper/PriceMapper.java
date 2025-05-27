package com.inditex.challenge.pricing.infraestructure.mapper;

import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.entity.PriceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);
    PriceJpaEntity toJpaEntity(Price price);
    Price fromJpaEntity(PriceJpaEntity priceJpaEntity);

}
