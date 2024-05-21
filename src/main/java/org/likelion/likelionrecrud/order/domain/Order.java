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
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 파라미터 x 디폴트 생성자
@Table(name = "orders") // order가 예약어(키워드)라 오류가 발생하기 때문에 테이블 이름을 바꿔둠.
public class Order {
    // 주문과 주문상품은 일대다 관계,
    // 주문은 상품을 주문한 회원과 주문리스트, 주문 날짜, 주문 상태(status)를 가지고 있다.
    // 주문 상태는 열거형을 사용해서 주문(ORDER), 취소(CANCEL)을 표현할 수 있다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키
    @Column(name = "order_id")  //속성명
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore // response에 안담김. 노션참고
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) // 연관관계 주인
    private List<OrderItem> orderItems = new ArrayList<>(); // 이떄 OrderItem 생성!

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Builder
    private Order(Member member, OrderStatus status) {
        this.member = member;
        this.status = status;
        this.orderDate = LocalDateTime.now();
    }

    //    public void cancel() {
//        this.status = OrderStatus.CANCEL;
//        orderItems.forEach(OrderItem::cancel); // 주문 취소
//    }
}
