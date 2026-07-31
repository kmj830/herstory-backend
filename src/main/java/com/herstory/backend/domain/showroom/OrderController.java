package com.herstory.backend.domain.showroom;

import com.herstory.backend.domain.showroom.dto.OrderCreateRequest;
import com.herstory.backend.domain.showroom.dto.OrderResponse;
import com.herstory.backend.global.common.ApiResponse;
import com.herstory.backend.global.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "5. 주문 및 아티스트 후원 API (Order)", description = "상품 주문 및 아티스트 추가 후원금 결제 처리")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 결제 및 후원금 처리", description = "3D 쇼룸 커스텀 상품 결제 시 아티스트 추가 후원금 옵션을 함께 결제합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody OrderCreateRequest request) {
        OrderResponse response = orderService.createOrder(userPrincipal.getId(), request);
        return ResponseEntity.ok(ApiResponse.success("주문 및 결제 처리 완료", response));
    }

    @Operation(summary = "나의 주문 내역 조회", description = "로그인한 고객의 과거 주문/결제 내역 및 배송 상태를 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getMyOrders(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<OrderResponse> orders = orderService.getCustomerOrders(userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success(orders));
    }
}
