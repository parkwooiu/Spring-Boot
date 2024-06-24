package com.shop.service;

import com.shop.dto.ReviewFormDto;
import com.shop.entity.Item;
import com.shop.entity.Order;
import com.shop.entity.Review;
import com.shop.repository.ItemRepository;
import com.shop.repository.OrderRepository;
import com.shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public void saveReview(ReviewFormDto reviewFormDto, String email) {
        Order order = orderRepository.findById(reviewFormDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid order ID"));

        Item item = itemRepository.findById(reviewFormDto.getItemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));

        Review review = new Review();
        review.setOrder(order);
        review.setItem(item);
        review.setRating(reviewFormDto.getRating());
        review.setComment(reviewFormDto.getComment());
        review.setEmail(email);

        reviewRepository.save(review);
    }

    // 아이템 ID로 리뷰를 조회하는 메서드 추가
    @Transactional(readOnly = true)
    public List<ReviewFormDto> getReviewsByItemId(Long itemId) {
        return reviewRepository.findAllByItemId(itemId);
    }
}
