package org.likelion.likelionrecrud.order.api.response;

import lombok.Builder;

import java.util.List;

// 여러 주문의 정보를 리스트 형태로 응답하기 위한 데이터 구조를 정의
@Builder
public record OrderListResDto (
        List<OrderInfoResDto> orderList
) {
    // 주어진 orderList를 사용하여 OrderListResDto 객체를 빌드하는 정적 메서드
    public static OrderListResDto from(List<OrderInfoResDto> orderList){    // 이 클래스들은 함께 사용되어 주문 정보와 주문 항목 정보를 구조화하여 응답하는 역할
        return OrderListResDto.builder()    // 개별 주문의 정보
                .orderList(orderList)   // 개별 주문 항목의 정보
                .build();
    } // OrderListResDto는 여러 주문의 정보를 리스트 형태로 관리
}
