package com.herstory.backend.domain.studio;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AiPatternRepository extends JpaRepository<AiPattern, Long> {
    List<AiPattern> findByArtworkId(Long artworkId);
}
