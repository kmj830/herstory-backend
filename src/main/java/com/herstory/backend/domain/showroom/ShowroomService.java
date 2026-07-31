package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.showroom.dto.*;
import com.herstory.backend.domain.studio.AiPattern;
import com.herstory.backend.domain.studio.AiPatternRepository;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowroomService {

    private final ShowroomItemRepository showroomItemRepository;
    private final AiPatternRepository aiPatternRepository;
    private final CustomDesignRepository customDesignRepository;
    private final UserRepository userRepository;

    @Transactional
    public ShowroomItemResponse registerItem(ShowroomItemRegisterRequest request) {
        AiPattern pattern = aiPatternRepository.findById(request.getAiPatternId())
                .orElseThrow(() -> new IllegalArgumentException("AI 패턴을 찾을 수 없습니다."));

        ShowroomItem item = ShowroomItem.builder()
                .aiPattern(pattern)
                .title(request.getTitle())
                .price(request.getPrice())
                .description(request.getDescription())
                .rendering3dUrl(request.getRendering3dUrl() != null ? request.getRendering3dUrl() : "https://cdn.herstory.com/3d/default.gltf")
                .build();

        return ShowroomItemResponse.from(showroomItemRepository.save(item));
    }

    public List<ShowroomItemResponse> getAllItems() {
        return showroomItemRepository.findAll().stream()
                .map(ShowroomItemResponse::from)
                .toList();
    }

    public ShowroomItemResponse getItem(Long itemId) {
        ShowroomItem item = showroomItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        return ShowroomItemResponse.from(item);
    }

    public List<ShowroomItemResponse> searchItems(String keyword, BigDecimal minPrice, BigDecimal maxPrice, String sortBy) {
        Stream<ShowroomItem> stream = showroomItemRepository.findAll().stream();

        if (StringUtils.hasText(keyword)) {
            String lowerKeyword = keyword.toLowerCase();
            stream = stream.filter(item ->
                    (item.getTitle() != null && item.getTitle().toLowerCase().contains(lowerKeyword)) ||
                    (item.getDescription() != null && item.getDescription().toLowerCase().contains(lowerKeyword)) ||
                    (item.getAiPattern() != null && item.getAiPattern().getPatternName() != null && item.getAiPattern().getPatternName().toLowerCase().contains(lowerKeyword))
            );
        }

        if (minPrice != null) {
            stream = stream.filter(item -> item.getPrice().compareTo(minPrice) >= 0);
        }

        if (maxPrice != null) {
            stream = stream.filter(item -> item.getPrice().compareTo(maxPrice) <= 0);
        }

        if ("priceAsc".equalsIgnoreCase(sortBy)) {
            stream = stream.sorted(Comparator.comparing(ShowroomItem::getPrice));
        } else if ("priceDesc".equalsIgnoreCase(sortBy)) {
            stream = stream.sorted(Comparator.comparing(ShowroomItem::getPrice).reversed());
        } else if ("popular".equalsIgnoreCase(sortBy) || "sponsorship".equalsIgnoreCase(sortBy)) {
            stream = stream.sorted(Comparator.comparing(ShowroomItem::getTotalSponsorshipAmount).reversed());
        } else {
            stream = stream.sorted(Comparator.comparing(ShowroomItem::getId).reversed());
        }

        return stream.map(ShowroomItemResponse::from).toList();
    }

    @Transactional
    public CustomDesignResponse createCustomDesign(Long customerId, CustomDesignCreateRequest request) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ShowroomItem item = showroomItemRepository.findById(request.getShowroomItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        CustomDesign customDesign = CustomDesign.builder()
                .customer(customer)
                .showroomItem(item)
                .customColor(request.getCustomColor())
                .fit(request.getFit())
                .patternPlacement(request.getPatternPlacement())
                .build();

        return CustomDesignResponse.from(customDesignRepository.save(customDesign));
    }
}
