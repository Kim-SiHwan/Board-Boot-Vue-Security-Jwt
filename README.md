# Board

---
# 소개
기본적인 기능들을 갖춘 게시판입니다

---
# 실행 GIF
![실행 GIF](https://user-images.githubusercontent.com/66605925/110931193-5410e780-836d-11eb-917e-4f9d1c7a3477.gif)

---
# 개발 환경
######Back
- SpringBoot 2.4.2
- SpringSecurity 2.4.2
- SpringWeb 2.4.2
- SpringDataJpa 2.4.2
- QueryDsl 4.4.0

######Front
- Vue 2.6.11
- Vuex 3.6.0
- Vuetify 2.2.11
- Router 1.3.5
- Axios 0.21.1

---
# 프로젝트 설계 
###### 도메인 객체 설계
![도메인 객체](https://user-images.githubusercontent.com/66605925/110930771-dd73ea00-836c-11eb-812c-30a5af895a8a.PNG)

###### 테이블 설계
![설계 테이블](https://user-images.githubusercontent.com/66605925/110930792-e369cb00-836c-11eb-9cea-1733dcc1e4f0.PNG)

###### 도메인 분석
- 회원 ( Member ) 
⇒ 중복을 허용하지 않는 username, 비밀번호, 별명과 권한 정보를 가지고 
    게시글 , 댓글의 LinkedHashSet을 가지고 있음.

- 게시글 ( Board ) 
⇒ 제목, 내용, 생성 일자 , 조회 수를 가지고 있으며 회원 정보와 댓글 리스트와 
    게시글 추천 리스트를 가지고 있음

- 댓글 ( Reply )
⇒ 댓글 내용과 생성일자, 작성한 회원 정보와 댓글이 작성될 게시글 정보를 가지고 있음

- 추천 ( BoardLike )
⇒ 추천한 회원 정보와 추천된 게시글 정보를 가지고 있음

###### 구현 기능
Member → 회원 등록, 로그인, 권한이 없는 사용자의 접근 제한

Board → 조회, 등록, 수정, 삭제, 추천 수에 따른 인기 게시판으로 이동,검색 

Reply → 조회, 등록, 수정, 삭제

Like → 중복되지않는 게시글 추천

Validate → Spring Validation 사용으로 유효성 검증을 통해, 1차로 Front에서 처리한 뒤 
마지막으로 다시 요청의 유효성을 검증함.

Authentication, Authorization → 로그인 시 Jwt 토큰 발급, 로컬 스토리지에 저장 후 js 인터셉터를 활용해 요청할 때마다 헤더에 담아 전송해 사용자의 인증과 권한 부여

관리자 - ADMIN권한으로 모든 게시글과 댓글에 관여할 수 있음.

---
# API
###### Member
- POST   /api/member/save : 회원 가입
- POST   /api/member/login : 로그인

###### Board
- GET   /api/board : 검색어를 통한 전체 게시글 조회
- GET   /api/board/best : 추천수가 3이 넘는 게시글들의 조회
- GET   /api/board/{board_id} : 게시글 단건 조회
- POST   /api/board : 게시글작성
- DELETE   /api/board/{board_id} : 게시글 삭제
- PATCH   /api/board : 게시글 수정

###### Reply
- GET   /api/replies/{board_id} : 해당 게시글에 달린 전체 댓글 조회
- POST   /api/replies : 댓글 생성
- DELETE   /api/replies : 댓글 삭제
- PUT   /api/replies : 댓글 수정

###### Like
- POST   /api/like : 게시글 추천
