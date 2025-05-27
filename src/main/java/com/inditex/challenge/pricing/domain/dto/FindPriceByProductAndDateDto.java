package com.inditex.challenge.pricing.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class FindPriceByProductAndDateDto {

    @NotNull
    private Long productId;

    @NotNull
    private LocalDateTime date;

}
