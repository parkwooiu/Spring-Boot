package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "response")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private Inquiry inquiry;  // 응답이 달린 문의

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Member admin;  // 응답을 작성한 관리자

    private String content;  // 응답 내용


}
