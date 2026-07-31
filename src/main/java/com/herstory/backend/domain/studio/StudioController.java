package com.herstory.backend.domain.studio;

import com.herstory.backend.domain.studio.dto.*;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "3. AI 스튜디오 API (Studio)", description = "아티스트 원화 업로드, AI 패턴 비동기 생성 및 콜백 웹훅")
@RestController
@RequestMapping("/api/v1/studio")
@RequiredArgsConstructor
public class StudioController {

    private final StudioService studioService;

    @Operation(summary = "원화/스케치 등록 (URL 기반)", description = "이미지 URL로 아티스트의 원화 및 스케치를 등록합니다.")
    @PostMapping("/artworks")
    public ResponseEntity<ApiResponse<ArtworkResponse>> uploadArtwork(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ArtworkUploadRequest request) {
        ArtworkResponse response = studioService.uploadArtwork(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("원화 업로드 성공", response));
    }

    @Operation(summary = "원화/스케치 파일 업로드 (Multipart)", description = "로컬 이미지 파일(PNG/JPG)을 업로드하고 로컬/S3 저장소에 저장합니다.")
    @PostMapping(value = "/artworks/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ArtworkResponse>> uploadArtworkFile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestPart("file") MultipartFile file) {
        ArtworkResponse response = studioService.uploadArtworkWithFile(userPrincipal.getId(), title, description, file);
        return ResponseEntity.ok(ApiResponse.success("원화 파일 업로드 성공", response));
    }

    @Operation(summary = "AI 패턴 생성 요청 (비동기)", description = "등록된 원화 ID와 프롬프트를 기반으로 AI 패션 패턴 비동기 생성을 요청합니다 (TaskId 반환).")
    @PostMapping("/patterns/generate")
    public ResponseEntity<ApiResponse<PatternTaskResponse>> generatePattern(
            @Valid @RequestBody PatternGenerateRequest request) {
        PatternTaskResponse response = studioService.requestPatternGeneration(request);
        return ResponseEntity.ok(ApiResponse.success("AI 패턴 생성 요청 등록 성공", response));
    }

    @Operation(summary = "AI 패턴 생성 작업 상태 조회", description = "TaskId로 비동기 AI 생성 작업 상태(PENDING, IN_PROGRESS, COMPLETED, FAILED)를 조회합니다.")
    @GetMapping("/patterns/tasks/{taskId}")
    public ResponseEntity<ApiResponse<PatternTaskResponse>> getTaskStatus(@PathVariable String taskId) {
        PatternTaskResponse response = studioService.getTaskStatus(taskId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "AI 패턴 생성 완료 웹훅 콜백", description = "외부 AI 생성 엔진의 작업 완료/실패 결과를 알림받는 웹훅 엔드포인트입니다.")
    @PostMapping("/patterns/callback")
    public ResponseEntity<ApiResponse<PatternTaskResponse>> patternCallback(
            @Valid @RequestBody PatternCallbackRequest callbackRequest) {
        PatternTaskResponse response = studioService.handlePatternCallback(callbackRequest);
        return ResponseEntity.ok(ApiResponse.success("AI 패턴 생성 콜백 처리 완료", response));
    }

    @Operation(summary = "나의 원화 목록 조회", description = "로그인한 아티스트 본인이 등록한 모든 원화/스케치 목록을 조회합니다.")
    @GetMapping("/artworks/my")
    public ResponseEntity<ApiResponse<List<ArtworkResponse>>> getMyArtworks(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<ArtworkResponse> response = studioService.getArtistArtworks(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "원화별 생성된 AI 패턴 목록 조회", description = "특정 원화에서 파생되어 생성된 AI 패턴 목록을 조회합니다.")
    @GetMapping("/artworks/{artworkId}/patterns")
    public ResponseEntity<ApiResponse<List<PatternResponse>>> getArtworkPatterns(
            @PathVariable Long artworkId) {
        List<PatternResponse> response = studioService.getArtworkPatterns(artworkId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
