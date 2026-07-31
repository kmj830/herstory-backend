package com.herstory.backend.domain.o2o;

import com.herstory.backend.domain.showroom.ShowroomItem;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "print_reservations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PrintReservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_item_id", nullable = false)
    private ShowroomItem showroomItem;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Builder
    public PrintReservation(User user, ShowroomItem showroomItem, LocalDateTime reservationTime, ReservationStatus status) {
        this.user = user;
        this.showroomItem = showroomItem;
        this.reservationTime = reservationTime;
        this.status = status != null ? status : ReservationStatus.RESERVED;
    }

    public void updateStatus(ReservationStatus status) {
        this.status = status;
    }
}
