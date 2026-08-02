# HER-STORY Backend Development Guide

본 프로젝트는 **해커톤 대회 출품**을 목표로 구축되는 `REQUIREMENTS.md` 기반 Spring Boot 백엔드 애플리케이션입니다.

## 🏆 프로젝트 주요 특징 & 방침
- **목적**: 해커톤 출품 및 완성도 높은 시연(Demo) 및 프레젠테이션
- **AI 연동**: OpenAI API (Image Generation / DALL-E) 기반 패턴 생성
- **결제 연동**: 해커톤 시연을 위한 Mock/시뮬레이션 결제 (실제 PG 결제 모듈 미연동)

## 개발 스택
- Java 21
- Spring Boot 3.x
- Gradle
- Spring Data JPA
- Spring Security (JWT)
- OpenAI API Client
- PostgreSQL (Local test: H2)
- Lombok

## 백엔드 패키지 구성 가이드
- `com.herstory.backend.domain.user` (Auth, Account, Role)
- `com.herstory.backend.domain.studio` (Upload, AI Pattern, 3D Showroom Registration)
- `com.herstory.backend.domain.showroom` (Custom, Order, Payment, Sponsorship)
- `com.herstory.backend.domain.royalty` (Settlement, NFT Metadata/Certificate)
- `com.herstory.backend.domain.o2o` (Media Wall, 3D Print Reservation)
- `com.herstory.backend.global` (Security, Config, Exception, Common DTO)