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

    @Value("${OPENAI_API_KEY:${openai.api-key:}}")
    private String apiKey;

    @Value("${OPENAI_ORGANIZATION_ID:${openai.organization-id:}}")
    private String organizationId;

    @Value("${openai.model:dall-e-3}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateFashionPatternImage(String artworkTitle, String userPrompt) {
        String cleanApiKey = apiKey != null ? apiKey.trim() : "";
        String cleanOrgId = organizationId != null ? organizationId.trim() : "";

        if (cleanApiKey.isBlank() || cleanApiKey.startsWith("sk-your")) {
            log.warn("OpenAI API Key가 설정되지 않았습니다. (cleanApiKey='{}') 기본 패턴 이미지를 반환합니다.", cleanApiKey);
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
            headers.setBearerAuth(cleanApiKey);
            if (!cleanOrgId.isBlank()) {
                headers.set("OpenAI-Organization", cleanOrgId);
            }

            Map<String, Object> body = Map.of(
                    "model", model,
                    "prompt", fullPrompt,
                    "n", 1,
                    "size", "1024x1024"
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            log.info("OpenAI DALL-E 3 패턴 생성 요청 시작: model={}, org={}, prompt='{}'", model, cleanOrgId, fullPrompt);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> data = (List<Map<String, Object>>) response.getBody().get("data");
                if (data != null && !data.isEmpty()) {
                    String imageUrl = (String) data.get(0).get("url");
                    log.info("OpenAI DALL-E 3 패턴 생성 성공: {}", imageUrl);
                    return imageUrl;
                }
            }
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            log.error("OpenAI API 호출 실패 HttpStatusCodeException: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("OpenAI DALL-E 3 이미지 생성 일반 실패: {}", e.getMessage(), e);
        }

        return generateFluxAiPatternUrl(userPrompt != null ? userPrompt : artworkTitle);
    }

    private String generateFluxAiPatternUrl(String prompt) {
        try {
            String fullPrompt = String.format(
                    "High quality seamless fashion textile pattern inspired by '%s'. Elegant 8k resolution digital fashion garment pattern.",
                    prompt != null ? prompt : "Korean traditional fashion pattern"
            );
            String encodedPrompt = java.net.URLEncoder.encode(fullPrompt, java.nio.charset.StandardCharsets.UTF_8);
            long seed = Math.abs((prompt != null ? prompt : "pattern").hashCode()) + System.currentTimeMillis() % 1000;
            String fluxUrl = "https://image.pollinations.ai/prompt/" + encodedPrompt + "?model=flux&width=1024&height=1024&nologo=true&seed=" + seed;
            log.info("FLUX.1 오픈소스 AI 패턴 생성 완료: URL={}", fluxUrl);
            return fluxUrl;
        } catch (Exception e) {
            log.error("FLUX.1 URL 생성 실패: {}", e.getMessage());
            return "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800";
        }
    }
}

