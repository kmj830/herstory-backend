package com.herstory.backend.domain.home;

import com.herstory.backend.domain.home.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HomeServiceTest {

    @Autowired
    private HomeService homeService;

    @Test
    @DisplayName("HOME-01 브랜드 스토리 조회 테스트")
    void getBrandStoryTest() {
        BrandStoryResponse response = homeService.getBrandStory();

        assertThat(response).isNotNull();
        assertThat(response.getSlogan()).contains("Generative AI");
        assertThat(response.getMission()).contains("여성 아티스트");
    }

    @Test
    @DisplayName("HOME 메인 통합 데이터 (HOME-01 ~ HOME-04) 조회 테스트")
    void getHomeSummaryTest() {
        HomeSummaryResponse summary = homeService.getHomeSummary();

        assertThat(summary).isNotNull();
        assertThat(summary.getBrandStory()).isNotNull();
        assertThat(summary.getPopularItems()).isNotNull();
        assertThat(summary.getFeaturedArtists()).isNotNull();
        assertThat(summary.getSponsorshipStatus()).isNotNull();
    }
}
