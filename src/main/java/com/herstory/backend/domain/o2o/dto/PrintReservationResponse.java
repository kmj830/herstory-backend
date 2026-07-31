package com.herstory.backend.domain.o2o.dto;

import com.herstory.backend.domain.o2o.PrintReservation;
import com.herstory.backend.domain.o2o.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "친환경 3D 프린팅 수령 예약 정보 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class PrintReservationResponse {

    @Schema(description = "예약 ID", example = "1")
    private Long id;

    @Schema(description = "예약 사용자 ID", example = "2")
    private Long userId;

    @Schema(description = "예약 상품 ID", example = "2")
    private Long showroomItemId;

    @Schema(description = "방문 수령 예약 시간", example = "2026-08-01T15:00:00")
    private LocalDateTime reservationTime;

    @Schema(description = "예약 상태 (RESERVED, COMPLETED, CANCELLED)", example = "RESERVED")
    private ReservationStatus status;

    @Schema(description = "신청 일시", example = "2026-07-31T23:05:00")
    private LocalDateTime createdAt;

    public static PrintReservationResponse from(PrintReservation reservation) {
        return PrintReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUser().getId())
                .showroomItemId(reservation.getShowroomItem().getId())
                .reservationTime(reservation.getReservationTime())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}
