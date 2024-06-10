package com.shop.dto;

import com.shop.constant.ItemSellStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {


    private Long id;    //상품코드

    private String itemNm;   //상품명

    private int price;  //가격

    private String itemDetail; //상품 상세설명

    private String sellStatCd;

    private LocalDateTime regTime;   //등록 시간

    private LocalDateTime updateTime; //수정 시간


}