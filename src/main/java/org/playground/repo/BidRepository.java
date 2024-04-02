package org.playground.repo;

import org.playground.domain.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query("SELECT b FROM Bid b WHERE b.lot.id = :lotId AND b.amount = (SELECT MAX(b2.amount) FROM Bid b2 WHERE b2.lot.id = :lotId)")
    Optional<Bid> findTopByLotIdAndAmount(@Param("lotId") Long lotId);
}
