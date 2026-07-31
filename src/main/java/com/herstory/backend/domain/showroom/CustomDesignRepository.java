package com.herstory.backend.domain.showroom;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomDesignRepository extends JpaRepository<CustomDesign, Long> {
    List<CustomDesign> findByCustomerId(Long customerId);
}
