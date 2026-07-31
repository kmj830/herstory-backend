package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.showroom.dto.ShowroomItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ShowroomSearchTest {

    @Autowired
    private ShowroomService showroomService;

    @Test
    @DisplayName("3D 쇼룸 키워드 검색 및 정렬 필터링 테스트")
    void searchItemsTest() {
        List<ShowroomItemResponse> items = showroomService.searchItems("단청", null, null, "popular");

        assertThat(items).isNotNull();
        assertThat(items).allMatch(item ->
                (item.getTitle() != null && item.getTitle().contains("단청")) ||
                (item.getDescription() != null && item.getDescription().contains("단청"))
        );
    }

    @Test
    @DisplayName("3D 쇼룸 가격 필터링 테스트")
    void searchPriceFilterTest() {
        BigDecimal min = new BigDecimal("150000");
        BigDecimal max = new BigDecimal("300000");

        List<ShowroomItemResponse> items = showroomService.searchItems(null, min, max, "priceAsc");

        assertThat(items).isNotNull();
        assertThat(items).allMatch(item ->
                item.getPrice().compareTo(min) >= 0 && item.getPrice().compareTo(max) <= 0
        );
    }
}
