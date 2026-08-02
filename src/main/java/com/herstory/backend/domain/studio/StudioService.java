package com.herstory.backend.domain.studio;

import com.herstory.backend.domain.studio.dto.*;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import com.herstory.backend.global.util.FileStorageService;
import com.herstory.backend.global.util.OpenAiImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudioService {

    private final ArtworkRepository artworkRepository;
    private final AiPatternRepository aiPatternRepository;
    private final AiPatternTaskRepository aiPatternTaskRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final OpenAiImageService openAiImageService;

    @Transactional
    public ArtworkResponse uploadArtwork(Long artistId, ArtworkUploadRequest request) {
        User artist = userRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트를 찾을 수 없습니다."));

        Artwork artwork = Artwork.builder()
                .artist(artist)
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();

        return ArtworkResponse.from(artworkRepository.save(artwork));
    }

    @Transactional
    public ArtworkResponse uploadArtworkWithFile(Long artistId, String title, String description, MultipartFile file) {
        User artist = userRepository.findById(artistId)
                .orElseThrow(() -> new IllegalArgumentException("아티스트를 찾을 수 없습니다."));

        String fileUrl = fileStorageService.storeFile(file, "artworks");

        Artwork artwork = Artwork.builder()
                .artist(artist)
                .title(title)
                .description(description)
                .imageUrl(fileUrl)
                .build();

        return ArtworkResponse.from(artworkRepository.save(artwork));
    }

    @Transactional
    public PatternTaskResponse requestPatternGeneration(PatternGenerateRequest request) {
        Artwork artwork = artworkRepository.findById(request.getArtworkId())
                .orElseThrow(() -> new IllegalArgumentException("원화를 찾을 수 없습니다."));

        String taskId = "TASK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        AiPatternTask task = AiPatternTask.builder()
                .taskId(taskId)
                .artwork(artwork)
                .patternName(request.getPatternName())
                .prompt(request.getPrompt())
                .status(PatternTaskStatus.PENDING)
                .build();

        aiPatternTaskRepository.save(task);

        // Async trigger simulation
        processAiGenerationAsync(taskId);

        return PatternTaskResponse.from(task);
    }

    @Async
    @Transactional
    public void processAiGenerationAsync(String taskId) {
        aiPatternTaskRepository.findById(taskId).ifPresent(task -> {
            task.updateStatus(PatternTaskStatus.IN_PROGRESS);
            aiPatternTaskRepository.save(task);

            Artwork artwork = task.getArtwork();
            String artworkTitle = artwork != null ? artwork.getTitle() : "Original Artwork";
            String artworkDesc = artwork != null ? artwork.getDescription() : "";
            String artworkUrl = artwork != null ? artwork.getImageUrl() : "";

            String generatedPatternUrl = openAiImageService.generateFashionPatternImage(artworkTitle, artworkDesc, artworkUrl, task.getPrompt());


            AiPattern pattern = AiPattern.builder()
                    .artwork(task.getArtwork())
                    .patternName(task.getPatternName())
                    .patternImageUrl(generatedPatternUrl)
                    .prompt(task.getPrompt())
                    .build();

            AiPattern savedPattern = aiPatternRepository.save(pattern);
            task.markAsCompleted(generatedPatternUrl, savedPattern);
            aiPatternTaskRepository.save(task);
        });
    }


    @Transactional
    public PatternTaskResponse handlePatternCallback(PatternCallbackRequest callbackRequest) {
        AiPatternTask task = aiPatternTaskRepository.findById(callbackRequest.getTaskId())
                .orElseThrow(() -> new IllegalArgumentException("해당 작업을 찾을 수 없습니다: " + callbackRequest.getTaskId()));

        if (callbackRequest.getStatus() == PatternTaskStatus.COMPLETED) {
            String patternUrl = callbackRequest.getPatternImageUrl() != null
                    ? callbackRequest.getPatternImageUrl()
                    : "/uploads/patterns/pattern_" + System.currentTimeMillis() + ".png";

            AiPattern pattern = AiPattern.builder()
                    .artwork(task.getArtwork())
                    .patternName(task.getPatternName())
                    .patternImageUrl(patternUrl)
                    .prompt(task.getPrompt())
                    .build();

            AiPattern savedPattern = aiPatternRepository.save(pattern);
            task.markAsCompleted(patternUrl, savedPattern);
        } else if (callbackRequest.getStatus() == PatternTaskStatus.FAILED) {
            task.markAsFailed(callbackRequest.getErrorMessage());
        } else {
            task.updateStatus(callbackRequest.getStatus());
        }

        return PatternTaskResponse.from(aiPatternTaskRepository.save(task));
    }

    public PatternTaskResponse getTaskStatus(String taskId) {
        AiPatternTask task = aiPatternTaskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("해당 작업을 찾을 수 없습니다: " + taskId));
        return PatternTaskResponse.from(task);
    }

    public List<ArtworkResponse> getArtistArtworks(Long artistId) {
        return artworkRepository.findByArtistId(artistId).stream()
                .map(ArtworkResponse::from)
                .toList();
    }

    public List<PatternResponse> getArtworkPatterns(Long artworkId) {
        return aiPatternRepository.findByArtworkId(artworkId).stream()
                .map(PatternResponse::from)
                .toList();
    }
}
