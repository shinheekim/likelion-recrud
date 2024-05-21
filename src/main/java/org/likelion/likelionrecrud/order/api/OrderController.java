package org.likelion.likelionrecrud.order.api;

import lombok.RequiredArgsConstructor;
import org.likelion.likelionrecrud.order.api.request.OrderSaveReqDto;
import org.likelion.likelionrecrud.order.api.response.OrderInfoResDto;
import org.likelion.likelionrecrud.order.api.response.OrderListResDto;
import org.likelion.likelionrecrud.order.application.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<String> orderSave(@RequestBody OrderSaveReqDto orderSaveReqDto) {
        orderService.orderSave(orderSaveReqDto);
        return new ResponseEntity<>("주문 저장!", HttpStatus.CREATED);
        // Long orderId = orderService.orderSave(orderSaveReqDto);
        // return ResponseEntity.ok("주문이 접수되었습니다. 주문번호: " + orderId);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<OrderListResDto> getOrdersByMember(@PathVariable("memberId") Long memberId) {
        List<OrderInfoResDto> orderInfoResDto = orderService.findOrderInfoByMemberId(memberId);
        return ResponseEntity.ok(OrderListResDto.from(orderInfoResDto));
    }

//    @PostMapping("/{orderId}")
//    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {
//        orderService.cancelOrder(orderId);
//        return ResponseEntity.ok("주문이 취소되었습니다.");
//    }
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<String> deleteOrder(@PathVariable("orderId") Long orderId) {
//        orderService.deleteOrder(orderId);
//        return ResponseEntity.ok("주문이 삭제되었습니다.");
//    }
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }

}
