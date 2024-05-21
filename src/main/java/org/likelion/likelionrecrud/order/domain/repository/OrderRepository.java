package org.likelion.likelionrecrud.order.domain.repository;

import org.likelion.likelionrecrud.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findByMemberId(Long memberId);
}
