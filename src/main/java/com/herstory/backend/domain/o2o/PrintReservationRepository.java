package com.herstory.backend.domain.o2o;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrintReservationRepository extends JpaRepository<PrintReservation, Long> {
    List<PrintReservation> findByUserId(Long userId);
}
