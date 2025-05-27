package com.inditex.challenge.core.infraestructure.mapper;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.BrandJpaEntity;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.entity.PriceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);
    BrandJpaEntity toJpaEntity(Brand brand);
    Brand fromJpaEntity(BrandJpaEntity brandJpaEntity);

}
