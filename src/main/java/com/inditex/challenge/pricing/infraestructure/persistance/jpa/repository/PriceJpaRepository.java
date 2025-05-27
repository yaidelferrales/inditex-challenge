package com.inditex.challenge.pricing.infraestructure.persistance.jpa.repository;

import com.inditex.challenge.pricing.infraestructure.persistance.jpa.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    Optional<PriceJpaEntity> findFirstByProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
            Long productId, LocalDateTime startDate, LocalDateTime endDate);

    Optional<PriceJpaEntity> findFirstByProductIdAndStartDateBeforeAndEndDateAfter(
            Long productId, LocalDateTime date1, LocalDateTime date2);

    List<PriceJpaEntity> findAllByProductIdAndStartDateBeforeAndEndDateAfter(
            Long productId, LocalDateTime date1, LocalDateTime date2);

    Optional<PriceJpaEntity> findFirstByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfterOrderByPriorityDesc(
            Long brandId, Long productId, LocalDateTime date1, LocalDateTime date2);

    List<PriceJpaEntity> findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
            Long brandId, Long productId, LocalDateTime date1, LocalDateTime date2);

}
