# 완료 리스트
- [x] 프레임워크 구현 (db연결, Service, dao 등 실행구조 구현)
- [x] 로그인/로그아웃
- [x] 회원가입
- [x] 게시글 CRUD
- [x] 글 CRUD
- [x] 인터셉터
- [x] 로그인/로그아웃 상태 관련 권한체크
- [x] 게시물 수정/삭제 관련 권한체크
- [x] 비밀번호 암호화
- [x] 아이디 찾기
- [x] 비밀번호 찾기(임시패스워드 발송)
- [x] 게시물 검색
- [x] 게시물 페이징
- [x] 토스트 에디터 붙이기
- [x] header/footer 꾸미기
- [x] 로그인 폼 꾸미기
- [x] 회원가입 폼 꾸미기
- [x] 좋아요, 싫어요
- [x] tomcat 한방배포

# 당장 할일 리스트
- [ ] 게시물 블라인드


# 추후 할일 리스트
- [ ] 내 글에 새 댓글 알림
- [ ] 내 댓글에 추가 댓글
- [ ] 댓글
- [ ] 댓글멘션
- [ ] 대댓글
- [ ] 태그
- [ ] 댓글 , ajax화
- [ ] 파일 업로드
- [ ] 회원인증
- [ ] 관리자 페이지
- [ ] 1:1 쪽지
- [ ] 신고



# 메이븐 settings.xml 템플릿
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <servers>
        <server>
            <id>law__manager_text</id>
            <username>톰캣 웹 관리자의 계정명</username>
            <password>톰캣 웹 관리자의 계정의 비밀번호</password>
        </server>
    </servers>
</settings>
```
