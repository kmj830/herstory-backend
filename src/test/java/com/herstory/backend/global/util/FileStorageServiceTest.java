package com.herstory.backend.global.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    @DisplayName("파일 업로드 스토리지 테스트")
    void storeFileTest() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test-artwork.png",
                "image/png",
                "Test Image Content".getBytes()
        );

        String fileUrl = fileStorageService.storeFile(mockFile, "artworks");

        assertThat(fileUrl).isNotBlank();
        assertThat(fileUrl).contains("artworks");
    }
}
