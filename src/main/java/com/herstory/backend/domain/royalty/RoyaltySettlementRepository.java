package com.herstory.backend.domain.royalty;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoyaltySettlementRepository extends JpaRepository<RoyaltySettlement, Long> {
    List<RoyaltySettlement> findByArtistId(Long artistId);
}
