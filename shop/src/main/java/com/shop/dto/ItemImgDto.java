package com.shop.dto;

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class ItemImgDto { //상품 저장 후 상품 이미지에 대한 데이터 전달할 클래스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName;   //이미지 파일명

    private String oriImgName;    // 원본 이미지 파일명

    private String imgUrl;      //이미지 조회 경로

    private String repimgYn;   //대표 이미지 여부



    private static ModelMapper modelMapper = new ModelMapper();

    //ItemImg 엔티티를  받아서 ItemImgDto로 변환
    public static ItemImgDto ItemImgofItemImgDto(ItemImg itemImg) {
        return modelMapper.map(itemImg, ItemImgDto.class);
    }
}
