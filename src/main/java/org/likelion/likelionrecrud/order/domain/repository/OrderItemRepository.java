package org.likelion.likelionrecrud.order.domain.repository;

import org.likelion.likelionrecrud.member.domain.Member;
import org.likelion.likelionrecrud.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// OrderItemRepository는 특정 회원의 주문 항목을 효율적으로 조회
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 사용자 정의 JPQL 쿼리 정의
    @Query(value = "select oi " +   // 모든 OrderItem 조회하는 쿼리
            "from OrderItem oi join fetch oi.order o " +    //OrderItem엔티티와 이를 포함하는 Order 엔티티를 패치 조인해서 특정 회원(Member)이 주문한~~
            "where oi.order.member = :member ") // member는 메서드 파라미터로 전달된 Member 객체를 바인딩하는 위치 파라미터
    List<OrderItem> findAllByOrderMember(Member member);
}
