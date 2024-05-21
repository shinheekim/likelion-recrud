package org.likelion.likelionrecrud.order.api.response;

public record OrderItemResDto(
        Long itemId,
        int orderPrice,
        int count
) {
}
