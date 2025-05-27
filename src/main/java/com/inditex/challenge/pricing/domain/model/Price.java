package com.inditex.challenge.pricing.domain.model;

import com.inditex.challenge.core.domain.model.Brand;
import com.inditex.challenge.core.domain.model.Product;
import com.inditex.challenge.pricing.domain.model.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Price {

    private Long id;

    @NotNull
    private Brand brand;

    @NotNull
    private Product product;

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
