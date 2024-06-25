package com.shop.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Announcement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime regTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_member_id")
    private Member createdByMember;

    // 생성자, 빌더 메서드, 기타 필요한 코드 추가
}
