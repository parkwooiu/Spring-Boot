package com.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")  // 데이터베이스 테이블 이름을 지정합니다.
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 생성을 데이터베이스에 위임합니다.
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계를 설정합니다.
    @JoinColumn(name = "member_id", nullable = false)  // 외래 키 컬럼을 설정합니다.
    private Member sender;  // 메시지를 보낸 회원 정보

    @ManyToOne(fetch = FetchType.LAZY)  // 다대일 관계를 설정합니다.
    @JoinColumn(name = "item_id", nullable = false)  // 외래 키 컬럼을 설정합니다.
    private Item item;  // 채팅이 속한 상품 정보

    @Column(length = 1000)  // 메시지 내용의 최대 길이를 1000자로 제한합니다.
    private String content;

    private LocalDateTime timestamp;  // 메시지가 전송된 시간
}
