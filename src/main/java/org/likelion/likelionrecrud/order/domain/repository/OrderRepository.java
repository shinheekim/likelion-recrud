package org.likelion.likelionrecrud.order.domain.repository;

import org.likelion.likelionrecrud.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
        List<Order> findByMemberId(Long memberId); // memberId로 검색하기 위해 메서드 추가
}