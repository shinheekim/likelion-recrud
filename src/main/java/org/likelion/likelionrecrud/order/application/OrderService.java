package org.likelion.likelionrecrud.order.application;

import lombok.RequiredArgsConstructor;
import org.likelion.likelionrecrud.item.domain.Item;
import org.likelion.likelionrecrud.item.domain.repository.ItemRepository;
import org.likelion.likelionrecrud.member.domain.Member;
import org.likelion.likelionrecrud.member.domain.repository.MemberRepository;
import org.likelion.likelionrecrud.order.api.request.OrderSaveReqDto;
import org.likelion.likelionrecrud.order.api.response.OrderInfoResDto;
import org.likelion.likelionrecrud.order.api.response.OrderItemResDto;
import org.likelion.likelionrecrud.order.domain.Order;
import org.likelion.likelionrecrud.order.domain.OrderItem;
import org.likelion.likelionrecrud.order.domain.OrderStatus;
import org.likelion.likelionrecrud.order.domain.repository.OrderItemRepository;
import org.likelion.likelionrecrud.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void orderSave(OrderSaveReqDto orderSaveReqDto) {
        // 회원 id로 조회
        Member member = memberRepository.findById(orderSaveReqDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + orderSaveReqDto.memberId()));

        // 주문 생성: 조회한 회원과 주문상태를 사용해서 Order 엔티티 생성하고 orderRepository를 통해 저장
        Order order = Order.builder()
                .member(member)
                .status(OrderStatus.ORDER)
                .build();

        orderRepository.save(order);

        // 주문 상품 목록 준비: DTO에서 상품 ID 목록과 각 상품의 주문 수량을 이용
        for (int i = 0; i < orderSaveReqDto.itemIds().size(); i++) {
            Long itemId = orderSaveReqDto.itemIds().get(i);
            int count = orderSaveReqDto.counts().get(i);

            // 상품 조회
            Item item = itemRepository.findById(itemId) // itemRepository를 통해 상품을 조회
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));

            // 주문 상품 생성 및 저장
            orderItemRepository.save(OrderItem.builder()
                    .item(item)
                    .order(order)
                    .count(count)
                    .orderPrice(item.getPrice())
                    .build());
        }
        // 이 과정에서 모든 주문 상품에 대해 별도의 주문(Order) 엔티티가 생성
    }

    // OrderInfoResDto와 OrderItemResDto 클래스를 활용하여 주문 정보 표현
    public List<OrderInfoResDto> findOrderInfoByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));

        // findAllByOrderMember를 통해 주어진 emeber와 관련된 모든 OrderItem 객체들을 리스트로 가져옴.
        List<OrderItem> allByOrderMember = orderItemRepository.findAllByOrderMember(member);

        // 최종적으로 반환할 OrderInfoResDto 객체들을 담을 리스트를 초기화
        List<OrderInfoResDto> orderInfoResDtoList = new ArrayList<>();
        // allByOrderMember 리스트에 담긴 모든 OrderItem 객체 순회
        for (OrderItem orderItem : allByOrderMember) {
            OrderInfoResDto o = new OrderInfoResDto(    //순회중인 OrderItem 객체로부터 OrderInfoResDto 객체 생성
                    member.getId(),
                    orderItem.getOrder().getId(),
                    new OrderItemResDto(    // 회원 ID, 주문 ID, 그리고 주문 항목(OrderItemResDto)을 생성자에 전달
                            orderItem.getItem().getId(),
                            orderItem.getItem().getPrice(),
                            orderItem.getCount())   // OrderItemResDto는 주문된 상품의 ID, 가격, 수량을 포함
            );

            //생성된 OrderInfoResDto 객체를 orderInfoResDtoList에 추가
            orderInfoResDtoList.add(o);
        }

        return orderInfoResDtoList;
    }
}