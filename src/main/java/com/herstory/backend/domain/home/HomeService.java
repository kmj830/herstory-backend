package com.herstory.backend.domain.home;

import com.herstory.backend.domain.home.dto.*;
import com.herstory.backend.domain.royalty.RoyaltySettlement;
import com.herstory.backend.domain.royalty.RoyaltySettlementRepository;
import com.herstory.backend.domain.showroom.ShowroomItem;
import com.herstory.backend.domain.showroom.ShowroomItemRepository;
import com.herstory.backend.domain.showroom.dto.ShowroomItemResponse;
import com.herstory.backend.domain.studio.Artwork;
import com.herstory.backend.domain.studio.ArtworkRepository;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final ShowroomItemRepository showroomItemRepository;
    private final UserRepository userRepository;
    private final ArtworkRepository artworkRepository;
    private final RoyaltySettlementRepository royaltySettlementRepository;

    public BrandStoryResponse getBrandStory() {
        return BrandStoryResponse.builder()
                .slogan("무명 여성 아티스트 x Generative AI 기반 패션 팝업 플랫폼")
                .mission("숨겨진 여성 아티스트의 예술적 가치를 Generative AI 패션 패턴으로 확장하고 투명한 상생 로열티 생태계를 구축합니다.")
                .aesthetic("한국 전통의 조형적 미학과 선명한 모던 그래픽, 지속 가능한 ESG 패션 3D 렌더링의 융합")
                .impactModel("판매 수익 및 후원금의 15% 이상을 아티스트에게 직접 로열티로 정산하며 블록체인 NFT 보증서로 기여를 증명합니다.")
                .build();
    }

    public List<ShowroomItemResponse> getPopularItems() {
        return showroomItemRepository.findAll().stream()
                .sorted((a, b) -> b.getTotalSponsorshipAmount().compareTo(a.getTotalSponsorshipAmount()))
                .limit(5)
                .map(ShowroomItemResponse::from)
                .toList();
    }

    public List<FeaturedArtistResponse> getFeaturedArtists() {
        List<User> artists = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.ROLE_ARTIST)
                .toList();

        List<FeaturedArtistResponse> result = new ArrayList<>();
        for (User artist : artists) {
            List<Artwork> artworks = artworkRepository.findByArtistId(artist.getId());
            Artwork repArtwork = artworks.isEmpty() ? null : artworks.getFirst();

            result.add(FeaturedArtistResponse.builder()
                    .artistId(artist.getId())
                    .artistName(artist.getName())
                    .profileImageUrl(artist.getProfileImageUrl())
                    .bio(artist.getBio())
                    .artworkCount(artworks.size())
                    .representativeArtworkTitle(repArtwork != null ? repArtwork.getTitle() : "대표 원화 없음")
                    .representativeArtworkUrl(repArtwork != null ? repArtwork.getImageUrl() : null)
                    .build());
        }
        return result;
    }

    public SponsorshipStatusResponse getSponsorshipStatus() {
        List<ShowroomItem> items = showroomItemRepository.findAll();

        BigDecimal totalSponsorship = items.stream()
                .map(ShowroomItem::getTotalSponsorshipAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long totalSponsors = items.stream()
                .mapToLong(ShowroomItem::getSponsorCount)
                .sum();

        BigDecimal totalRoyaltySettled = royaltySettlementRepository.findAll().stream()
                .map(RoyaltySettlement::getSettlementAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long supportedArtists = userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.ROLE_ARTIST)
                .count();

        return SponsorshipStatusResponse.builder()
                .totalSponsorshipAmount(totalSponsorship)
                .totalSponsorCount(totalSponsors)
                .totalRoyaltySettled(totalRoyaltySettled)
                .supportedArtistCount((int) supportedArtists)
                .build();
    }

    public HomeSummaryResponse getHomeSummary() {
        return HomeSummaryResponse.builder()
                .brandStory(getBrandStory())
                .popularItems(getPopularItems())
                .featuredArtists(getFeaturedArtists())
                .sponsorshipStatus(getSponsorshipStatus())
                .build();
    }
}
