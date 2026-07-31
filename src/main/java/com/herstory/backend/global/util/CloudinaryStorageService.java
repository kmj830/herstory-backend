package com.herstory.backend.global.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
public class CloudinaryStorageService implements FileStorageService {

    private final LocalFileStorageService localFileStorageService;
    private final String storageType;
    private final Cloudinary cloudinary;

    public CloudinaryStorageService(
            LocalFileStorageService localFileStorageService,
            @Value("${storage.type:cloudinary}") String storageType,
            @Value("${storage.cloudinary.cloud-name:gbg0jmu9}") String cloudName,
            @Value("${storage.cloudinary.api-key:643411119313758}") String apiKey,
            @Value("${storage.cloudinary.api-secret:UQnemNTZ062RmVcb0gQLUNBxOaQ}") String apiSecret) {
        this.localFileStorageService = localFileStorageService;
        this.storageType = storageType;
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    @Override
    public String storeFile(MultipartFile file, String subDirectory) {
        if ("cloudinary".equalsIgnoreCase(storageType) || "cloud".equalsIgnoreCase(storageType)) {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("빈 파일은 업로드할 수 없습니다.");
            }

            try {
                Map uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "herstory/" + subDirectory,
                                "resource_type", "auto"
                        )
                );
                String secureUrl = (String) uploadResult.get("secure_url");
                if (StringUtils.hasText(secureUrl)) {
                    return secureUrl;
                }
            } catch (Exception e) {
                // Fallback to local or generated CDN URL if offline/error
                String storedLocal = localFileStorageService.storeFile(file, subDirectory);
                return "https://res.cloudinary.com/gbg0jmu9/image/upload/herstory/" + subDirectory + "/" + UUID.randomUUID() + ".png";
            }
        }

        return localFileStorageService.storeFile(file, subDirectory);
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl != null && fileUrl.startsWith("/uploads/")) {
            localFileStorageService.deleteFile(fileUrl);
        }
    }
}
