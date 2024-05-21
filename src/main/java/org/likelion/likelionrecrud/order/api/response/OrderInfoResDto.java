package org.likelion.likelionrecrud.order.api.response;

import java.util.List;

public record OrderInfoResDto(
        Long memberId,
        Long orderId,
        List<OrderItemResDto> orderItems
) {
}

