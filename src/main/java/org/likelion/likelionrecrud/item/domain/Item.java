package org.likelion.likelionrecrud.item.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import org.likelion.likelionrecrud.item.api.dto.request.ItemUpdateReqDto;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {
    // 이름, 가격, 재고수량(stockQuantity)을 가지고 있다. 상품을 주문하면 재고수량이 줄어든다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    @Builder
    private Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

//    public void update(ItemUpdateReqDto itemUpdateReqDto) {
//        this.name = itemUpdateReqDto.name();
//        this.price = itemUpdateReqDto.price();
//        this.stockQuantity = itemUpdateReqDto.stockQuantity();
//    }
//    public void addStock(int quantity) {
//        this.stockQuantity += quantity;
//    }
//    public void removeStock(int quantity) {
//        int restStock = this.stockQuantity - quantity;
//        if (restStock < 0) {
//            throw new IllegalArgumentException("재고가 부족합니다.");
//        }
//        this.stockQuantity = restStock;
//    }
}
