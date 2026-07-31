package com.herstory.backend.domain.home.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "HOME-01 브랜드 스토리 안내 DTO")
@Getter
@Builder
@AllArgsConstructor
public class BrandStoryResponse {

    @Schema(description = "브랜드 슬로건", example = "무명 여성 아티스트 x Generative AI 기반 패션 팝업 플랫폼")
    private String slogan;

    @Schema(description = "브랜드 미션", example = "숨겨진 여성 아티스트의 예술적 가치를 Generative AI 패션 패턴으로 확장하고 투명한 상생 로열티 생태계를 구축합니다.")
    private String mission;

    @Schema(description = "디자인 미학 안내", example = "한국 전통의 조형적 미학과 선명한 모던 그래픽, 지속 가능한 ESG 패션 3D 렌더링의 융합")
    private String aesthetic;

    @Schema(description = "아티스트 상생 모델", example = "판매 수익 및 후원금의 15% 이상을 아티스트에게 직접 로열티로 정산하며 블록체인 NFT 보증서로 기여를 증명합니다.")
    private String impactModel;
}
