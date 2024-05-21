package org.likelion.likelionrecrud.item.api.dto.request;

public record ItemSaveReqDto (
        String name,
        int price,
        int stockQuantity
){
}
