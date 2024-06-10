package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)      //중복 불가
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;      //이대로 생성하면 int로 생성

//    @Enumerated(EnumType.STRING)
//    private Role role;

    // 이거 대신 Mapper 란 것 이용할 수 있음.
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .address(memberFormDto.getAddress())
                .password(passwordEncoder.encode(memberFormDto.getPassword()))
                .role(Role.USER)
                .build();


    }


}
