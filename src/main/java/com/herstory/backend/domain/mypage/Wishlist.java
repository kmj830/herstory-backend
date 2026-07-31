package com.herstory.backend.domain.mypage;

import com.herstory.backend.domain.showroom.ShowroomItem;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wishlists")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_item_id")
    private ShowroomItem showroomItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private User artist;

    @Builder
    public Wishlist(User user, ShowroomItem showroomItem, User artist) {
        this.user = user;
        this.showroomItem = showroomItem;
        this.artist = artist;
    }
}
