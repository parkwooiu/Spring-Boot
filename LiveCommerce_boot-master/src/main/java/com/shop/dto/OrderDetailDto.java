package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailDto {

    private Long orderId; // 주문 번호
    private Long itemId; // 상품 번호
    private String itemName; // 상품명
    private int orderPrice; // 주문 가격
    private int count; // 주문 수량
    private String memberName; // 주문자 이름
    private String memberEmail; // 주문자 이메일
    private String itemDetail; // 상품 상세 설명
    private int itemStockNumber; // 상품 재고 수량
    // 기타 주문 상세 정보 필드들 추가 가능

    // 생성자, Getter, Setter 등은 생략하였습니다.
}
