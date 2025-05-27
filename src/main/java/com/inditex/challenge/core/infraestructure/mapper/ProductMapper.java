package com.inditex.challenge.core.infraestructure.mapper;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.BrandJpaEntity;
import com.inditex.challenge.core.infraestructure.persistance.jpa.entity.ProductJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductJpaEntity toJpaEntity(Product product);
    Product fromJpaEntity(ProductJpaEntity productJpaEntity);

}
