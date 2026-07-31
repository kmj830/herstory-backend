package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.mypage.dto.*;
import com.herstory.backend.domain.royalty.NftCertificateRepository;
import com.herstory.backend.domain.royalty.RoyaltySettlement;
import com.herstory.backend.domain.royalty.RoyaltySettlementRepository;
import com.herstory.backend.domain.royalty.dto.NftCertificateResponse;
import com.herstory.backend.domain.royalty.dto.RoyaltySettlementResponse;
import com.herstory.backend.domain.showroom.OrderRepository;
import com.herstory.backend.domain.showroom.ShowroomItem;
import com.herstory.backend.domain.showroom.ShowroomItemRepository;
import com.herstory.backend.domain.showroom.dto.OrderResponse;
import com.herstory.backend.domain.studio.AiPatternRepository;
import com.herstory.backend.domain.studio.Artwork;
import com.herstory.backend.domain.studio.ArtworkRepository;
import com.herstory.backend.domain.studio.dto.ArtworkResponse;
import com.herstory.backend.domain.studio.dto.PatternResponse;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import com.herstory.backend.domain.user.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final ArtworkRepository artworkRepository;
    private final AiPatternRepository aiPatternRepository;
    private final ShowroomItemRepository showroomItemRepository;
    private final OrderRepository orderRepository;
    private final RoyaltySettlementRepository royaltySettlementRepository;
    private final NftCertificateRepository nftCertificateRepository;
    private final WishlistRepository wishlistRepository;
    private final QnaInquiryRepository qnaInquiryRepository;
    private final MentoringApplicationRepository mentoringApplicationRepository;

    public MyPageSummaryResponse getMyPageSummary(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));

        UserProfileResponse userProfile = UserProfileResponse.from(user);

        ArtistDashboardResponse artistDashboard = null;
        if (user.getRole() == Role.ROLE_ARTIST || user.getRole() == Role.ROLE_ADMIN) {
            artistDashboard = getArtistDashboard(userId);
        }

        CustomerDashboardResponse customerDashboard = getCustomerDashboard(userId);

        List<QnaInquiryResponse> qnaInquiries = qnaInquiryRepository.findByUserId(userId).stream()
                .map(QnaInquiryResponse::from)
                .toList();

        return MyPageSummaryResponse.builder()
                .userProfile(userProfile)
                .artistDashboard(artistDashboard)
                .customerDashboard(customerDashboard)
                .qnaInquiries(qnaInquiries)
                .build();
    }

    public ArtistDashboardResponse getArtistDashboard(Long artistId) {
        List<ArtworkResponse> myArtworks = artworkRepository.findByArtistId(artistId).stream()
                .map(ArtworkResponse::from)
                .toList();

        List<PatternResponse> myPatterns = new ArrayList<>();
        for (ArtworkResponse artwork : myArtworks) {
            myPatterns.addAll(aiPatternRepository.findByArtworkId(artwork.getId()).stream()
                    .map(PatternResponse::from)
                    .toList());
        }

        List<RoyaltySettlement> settlements = royaltySettlementRepository.findByArtistId(artistId);
        List<RoyaltySettlementResponse> settlementResponses = settlements.stream()
                .map(RoyaltySettlementResponse::from)
                .toList();

        BigDecimal totalSales = settlements.stream()
                .map(RoyaltySettlement::getTotalSalesAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalRoyalty = settlements.stream()
                .map(RoyaltySettlement::getSettlementAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<MentoringResponse> mentorings = mentoringApplicationRepository.findByArtistId(artistId).stream()
                .map(MentoringResponse::from)
                .toList();

        return ArtistDashboardResponse.builder()
                .myArtworks(myArtworks)
                .myPatterns(myPatterns)
                .totalSalesAmount(totalSales)
                .totalRoyaltyAmount(totalRoyalty)
                .withdrawableAmount(totalRoyalty)
                .settlementHistory(settlementResponses)
                .mentoringApplications(mentorings)
                .build();
    }

    public CustomerDashboardResponse getCustomerDashboard(Long customerId) {
        List<OrderResponse> orders = orderRepository.findByCustomerId(customerId).stream()
                .map(OrderResponse::from)
                .toList();

        List<NftCertificateResponse> wallet = nftCertificateRepository.findByCustomerId(customerId).stream()
                .map(NftCertificateResponse::from)
                .toList();

        List<WishlistResponse> wishlist = wishlistRepository.findByUserId(customerId).stream()
                .map(WishlistResponse::from)
                .toList();

        return CustomerDashboardResponse.builder()
                .myOrders(orders)
                .nftWallet(wallet)
                .wishlist(wishlist)
                .build();
    }

    @Transactional
    public WishlistResponse addWishlist(Long userId, WishlistRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ShowroomItem item = null;
        if (request.getShowroomItemId() != null) {
            item = showroomItemRepository.findById(request.getShowroomItemId()).orElse(null);
        }

        User artist = null;
        if (request.getArtistId() != null) {
            artist = userRepository.findById(request.getArtistId()).orElse(null);
        }

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .showroomItem(item)
                .artist(artist)
                .build();

        return WishlistResponse.from(wishlistRepository.save(wishlist));
    }

    @Transactional
    public void deleteWishlist(Long userId, Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("위시리스트 항목을 찾을 수 없습니다."));

        if (!wishlist.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 위시리스트 항목만 삭제할 수 있습니다.");
        }

        wishlistRepository.delete(wishlist);
    }

    @Transactional
    public QnaInquiryResponse createQnaInquiry(Long userId, QnaInquiryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        QnaInquiry inquiry = QnaInquiry.builder()
                .user(user)
                .category(request.getCategory())
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        return QnaInquiryResponse.from(qnaInquiryRepository.save(inquiry));
    }

    @Transactional
    public MentoringResponse applyMentoring(Long artistId, MentoringRequest request) {
        User artist = userRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트를 찾을 수 없습니다."));

        MentoringApplication application = MentoringApplication.builder()
                .artist(artist)
                .programName(request.getProgramName())
                .topic(request.getTopic())
                .status("APPROVED")
                .build();

        return MentoringResponse.from(mentoringApplicationRepository.save(application));
    }

    @Transactional
    public UserProfileResponse updateProfile(Long userId, String name, String bio, String profileImageUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.updateProfile(name, bio, profileImageUrl);
        return UserProfileResponse.from(user);
    }
}
