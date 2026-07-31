package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.notification.NotificationService;
import com.herstory.backend.domain.notification.NotificationType;
import com.herstory.backend.domain.showroom.dto.OrderCreateRequest;
import com.herstory.backend.domain.showroom.dto.OrderResponse;
import com.herstory.backend.domain.user.User;
import com.herstory.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ShowroomItemRepository showroomItemRepository;
    private final CustomDesignRepository customDesignRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public OrderResponse createOrder(Long customerId, OrderCreateRequest request) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ShowroomItem item = showroomItemRepository.findById(request.getShowroomItemId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        CustomDesign customDesign = null;
        if (request.getCustomDesignId() != null) {
            customDesign = customDesignRepository.findById(request.getCustomDesignId())
                    .orElseThrow(() -> new IllegalArgumentException("커스텀 디자인을 찾을 수 없습니다."));
        }

        BigDecimal sponsorship = request.getSponsorshipAmount() != null ? request.getSponsorshipAmount() : BigDecimal.ZERO;
        BigDecimal totalAmount = item.getPrice().add(sponsorship);

        if (sponsorship.compareTo(BigDecimal.ZERO) > 0) {
            item.addSponsorship(sponsorship);
        }

        Order order = Order.builder()
                .customer(customer)
                .showroomItem(item)
                .customDesign(customDesign)
                .amount(totalAmount)
                .sponsorshipAmount(sponsorship)
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PAID)
                .build();

        Order savedOrder = orderRepository.save(order);

        // Send Notification to Customer
        notificationService.sendNotification(
                customer,
                "주문 및 결제 완료 안내",
                "[" + item.getTitle() + "] 상품 주문이 성공적으로 완료되었습니다.",
                NotificationType.ORDER_PAID,
                "/mypage/customer-dashboard"
        );

        // Send Notification to Artist if sponsorship exists
        if (sponsorship.compareTo(BigDecimal.ZERO) > 0) {
            User artist = item.getAiPattern().getArtwork().getArtist();
            notificationService.sendNotification(
                    artist,
                    "아티스트 후원금 수령 알림!",
                    customer.getName() + " 님이 아티스트님의 패턴 작품에 " + sponsorship + "원의 후원금을 결제하셨습니다.",
                    NotificationType.SPONSORSHIP_RECEIVED,
                    "/mypage/artist-dashboard"
            );
        }

        return OrderResponse.from(savedOrder);
    }

    public List<OrderResponse> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId).stream()
                .map(OrderResponse::from)
                .toList();
    }
}
