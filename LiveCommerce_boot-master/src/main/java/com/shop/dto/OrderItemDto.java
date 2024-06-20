package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {

    private Long itemId; // 상품 ID
    private String itemNm; // 상품 이름
    private int count; // 주문 수량
    private int orderPrice; // 주문 가격
    private String imgUrl; // 상품 이미지 경로

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemId = orderItem.getItem().getId(); // 상품 ID 설정
        this.itemNm = orderItem.getItem().getItemNm(); // 상품 이름 설정
        this.count = orderItem.getCount(); // 주문 수량 설정
        this.orderPrice = orderItem.getOrderPrice(); // 주문 가격 설정
        this.imgUrl = imgUrl; // 상품 이미지 경로 설정
    }
}
