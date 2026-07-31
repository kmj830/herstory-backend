package com.herstory.backend.domain.o2o.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "친환경 3D 프린팅 수령 예약 요청 DTO")
@Getter
@NoArgsConstructor
public class PrintReservationCreateRequest {

    @Schema(description = "3D 프린팅 수령 대상 쇼룸 상품 ID", example = "2")
    @NotNull(message = "쇼룸 아이템 ID는 필수입니다.")
    private Long showroomItemId;

    @Schema(description = "팝업스토어 현장 방문 수령 예약 시간", example = "2026-08-01T15:00:00")
    @NotNull(message = "예약 시간은 필수입니다.")
    private LocalDateTime reservationTime;
}
