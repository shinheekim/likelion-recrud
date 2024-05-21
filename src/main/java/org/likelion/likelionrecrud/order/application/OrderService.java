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
        // 회원 조회
        Member member = memberRepository.findById(orderSaveReqDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + orderSaveReqDto.memberId()));

        // 주문 상품 목록 준비
        for (int i = 0; i < orderSaveReqDto.itemIds().size(); i++) {
            Long itemId = orderSaveReqDto.itemIds().get(i);
            int count = orderSaveReqDto.counts().get(i);

            // 상품 조회
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));

            Order order = Order.builder()
                    .member(member)
                    .status(OrderStatus.ORDER)
                    .build();

            orderRepository.save(order);

            orderItemRepository.save(OrderItem.builder()
                    .item(item)
                    .order(order)
                    .count(count)
                    .orderPrice(item.getPrice())
                    .build());
        }
    }

    //특정 회원이 가진 모든 주문과 각 주문에 포함된 주문 항목들의 정보를 OrderInfoResDto 객체의 리스트 형태로 제공
    public List<OrderInfoResDto> findOrderInfoByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + memberId));

        List<OrderItem> allByOrderMember = orderItemRepository.findAllByOrderMember(member);

        List<OrderInfoResDto> orderInfoResDtoList = new ArrayList<>();
        for (OrderItem orderItem : allByOrderMember) {
            OrderInfoResDto o = new OrderInfoResDto(member.getId(), orderItem.getOrder().getId(), orderItem.getOrder().getOrderItems()
                    .stream().map(orderItems -> new OrderItemResDto(
                            orderItems.getItem().getId(),
                            orderItems.getOrderPrice(),
                            orderItems.getCount()))
                    .toList());

            orderInfoResDtoList.add(o);
        }

        return orderInfoResDtoList;
    }
}