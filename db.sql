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
    adminLevel TINYINT(1) UNSIGNED NOT NULL DEFAULT 2 COMMENT '0=탈퇴/1=정지/2=일반/3=인증/4=관리자'
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