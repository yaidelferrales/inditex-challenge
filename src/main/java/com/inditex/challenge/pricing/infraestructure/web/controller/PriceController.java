package com.inditex.challenge.pricing.infraestructure.web.controller;

import com.inditex.challenge.pricing.application.dto.SavePriceDto;
import com.inditex.challenge.pricing.application.useCase.command.SavePriceCommand;
import com.inditex.challenge.pricing.application.useCase.query.FindAllPricesQuery;
import com.inditex.challenge.pricing.application.useCase.query.FindPriceByBrandProductAndDateQuery;
import com.inditex.challenge.pricing.application.useCase.query.FindPriceByIdQuery;
import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.infraestructure.mapper.FindPriceByBrandAndProductAndDateRequestDtoMapper;
import com.inditex.challenge.pricing.infraestructure.mapper.SavePriceRequestDtoMapper;
import com.inditex.challenge.pricing.infraestructure.web.exception.ErrorResponse;
import com.inditex.challenge.pricing.infraestructure.web.dto.FindPriceByBrandAndProductAndDateRequestDto;
import com.inditex.challenge.pricing.infraestructure.web.dto.SavePriceRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
@AllArgsConstructor
public class PriceController {

    private SavePriceCommand savePriceCommand;
    private FindAllPricesQuery findAllPricesQuery;
    private FindPriceByIdQuery findPriceByIdQuery;
    private FindPriceByBrandProductAndDateQuery findPriceByBrandProductAndDateQuery;

    @GetMapping("/")
    public ResponseEntity<?> listPrices() {
        try {
            List<Price> prices = findAllPricesQuery.handle();
            return ResponseEntity.ok(prices);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> listPrices(@Valid @RequestBody SavePriceRequestDto requestDto) {
        try {
            SavePriceDto dto = SavePriceRequestDtoMapper.INSTANCE.toSavePriceDto(requestDto);
            Price price = savePriceCommand.handle(dto);
            return ResponseEntity.ok(price);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPriceById(@PathVariable final Long id) {
        try {
            return findPriceByIdQuery.handle(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> findPriceByQuery(@Valid @ModelAttribute FindPriceByBrandAndProductAndDateRequestDto requestDto) {
        try {
            FindPriceByBrandAndProductAndDateDto dto = FindPriceByBrandAndProductAndDateRequestDtoMapper
                    .INSTANCE.toFindPriceByBrandAndProductAndDateDto(requestDto);
            return findPriceByBrandProductAndDateQuery.handle(dto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error"));
        }
    }

}
