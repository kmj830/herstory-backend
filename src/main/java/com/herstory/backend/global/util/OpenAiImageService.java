package com.herstory.backend.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiImageService {

    @Value("${openai.api-key:}")
    private String apiKey;

    @Value("${openai.organization-id:}")
    private String organizationId;

    @Value("${openai.model:dall-e-3}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateFashionPatternImage(String artworkTitle, String userPrompt) {
        if (apiKey == null || apiKey.isBlank() || apiKey.startsWith("sk-your")) {
            log.warn("OpenAI API Key가 설정되지 않았습니다. 기본 패턴 이미지를 반환합니다.");
            return generateFallbackPatternUrl(artworkTitle);
        }

        String fullPrompt = String.format(
                "High quality seamless fashion textile pattern inspired by artwork titled '%s'. Style: %s. Suitable for high-end digital fashion garments and 3D showroom.",
                artworkTitle != null ? artworkTitle : "Artwork",
                userPrompt != null && !userPrompt.isBlank() ? userPrompt : "elegant modern aesthetic"
        );

        try {
            String url = "https://api.openai.com/v1/images/generations";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);
            if (organizationId != null && !organizationId.isBlank()) {
                headers.set("OpenAI-Organization", organizationId);
            }

            Map<String, Object> body = Map.of(
                    "model", model,
                    "prompt", fullPrompt,
                    "n", 1,
                    "size", "1024x1024"
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            log.info("OpenAI DALL-E 3 패턴 생성 요청 시작: prompt='{}'", fullPrompt);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
                if (data != null && !data.isEmpty()) {
                    String imageUrl = (String) data.get(0).get("url");
                    log.info("OpenAI DALL-E 3 패턴 생성 성공: {}", imageUrl);
                    return imageUrl;
                }
            }
        } catch (Exception e) {
            log.error("OpenAI DALL-E 3 이미지 생성 실패 (폴백 이미지 적용): {}", e.getMessage());
        }

        return generateFallbackPatternUrl(artworkTitle);
    }

    private String generateFallbackPatternUrl(String title) {
        return "https://images.unsplash.com/photo-1579783902614-a3fb3927b675?w=800&auto=format&fit=crop&q=80";
    }
}
