package org.likelion.likelionrecrud.order.api.response;

import java.util.List;

// 주문 정보 응답 데이터
public record OrderInfoResDto(
        Long memberId,
        Long orderId,
        OrderItemResDto orderItems
) {
}

