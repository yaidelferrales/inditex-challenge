package com.inditex.challenge.pricing.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class FindPriceByBrandAndProductAndDateDto {

    @NotNull
    private Long brandId;

    @NotNull
    private Long productId;

    @NotNull
    private LocalDateTime date;

}
