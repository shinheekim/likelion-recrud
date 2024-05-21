package org.likelion.likelionrecrud.order.api.request;

import org.likelion.likelionrecrud.order.domain.OrderStatus;

import java.util.List;

public record OrderSaveReqDto(
        Long memberId,
        List<Long> itemIds,
        List<Integer> counts
) {
}

