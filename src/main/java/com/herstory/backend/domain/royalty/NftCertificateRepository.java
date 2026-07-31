package com.herstory.backend.domain.royalty;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NftCertificateRepository extends JpaRepository<NftCertificate, Long> {
    List<NftCertificate> findByCustomerId(Long customerId);
    Optional<NftCertificate> findByOrderId(Long orderId);
}
