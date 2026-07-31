package com.herstory.backend.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path baseStorageLocation;

    public LocalFileStorageService(@Value("${file.upload-dir:./uploads}") String uploadDir) {
        this.baseStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.baseStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉터리를 생성할 수 없습니다.", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String subDirectory) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
        }

        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "file");
        String extension = "";
        int extIndex = originalFilename.lastIndexOf(".");
        if (extIndex >= 0) {
            extension = originalFilename.substring(extIndex);
        }

        String storedFileName = UUID.randomUUID().toString() + extension;

        try {
            Path targetDir = this.baseStorageLocation.resolve(subDirectory).normalize();
            Files.createDirectories(targetDir);

            Path targetLocation = targetDir.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + subDirectory + "/" + storedFileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다: " + originalFilename, e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (!StringUtils.hasText(fileUrl) || !fileUrl.startsWith("/uploads/")) {
            return;
        }
        String relativePath = fileUrl.substring("/uploads/".length());
        Path filePath = this.baseStorageLocation.resolve(relativePath).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // log error
        }
    }
}
