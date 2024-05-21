package org.likelion.likelionrecrud.item.domain.repository;

import org.likelion.likelionrecrud.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
