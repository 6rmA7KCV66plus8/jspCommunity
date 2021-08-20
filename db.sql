DROP DATABASE IF EXISTS jspCommunity;
CREATE DATABASE jspCommunity;
USE jspCommunity;

#게시판 테이블 생성
CREATE TABLE board(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(10) NOT NULL UNIQUE,
    `name` CHAR(10) NOT NULL UNIQUE
);

#회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(50) NOT NULL,
    `nickname` CHAR(50) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    loginId CHAR(50) NOT NULL UNIQUE,
    loginPw VARCHAR(200) NOT NULL,
    authLevel TINYINT(1) UNSIGNED NOT NULL DEFAULT 2 COMMENT '0=탈퇴/1=정지/2=일반/3=인증/4=관리자'
);

# 회원1 생성
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    `name` = "김정민",
    `nickname` = "therealmin",
    `email` = "rlaxodus0215@gmail.com",
    loginId = "rlaxodus0215",
    loginPw = "1234";
    
#회원2 생성
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    `name` = "김미소",
    `nickname` = "therealso",
    `email` = "rlaxodus0001@gmail.com",
    loginId = "rlaxodus0001",
    loginPw = "1234";

#공지사항 게시판 생성(board)
INSERT INTO board
SET regDate = NOW(),
    updateDate = NOW(),
    `code` =  'notice',
    `name` =  '공지사항';
    
#방명록 생성(board)
INSERT INTO board
SET regDate = NOW(),
    updateDate = NOW(),
    `code` =  'questBook',
    `name` =  '방명록';    


#자유 게시판 생성(board)
INSERT INTO board
SET regDate = NOW(),
    updateDate = NOW(),
    `code` =  'free',
    `name` =  '자유게시판';

#게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL,
    title CHAR(100) NOT NULL,
    `body` LONGTEXT NOT NULL,
    hitsCount INT(10) UNSIGNED NOT NULL DEFAULT 0
);

# 테스트 게시물1 생성
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 1,
    boardId = 1,
    title = '제목1',
    `body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 1,
    boardId = 1,
    title = '제목2',
    `body` = '내용2';
    
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 1,
    boardId = 1,
    title = '제목3',
    `body` = '내용3';  

INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 2,
    boardId = 1,
    title = '제목4',
    `body` = '내용4';  
   
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    memberId = 2,
    boardId = 1,
    title = '제목5',
    `body` = '내용5';         


# adminLebel 컬럼을 authLevel로 변경
ALTER TABLE `article``article``member` CHANGE `adminLevel` `authLevel` TINYINT UNSIGNED DEFAULT 2 NOT NULL COMMENT '0=탈퇴/1=정지/2=일반/3=인증/4=관리자'; 

# 중요한 순서대로 기존 컬럼 위치 변경, cellphoneNo컬럼 추가
ALTER TABLE `member` CHANGE `loginId` `loginId` CHAR(50) NOT NULL AFTER `updateDate`, 
                     CHANGE `loginPw` `loginPw` VARCHAR(200) NOT NULL AFTER `loginId`,
                     ADD COLUMN `cellphoneNo` CHAR(20) NOT NULL AFTER `email`; 

# adminLebel을 authLevel로 컬럼명 변경   
ALTER TABLE `member` CHANGE `adminLevel` `authLevel` TINYINT UNSIGNED DEFAULT 2 NOT NULL COMMENT '0=탈퇴/1=정지/2=일반/3=인증/4=관리자'; 


# 기존회원의 비밀번호를 암호화
UPDATE `member` SET loginPw = SHA2(loginPw, 256);

WHERE id < 8; 이건 아이디 번호가 8 이하인 아이디만 적용


# 71강 attr 추가 
# 부가정보테이블 
# 댓글 테이블 추가
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `relTypeCode` CHAR(20) NOT NULL, #관련 타입 코드, 회원에 관한 정보는 member가 들어가고 게시물은 article이 들어간다
    `relId` INT(10) UNSIGNED NOT NULL, # 관련 데이터 번호, 
    `typeCode` CHAR(30) NOT NULL, # 카테고리 extra__같은거
    `type2Code` CHAR(30) NOT NULL, #카테고리 isTempPasswordUsing 같은거
    `value` TEXT NOT NULL # 값인데 1 또는 0이 들어감
);

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`); # 변수는 똑같은 이름이 2개이상 존재하면 안되서 unique 제약을 걸어둠

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);

# attr에 만료날짜 추가
ALTER TABLE `attr` ADD COLUMN `expireDate` DATETIME NULL AFTER `value`; #expireDate : 만료 날짜를 구현할 수 있다