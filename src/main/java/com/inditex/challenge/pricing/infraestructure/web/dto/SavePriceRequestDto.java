package com.inditex.challenge.pricing.infraestructure.web.dto;

import com.inditex.challenge.pricing.domain.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavePriceRequestDto {

    private Long id;

    @NotNull
    private Long brandId;

    @NotNull
    private Long productId;

    @NotNull
    private Integer priority;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer priceList;

    @NotNull
    private Currency currency;

}
