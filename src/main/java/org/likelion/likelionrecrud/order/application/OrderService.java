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

    @Transactional
    public Long orderSave(OrderSaveReqDto orderSaveReqDto) {
        // 회원 조회
        Member member = memberRepository.findById(orderSaveReqDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다. id=" + orderSaveReqDto.memberId()));

        // 주문 상품 목록 준비
        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < orderSaveReqDto.itemIds().size(); i++) {
            Long itemId = orderSaveReqDto.itemIds().get(i);
            int count = orderSaveReqDto.counts().get(i);

            // 상품 조회
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + itemId));

            // 주문 상품 생성(상품이 정상적으로 조회되면, 해당 상품과 구매 수량을 이용하여 주문 상품(OrderItem)을 생성하고, 이를 주문 상품 목록에 추가)
            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
            orderItems.add(orderItem);

        }
        // 주문 생성(준비된 회원 정보와 주문 상품 목록을 이용하여 주문(Order) 객체를 생성)
        Order order = Order.createOrder(member, orderItems);

        // 주문 저장(생성된 주문 객체를 orderRepository를 통해 데이터베이스에 저장)
        orderRepository.save(order);

        //저장된 주문의 ID를 반환
        return order.getId();
    }

    //특정 회원이 가진 모든 주문과 각 주문에 포함된 주문 항목들의 정보를 OrderInfoResDto 객체의 리스트 형태로 제공
    public List<OrderInfoResDto> findOrderInfoByMemberId(Long memberId) {
        // member id에 해당하는 모든 주문 목록 조회
        List<Order> orders = orderRepository.findByMemberId(memberId);
        return orders.stream()  // 주문 목록을 스트림으로 반환
                .map(order -> new OrderInfoResDto(  // Order 객체를 OrderInfoResDto 객체로 변환
                        order.getMember().getId(),
                        order.getId(),
                        order.getOrderItems().stream()
                                .map(orderItem -> new OrderItemResDto( // 각 OrderItem 객체는 OrderItemResDto 객체로 변환
                                        orderItem.getItem().getId(),
                                        orderItem.getOrderPrice(),
                                        orderItem.getCount()
                                ))
                                .toList()   // 이 친구를 통해 OrderItemResDto 객체의 리스트로 수집
                ))
                .toList();  //최종 리스트로 수집, 이 리스트는 메서드의 반환값으로 사용
    }

//    @Transactional
//    public void cancelOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다. id=" + orderId));
//        order.cancel();
//    }
//
//    @Transactional
//    public void deleteOrder(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 주문이 없습니다. id=" + orderId));
//        orderRepository.delete(order);
//    }

}
