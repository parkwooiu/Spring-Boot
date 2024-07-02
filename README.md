<h1 align='center'> <img src='https://cdn-icons-png.flaticon.com/512/5208/5208370.png' style='width: 300px; height: 200px;'>&nbsp;</h1>
<h1  align='center'>👕라이브 커머스 프로젝트</h1>



## 목차
- [개요](https://github.com/parkwooiu/Spring-Boot#-개요)
- [기술 스택](https://github.com/parkwooiu/Spring-Boot#-기술-스택)
- [프로젝트 설계](https://github.com/parkwooiu/Spring-Boot#프로젝트-설계)
- [핵심 기능](https://github.com/parkwooiu/Spring-Boot#-핵심-기능)
- [주요기능 실행화면](https://github.com/parkwooiu/Spring-Boot#-주요기능-실행화면)
- [개선사항](https://github.com/parkwooiu/Spring-Boot#-개선사항)
  


## 🚩 개요
- 프로젝트 목표 : `스프링부트` 라이브 커머스 프로젝트
- 개발기간 : 24/06/17 ~ 24/06/27



## 🔧 기술 스택
- Language : `java(11)` `JavaScript(3.22)`
- Library & Framework : `Spring Boot(2.7.1)` `Thymeleaf(3.0.0)` `jQuery`
- Database : `MySQL(8.0.27)`
- Target : `Web Browser`
- Tool : `IntelliJ IDEA 2024.1.2`
- Etc : `Git`

## 👾 프로젝트 설계, 구현 📂 PPT 📂 (ERD, USECASE)

<details><summary>프로젝트 설계, 구현, PPT 눌러서 확인</summary>   
<div align="center">   

![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/76785628-3126-4603-9450-6b408cde6f0a)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/d7a53e9e-a9b9-4d2d-a33b-9791538ed104)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/ab3cfe48-0852-4bbd-b25b-1d8ffd62e24b)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/cd9e3623-d76b-48b9-9949-631bf31a9f8a)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/44d365fc-9b38-4e66-b857-2e09945f3bb2)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/6525cd4f-da79-4c21-9f40-58a97296f95f)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/b2b0f010-a77a-44e3-b75b-3896e41bd6d6)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/e2e93cb9-dfa9-4b40-8239-49d7ee0e0bca)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/99d7b22b-22b9-46f2-917e-3e3265385638)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/a4d57fa0-83ba-4374-9487-2990cdfd714c)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/19d65652-8c46-4e2e-8e1d-a36df8e59773)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/45f3cab4-69f2-4fb9-b550-2f5755361225)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/909e3106-aade-4aef-ac73-b3a3cd21a09f)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/53178a4b-b233-4ef9-b20f-400bb51d7546)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/e3e8eb5e-c00b-4bde-8a4e-12afe35aa040)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/f975b6fd-9f13-401b-b4aa-3fdc73b0bbb0)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/7bc875fd-3156-4207-949e-0608e81d84cf)
![image](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/52c0bc1e-e464-4699-89d5-b511a110bda3)




</div>            
</details>

## 💻 핵심 기능



#### 상품
- 상품 정보 DB 저장
- 상품 검색
- 상품 대표이미지 설정
- 주문 취소

#### 유저
- 로그인 및 회원가입
- 비밀번호 암호화 처리
- 후기 작성
- 공지사항 댓글

#### 관리자
- 상품 등록
- 상품 품절, 판매중 수정
- 공지사항 등록, 수정, 삭제 
- 문의사항 답변

#### 장바구니
- 상품 장바구니에 담기 및 제거
- 실시간 수량 수정 후 결제
- 같은 상품 장바구니 담을 시 수량 증가

#### 주문
- 장바구니 주문
- 회원만 구매가능

## 🎇 주요기능 실행화면

<details>
<summary> 실행화면 1 눌러서 확인</summary>

![1](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/7f747d12-f272-4d4a-b83a-0b65fa7d97c4)

</details>

<details>
<summary> 실행화면 2 눌러서 확인</summary>

![2](https://github.com/YoungQWER/LiveCommerce_boot/assets/157094828/c5947f15-1816-439e-ba7e-447fc7947d8c)

</details>

## 🚩 문제점
-페이지 로딩 속도 문제
많은 상품 이미지와 데이터를 한 번에 불러오면서 페이지 로딩이 느려졌습니다. 이를 해결하기 위해 이미지 최적화, CDN 도입, 비동기 데이터 로딩 방식을 적용하여 로딩 속도를 개선했습니다.

-사용자 경험
검색 기능 강화가 필요하여 사용자의 편리성을 높이는 작업이 필요합니다.

-보안 문제
초기에는 보안 취약점이 있었습니다. Spring Security 도입, 입력값 검증 강화, SSL/TLS 적용으로 보안을 강화하여 사용자 신뢰를 높였습니다.


## 🌄 개선사항
- 자동화 및 효율화: 재고 관리 자동화, 실시간 재고 업데이트, 고객 서비스 자동화를 통해 운영 효율성을 개선합니다.
- 사용자 경험 향상: 모바일 최적화, 검색 기능 강화, 맞춤형 추천 시스템 도입을 통해 사용자의 편의성과 만족도를 높입니다.
