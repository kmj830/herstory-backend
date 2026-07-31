package com.herstory.backend.domain.mypage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MentoringApplicationRepository extends JpaRepository<MentoringApplication, Long> {
    List<MentoringApplication> findByArtistId(Long artistId);
}
