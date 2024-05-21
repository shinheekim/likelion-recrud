package org.likelion.likelionrecrud.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.likelion.likelionrecrud.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"orders\"")
public class Order {
    // 주문과 주문상품은 일대다 관계,
    // 주문은 상품을 주문한 회원과 주문리스트, 주문 날짜, 주문 상태(status)를 가지고 있다.
    // 주문 상태는 열거형을 사용해서 주문(ORDER), 취소(CANCEL)을 표현할 수 있다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore // 연관관계 주인
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>(); // 이떄 OrderItem 생성!

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    public Order(Long id, Member member, List<OrderItem> orderItems, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.member = member;
        this.orderItems = new ArrayList<>();
        if (orderItems != null) {
            orderItems.forEach(this::addOrderItem); // orderItems 목록의 각 요소에 대해 addOrderItem 메소드 호출(메소드 참조)
        }// orderItems 목록의 각 OrderItem 객체에 대해 현재 주문 객체(this)의 addOrderItem 메소드를 실행하라는 의미
        this.orderDate = orderDate;
        this.status = status;
    }

    // 주문 아이템 목록(orderItems)에 주문 아이템을 추가하고, 주문 아이템 측에도 현재 주문 객체를 설정하여 양방향 관계를 완성
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.addOrder(this);
    }

    // 주문 객체를 생성하는 팩토리 메서드. 주문자, 주문 아이템 목록, 주문 날짜(현재 시각), 주문 상태(ORDER)를 설정
    // 주문 내역 하나 생성
    // 주문 항목(OrderItem)들을 주문 객체(Order)에 추가하는 역할
    public static Order createOrder(Member member, List<OrderItem> orderItems) {
        Order order = Order.builder()
                .member(member)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.ORDER)
                .build();

        for (OrderItem orderItem : orderItems) {    //입력으로 받은 orderItems 리스트에 포함된 모든 주문 항목을 순회
            order.addOrderItem(orderItem);  //현재 주문 항목을 주문 객체에 추가(주문 객체와 주문 항목 사이에 연관 관계)
        }
        return order;
    }

//    public void cancel() {
//        this.status = OrderStatus.CANCEL;
//        orderItems.forEach(OrderItem::cancel); // 주문 취소
//    }
}
