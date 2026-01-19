# CookLog

Spring MVC 기반 레시피 관리 웹 서비스입니다.  
사용자가 레시피를 등록하고, 요리 과정을 단계별로 관리할 수 있도록 구현했습니다.

---

## 기술 스택
- Java 17
- Spring MVC
- JDBC Template
- MariaDB
- Apache Tomcat

---

## 주요 기능
- 회원가입 / 로그인
- 레시피 CRUD
- 요리 단계 동적 추가 / 삭제
- 이미지 업로드
- 관리자 권한 분리
- 공지사항 관리

---

## DB 설계
- Member / Recipe / CookingStep / Notice 테이블
- 역할(Role), 상태(Status) 기반 설계

---

## 배포
- Cafe24 서버 + Apache Tomcat
- 배포 주소: http://cd01349.cafe24.com/CookLog

---

## 환경 설정
- DB 접속 정보는 설정 파일로 관리
- 민감한 정보는 GitHub에 업로드하지 않음
  
---

## 프로젝트 설명
CookLog는 기존 레시피 사이트의 복잡한 입력 구조를 개선하고,  
사용자가 **요리 과정에 집중할 수 있도록 UI/UX를 단순화**한 프로젝트입니다.
