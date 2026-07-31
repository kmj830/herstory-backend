package com.herstory.backend.domain.studio;

import com.herstory.backend.domain.studio.dto.*;
import com.herstory.backend.domain.user.Role;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StudioServiceTest {

    @Autowired
    private StudioService studioService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private AiPatternTaskRepository aiPatternTaskRepository;

    private User artist;

    @BeforeEach
    void setUp() {
        artist = userRepository.save(User.builder()
                .email("test-artist-studio@herstory.com")
                .password("password123")
                .name("Artist One")
                .role(Role.ROLE_ARTIST)
                .build());
    }

    @Test
    @DisplayName("원화 이미지 파일 업로드 테스트")
    void uploadArtworkWithFileTest() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sketch.png",
                "image/png",
                "mock image content".getBytes()
        );

        ArtworkResponse response = studioService.uploadArtworkWithFile(
                artist.getId(),
                "My Artwork Sketch",
                "Hand drawn sketch for AI pattern",
                file
        );

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("My Artwork Sketch");
        assertThat(response.getImageUrl()).contains("artworks");
    }

    @Test
    @DisplayName("AI 패턴 생성 비동기 요청 및 콜백 웹훅 처리 테스트")
    void aiPatternGenerationTaskAndCallbackTest() {
        Artwork artwork = artworkRepository.save(Artwork.builder()
                .artist(artist)
                .title("Flower Sketch")
                .imageUrl("/uploads/artworks/flower.png")
                .build());

        PatternGenerateRequest generateRequest = PatternGenerateRequest.builder()
                .artworkId(artwork.getId())
                .patternName("Floral Silk Pattern")
                .prompt("vibrant floral pattern on silk texture")
                .build();

        PatternTaskResponse taskResponse = studioService.requestPatternGeneration(generateRequest);

        assertThat(taskResponse).isNotNull();
        assertThat(taskResponse.getTaskId()).startsWith("TASK-");

        PatternCallbackRequest callbackRequest = PatternCallbackRequest.builder()
                .taskId(taskResponse.getTaskId())
                .status(PatternTaskStatus.COMPLETED)
                .patternImageUrl("/uploads/patterns/result_floral.png")
                .build();

        PatternTaskResponse callbackResult = studioService.handlePatternCallback(callbackRequest);

        assertThat(callbackResult.getStatus()).isEqualTo(PatternTaskStatus.COMPLETED);
        assertThat(callbackResult.getGeneratedPatternId()).isNotNull();
    }
}
