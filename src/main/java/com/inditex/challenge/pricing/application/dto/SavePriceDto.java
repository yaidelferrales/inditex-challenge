package com.inditex.challenge.pricing.application.dto;

import com.inditex.challenge.pricing.domain.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavePriceDto {

    private Long id;

    @NotNull
    private Long brandId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer priority;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer priceList;

    @NotNull
    private Currency currency;

}
