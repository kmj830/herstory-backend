# HER-STORY Backend Development Guide

본 프로젝트는 `REQUIREMENTS.md` 문서를 기반으로 구축되는 Spring Boot 백엔드 애플리케이션입니다.

## 개발 스택
- Java 21
- Spring Boot 3.x
- Gradle
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL (Local test: H2)
- Lombok

## 백엔드 패키지 구성 가이드
- `com.herstory.backend.domain.user` (Auth, Account, Role)
- `com.herstory.backend.domain.studio` (Upload, AI Pattern, 3D Showroom Registration)
- `com.herstory.backend.domain.showroom` (Custom, Order, Payment, Sponsorship)
- `com.herstory.backend.domain.royalty` (Settlement, NFT Metadata/Certificate)
- `com.herstory.backend.domain.o2o` (Media Wall, 3D Print Reservation)
- `com.herstory.backend.global` (Security, Config, Exception, Common DTO)