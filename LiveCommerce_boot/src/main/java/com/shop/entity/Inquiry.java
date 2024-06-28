package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inquiry")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 문의를 작성한 회원

    private String type;  // 문의 유형 (예: 주문 관련, 반품/교환, 제품 정보 등)

    private String content;  // 문의 내용

    private LocalDateTime inquiryDate;  // 문의 날짜

    private String status;  // 문의 상태 (예: 접수, 처리 중, 완료)
}
