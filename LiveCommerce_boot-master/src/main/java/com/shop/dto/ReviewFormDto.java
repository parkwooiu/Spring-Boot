package com.shop.dto;

import com.shop.entity.Review;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewFormDto {

    @NotNull
    private Long orderId;

    @NotNull
    private Long itemId; // 상품 아이디 추가

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String comment;

    private String email;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ReviewFormDto of(Review review) {
        return modelMapper.map(review, ReviewFormDto.class);
    }
}
