package org.likelion.likelionrecrud.order.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OrderListResDto (
        List<OrderInfoResDto> orderList
) {
    public static OrderListResDto from(List<OrderInfoResDto> orderList){
        return OrderListResDto.builder()
                .orderList(orderList)
                .build();
    }
}
