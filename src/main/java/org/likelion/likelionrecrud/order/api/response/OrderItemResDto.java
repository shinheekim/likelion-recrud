package org.likelion.likelionrecrud.order.api.response;

// 개별 주문 항목의 상세 정보(상품id, 가격, 수량)
public record OrderItemResDto(
        Long itemId,
        int orderPrice,
        int count
) {
}
