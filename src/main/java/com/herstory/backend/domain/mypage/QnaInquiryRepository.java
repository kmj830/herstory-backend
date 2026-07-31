package com.herstory.backend.domain.mypage;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QnaInquiryRepository extends JpaRepository<QnaInquiry, Long> {
    List<QnaInquiry> findByUserId(Long userId);
}
