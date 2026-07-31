package com.herstory.backend.global.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String directory);
    void deleteFile(String fileUrl);
}
