package org.likelion.likelionrecrud.item.api.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ItemListResDto(
        List<ItemInfoResDto> items
) {
    public static ItemListResDto from(List<ItemInfoResDto> items) {
        return ItemListResDto.builder()
                .items(items)
                .build();
    }
}
