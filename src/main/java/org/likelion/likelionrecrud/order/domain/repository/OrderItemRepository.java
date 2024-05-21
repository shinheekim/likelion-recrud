package org.likelion.likelionrecrud.order.domain.repository;

import org.likelion.likelionrecrud.member.domain.Member;
import org.likelion.likelionrecrud.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(value = "select oi " +
            "from OrderItem oi join fetch oi.order o " +
            "where oi.order.member = :member ")
    List<OrderItem> findAllByOrderMember(Member member);
}
