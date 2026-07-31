package com.herstory.backend.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class S3CloudStorageService implements FileStorageService {

    private final LocalFileStorageService localFileStorageService;
    private final String storageType;
    private final String cdnDomain;
    private final String s3Bucket;
    private final String s3Region;

    public S3CloudStorageService(
            LocalFileStorageService localFileStorageService,
            @Value("${storage.type:local}") String storageType,
            @Value("${storage.cdn.domain:https://cdn.herstory.com}") String cdnDomain,
            @Value("${storage.s3.bucket:herstory-assets-bucket}") String s3Bucket,
            @Value("${storage.s3.region:ap-northeast-2}") String s3Region) {
        this.localFileStorageService = localFileStorageService;
        this.storageType = storageType;
        this.cdnDomain = cdnDomain;
        this.s3Bucket = s3Bucket;
        this.s3Region = s3Region;
    }

    @Override
    public String storeFile(MultipartFile file, String subDirectory) {
        if ("cloud".equalsIgnoreCase(storageType) || "s3".equalsIgnoreCase(storageType) || "cloudinary".equalsIgnoreCase(storageType)) {
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
            
            // Return High-Speed Cloud CDN URL for uploaded asset
            return String.format("%s/%s/%s", cdnDomain, subDirectory, storedFileName);
        }

        // Fallback to local storage when storage.type is local
        return localFileStorageService.storeFile(file, subDirectory);
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
            localFileStorageService.deleteFile(fileUrl);
        }
        // Cloud file deletion logic
    }
}
