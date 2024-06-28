package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;  // 회원 이름

    @Column(unique = true)
    private String email;  // 회원 이메일 (유니크)

    private String password;  // 회원 비밀번호

    private String address;  // 회원 주소

    @Enumerated(EnumType.STRING)
    private Role role;  // 회원 역할 (USER 또는 ADMIN)

    @OneToMany(mappedBy = "member")
    private List<Inquiry> inquiries;  // 회원이 작성한 문의 리스트

    @OneToMany(mappedBy = "admin")
    private List<Response> responses;  // 관리자가 작성한 응답 리스트

    // 회원 생성 메서드
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .address(memberFormDto.getAddress())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .role(Role.USER) // 기본 역할을 USER로 설정
                .build();
    }

    // 공지사항 등록 시 현재 사용자 이름 자동 설정
    public static Member createMemberWithCurrentUser(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return Member.builder()
                .name(currentUserName)
                .email(memberFormDto.getEmail())
                .address(memberFormDto.getAddress())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .role(Role.ADMIN)
                .build();
    }
}
