package com.inditex.challenge.pricing.infraestructure.mapper;

import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.infraestructure.web.dto.SavePriceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SavePriceRequestDtoMapper {

    SavePriceRequestDtoMapper INSTANCE = Mappers.getMapper(SavePriceRequestDtoMapper.class);
    SavePriceDto toSavePriceDto(SavePriceRequestDto instanceToMap);

}
