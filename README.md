# Board

---
# 소개
기본적인 기능들을 갖춘 게시판입니다

---
# 실행 GIF
![실행 GIF](https://user-images.githubusercontent.com/66605925/110931193-5410e780-836d-11eb-917e-4f9d1c7a3477.gif)

---
# 개발 환경
**Back :** `Spring Boot` `Spring Security` `Spring JPA`

**Front :** `Vue` `Vuetify` `Axios`

---
# 프로젝트 설계 
###### 도메인 객체 설계
![도메인 객체](https://user-images.githubusercontent.com/66605925/110930771-dd73ea00-836c-11eb-812c-30a5af895a8a.PNG)

###### 테이블 설계
![설계 테이블](https://user-images.githubusercontent.com/66605925/110930792-e369cb00-836c-11eb-9cea-1733dcc1e4f0.PNG)

## 엔티티 분석

`회원 ( Member )`
⇒ 중복을 허용하지 않는 username, 비밀번호, 별명과 권한 정보를 가지고 
    게시글 , 댓글의 LinkedHashSet을 가지고 있음.

`게시글 ( Board )`
⇒ 제목, 내용, 생성 일자 , 조회 수를 가지고 있으며 회원 정보와 댓글 리스트와 
    게시글 추천 리스트를 가지고 있음

`댓글 ( Reply )`
⇒ 댓글 내용과 생성일자, 작성한 회원 정보와 댓글이 작성될 게시글 정보를 가지고 있음

`추천 ( BoardLike )`
⇒ 추천한 회원 정보와 추천된 게시글 정보를 가지고 있음

---

## 구현 기능

`회원` 

`회원 등록` `로그인 & 로그아웃` 

`게시물` 

`생성` `조회` `수정` `삭제` `검색` `추천에 따른 인기 게시판으로 이동`

`댓글` 

`생성` `조회` `수정` `삭제` 

`추천` 

`중복되지 않는 게시글 추천`

`유효성 검증` 

`Spring Validate을 통한 유효성 검증`

`인증` 

`JWT를 통해 토큰 발급과 토큰 검증`

`관리자`

`ADMIN 권한으로 모든 게시글과 댓글에 관여할 수 있음`

---

## API

`회원` `POST /api/member/save` ⇒ 회원 가입

`회원` `POST /api.member/login` ⇒ 로그인

`게시물` `GET /api/board` ⇒ 검색 키워드를 통한 전체 게시글 조회 [ 기본 값 Default ]

`게시물` `GET /api/board/best` ⇒ 3개 이상의 추천을 받은 게시글 조회

`게시물` `GET /api/board/{board_id}` ⇒ 게시글 단건 조회

`게시물` `POST /api/board` ⇒ 게시글 작성

`게시물` `DELETE /api/board/{board_id}` ⇒ 게시글 삭제

`게시물` `PATCH /api/board` ⇒ 게시글 수정

`댓글` `GET /api/replies/{board_id}` ⇒ 해당 게시글에 달린 전체 댓글 조회

`댓글` `POST /api/replies` ⇒ 댓글 생성

`댓글` `DELETE /api/replies` ⇒ 댓글 삭제

`댓글` `PUT /api/replies` ⇒ 댓글 수정

`추천` `POST /api/like` ⇒ 게시글 추천

---
### 문제점과 해결방법

`N+1 문제`

⇒ LAZY 로딩으로 설정한 후, Entity Graph를 사용함으로 필요한 시점에  그래프 탐색을 마쳐 참조 데이터를 가져오게 변경함으로 N+1 → 1개의 쿼리로 변경

`Cross-Origin-Resource-Sharing`

⇒ 프론트와 서버가 다른 도메인으로 설정되어 있었고, 프론트에서 호출할 때 CORS가 발생해 데이터를 불러올 수 없었던 문제가 발생해 CORS필터를 생성하고 적용해 해결

`JWT의 적용`

⇒ 처음 접해보는 Token의 개념과 직접 사용하려니 어려움을 겪어 강의와 검색 등을 통해 사용법을 익혀 적용해 해결

---

### 아쉬운 부분

`문서화`

⇒ 개인적으로 개발자로서 문서화는 굉장히 중요하다고 생각하는데, 이 프로젝트에서는 제대로 적용하지 않아 아쉽고, 다음 프로젝트부터는 Swagger 등을 통한 문서화를 해보고 싶음

`모던 자바`

⇒ Stream API, Lambda, Optional 등 Java8부터 제공하는 기능들을 사용해 프로젝트를 구성하는 모던 자바를 더욱 활용하지 못했기에 다시 공부하고 다음 프로젝트부터는 더 모던 자바스럽게 작성하고 싶음


