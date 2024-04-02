package org.playground.repo;

import org.playground.domain.WinningBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WinningBidRepository extends JpaRepository<WinningBid, Long> {

}
