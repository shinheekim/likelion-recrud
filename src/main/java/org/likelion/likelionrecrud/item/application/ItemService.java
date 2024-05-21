package org.likelion.likelionrecrud.item.application;

import lombok.RequiredArgsConstructor;
import org.likelion.likelionrecrud.item.api.dto.request.ItemSaveReqDto;
//import org.likelion.likelionrecrud.item.api.dto.request.ItemUpdateReqDto;
import org.likelion.likelionrecrud.item.api.dto.response.ItemInfoResDto;
import org.likelion.likelionrecrud.item.api.dto.response.ItemListResDto;
import org.likelion.likelionrecrud.item.domain.Item;
import org.likelion.likelionrecrud.item.domain.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

//    public ItemService(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @Transactional
    public void itemSave(ItemSaveReqDto itemsaveReqDto) {
        Item item = Item.builder()
                .name(itemsaveReqDto.name())
                .price(itemsaveReqDto.price())
                .stockQuantity(itemsaveReqDto.stockQuantity())
                .build();
        itemRepository.save(item);
    }

    public ItemListResDto itemFindAll() {
        List<Item> items = itemRepository.findAll();    // item 엔티티 목록을 itemRepository.findAll()을 통해 조회
        List<ItemInfoResDto> itemInfoResDtoList = items.stream()    //각 Item 객체를 ItemInfoResDto 객체로 변환
                .map(ItemInfoResDto::from)  // from 정적 메소드 사용
                .toList();  // MemberInfoResDto 객체들의 스트림을 리스트로 수집함
//                .collect(Collectors.toList());
        return ItemListResDto.from(itemInfoResDtoList);
    }

    public ItemInfoResDto itemFindOne(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow
                (() -> new IllegalArgumentException("해당 상품이 없습니다."));
        return ItemInfoResDto.from(item);
    }

//    @Transactional
//    public void updateItem(Long itemId, ItemUpdateReqDto itemUpdateReqDto) {
//        Item item = itemRepository.findById(itemId).orElseThrow
//                (() -> new IllegalArgumentException("해당 상품이 없습니다."));
//        item.update(itemUpdateReqDto);
//    }
//
//    @Transactional
//    public void itemDelete(Long itemId) {
//        Item item = findOne(itemId);
//        itemRepository.delete(item);
//    }

}