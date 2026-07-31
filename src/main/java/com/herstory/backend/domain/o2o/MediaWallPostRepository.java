package com.herstory.backend.domain.o2o;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MediaWallPostRepository extends JpaRepository<MediaWallPost, Long> {
    List<MediaWallPost> findByDisplayStatus(DisplayStatus displayStatus);
}
