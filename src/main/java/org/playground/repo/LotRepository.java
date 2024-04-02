package org.playground.repo;

import org.playground.domain.Lot;
import org.playground.dto.LotDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    @Query("SELECT NEW org.playground.dto.LotDto(l.id, l.lotName, l.seller.id, l.category.id, l.description, l.auctionEnd, l.active, l.buyOutPrice) " +
            "FROM Lot l " +
            "WHERE l.category.id = :categoryId")
    Page<LotDto> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);

    List<Lot> findAllBySellerId(@Param("id") Long id);


    @Query("SELECT l FROM Lot l WHERE l.auctionEnd <= CURRENT_TIMESTAMP AND l.active=true")
    List<Lot> fetchInactive();

}
