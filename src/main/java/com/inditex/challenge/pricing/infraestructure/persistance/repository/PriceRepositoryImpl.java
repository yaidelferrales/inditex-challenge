package com.inditex.challenge.pricing.infraestructure.persistance.repository;

import com.inditex.challenge.pricing.domain.dto.FindPriceByBrandAndProductAndDateDto;
import com.inditex.challenge.pricing.domain.dto.FindPriceByProductAndDateDto;
import com.inditex.challenge.pricing.domain.model.Price;
import com.inditex.challenge.pricing.domain.repository.PriceRepository;
import com.inditex.challenge.pricing.infraestructure.mapper.PriceMapper;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.entity.PriceJpaEntity;
import com.inditex.challenge.pricing.infraestructure.persistance.jpa.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository jpaPriceRepository;

    @Override
    public Optional<Price> findById(Long id) {
        return jpaPriceRepository.findById(id)
                .map(PriceMapper.INSTANCE::fromJpaEntity);
    }

    @Override
    public Optional<Price> findByProductAndDate(FindPriceByProductAndDateDto dto) {
        return jpaPriceRepository
                .findFirstByProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(dto.getProductId(), dto.getDate(), dto.getDate())
                .map(PriceMapper.INSTANCE::fromJpaEntity);
    }

    @Override
    public List<Price> findAllByProductAndDate(FindPriceByProductAndDateDto dto) {
        return jpaPriceRepository
                .findAllByProductIdAndStartDateBeforeAndEndDateAfter(dto.getProductId(), dto.getDate(), dto.getDate())
                .stream()
                .map(PriceMapper.INSTANCE::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Price> findByBrandAndProductAndDate(FindPriceByBrandAndProductAndDateDto dto) {
        return jpaPriceRepository
                .findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(dto.getBrandId(), dto.getProductId(), dto.getDate(), dto.getDate())
                .map(PriceMapper.INSTANCE::fromJpaEntity);
    }

    @Override
    public List<Price> findAllByBrandAndProductAndDate(FindPriceByBrandAndProductAndDateDto dto) {
        return jpaPriceRepository
                .findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(dto.getBrandId(), dto.getProductId(), dto.getDate(), dto.getDate())
                .stream()
                .map(PriceMapper.INSTANCE::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Price> findAll() {
        return jpaPriceRepository.findAll()
                .stream()
                .map(PriceMapper.INSTANCE::fromJpaEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Price save(Price price) {
        PriceJpaEntity entity = PriceMapper.INSTANCE.toJpaEntity(price);
        PriceJpaEntity saved = jpaPriceRepository.save(entity);
        return PriceMapper.INSTANCE.fromJpaEntity(saved);
    }

}

