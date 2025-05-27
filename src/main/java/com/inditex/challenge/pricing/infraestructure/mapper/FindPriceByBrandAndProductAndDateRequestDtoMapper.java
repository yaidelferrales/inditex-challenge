package com.inditex.challenge.pricing.infraestructure.mapper;

import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.infraestructure.web.dto.FindPriceByBrandAndProductAndDateRequestDto;
import com.inditex.challenge.pricing.infraestructure.web.dto.SavePriceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FindPriceByBrandAndProductAndDateRequestDtoMapper {

    FindPriceByBrandAndProductAndDateRequestDtoMapper INSTANCE = Mappers.getMapper(FindPriceByBrandAndProductAndDateRequestDtoMapper.class);
    FindPriceByBrandAndProductAndDateDto toFindPriceByBrandAndProductAndDateDto(FindPriceByBrandAndProductAndDateRequestDto instanceToMap);

}
