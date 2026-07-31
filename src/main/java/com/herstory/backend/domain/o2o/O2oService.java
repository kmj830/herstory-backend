package com.herstory.backend.domain.o2o;

import com.herstory.backend.domain.o2o.dto.*;
import com.herstory.backend.domain.showroom.CustomDesign;
import com.herstory.backend.domain.showroom.CustomDesignRepository;
import com.herstory.backend.domain.showroom.ShowroomItem;
import com.herstory.backend.domain.showroom.ShowroomItemRepository;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class O2oService {

    private final MediaWallPostRepository mediaWallPostRepository;
    private final PrintReservationRepository printReservationRepository;
    private final UserRepository userRepository;
    private final CustomDesignRepository customDesignRepository;
    private final ShowroomItemRepository showroomItemRepository;

    public PopupStoreInfoResponse getPopupStoreInfo() {
        return PopupStoreInfoResponse.builder()
                .name("HER-STORY Seongsu Flagship Popup Store")
                .location("서울특별시 성동구 성수이로 123")
                .operatingHours("11:00 - 20:00")
                .livePrintStatus("OPERATING")
                .waitingQueueCount(3)
                .build();
    }

    @Transactional
    public MediaWallPostResponse sendToMediaWall(Long userId, MediaWallPostSendRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        CustomDesign customDesign = null;
        if (request.getCustomDesignId() != null) {
            customDesign = customDesignRepository.findById(request.getCustomDesignId())
                    .orElseThrow(() -> new IllegalArgumentException("커스텀 디자인을 찾을 수 없습니다."));
        }

        MediaWallPost post = MediaWallPost.builder()
                .user(user)
                .customDesign(customDesign)
                .message(request.getMessage())
                .displayStatus(DisplayStatus.WAITING)
                .build();

        return MediaWallPostResponse.from(mediaWallPostRepository.save(post));
    }

    @Transactional
    public PrintReservationResponse createPrintReservation(Long userId, PrintReservationCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ShowroomItem item = showroomItemRepository.findById(request.getShowroomItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        PrintReservation reservation = PrintReservation.builder()
                .user(user)
                .showroomItem(item)
                .reservationTime(request.getReservationTime())
                .status(ReservationStatus.RESERVED)
                .build();

        return PrintReservationResponse.from(printReservationRepository.save(reservation));
    }

    public List<PrintReservationResponse> getUserReservations(Long userId) {
        return printReservationRepository.findByUserId(userId).stream()
                .map(PrintReservationResponse::from)
                .toList();
    }
}
