package com.herstory.backend.domain.o2o.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "O2O 팝업스토어 라이브 모니터링 및 안내 응답 DTO")
@Getter
@Builder
@AllArgsConstructor
public class PopupStoreInfoResponse {

    @Schema(description = "팝업스토어 명칭", example = "HER-STORY Seongsu Flagship Popup Store")
    private String name;

    @Schema(description = "오프라인 위치 주소", example = "서울특별시 성동구 성수이로 123")
    private String location;

    @Schema(description = "운영 시간 안내", example = "11:00 - 20:00")
    private String operatingHours;

    @Schema(description = "실시간 3D 프린터 가동 상태", example = "OPERATING")
    private String livePrintStatus;

    @Schema(description = "현재 현장 대기 인원 수", example = "3")
    private int waitingQueueCount;
}
