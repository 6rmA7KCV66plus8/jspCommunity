package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {

	public List<Article> getForPrintArticlesByBoardId(int boardId, int limitStart, int limitCount, String searchKeyword, String searchKeywordType) {
		List<Article> articles = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__writer"); // 회원이름
		sql.append(", B.name AS extra__boardName"); // 게시판 이름
		sql.append(", B.code AS extra__boardCode"); // 게시판 코드
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint"); // 좋아요, 싫어요 합계
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint"); // 좋아요 개수
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint"); // 싫어요 개수
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'article'");
		sql.append("AND A.id = L.relId");
		
		if(boardId != 0) {
			sql.append("WHERE A.boardId = ?", boardId);
		}
		
		if(searchKeyword != null) {
			if(searchKeywordType == null || searchKeywordType.equals("title")) { // 제목에서 검색
				sql.append("AND A.title LIKE CONCAT('%', ? '%')", searchKeyword);
			}
			else if(searchKeywordType.equals("body")) {
				sql.append("AND A.body LIKE CONCAT('%', ? '%')", searchKeyword); // 내용에서 검색
			}
			else if(searchKeywordType.equals("titleAndbody")) {
				sql.append("AND (A.title LIKE CONCAT('%', ? '%') OR A.body LIKE CONCAT('%', ? '%'))", searchKeyword, searchKeyword); // 제목+내용에서 검색
			}
		}
		
		sql.append("GROUP BY A.id"); //WHERE가 끝난 다음
		
		sql.append("ORDER BY A.id DESC");
//		System.out.println("sql.getRawSql() : " + sql.getRawSql()); // getRawSql : 최종 형태의 쿼리를 출력할 수 있음
		
		if(limitCount != -1) {
			sql.append("LIMIT ?, ?", limitStart, limitCount);
		}
		
		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		
		for (Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}

	public Article getForPrintArticleById(int id) {
		// TODO Auto-generated method stub
		
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__writer"); // 회원이름
		sql.append(", B.name AS extra__boardName"); // 게시판 이름
		sql.append(", B.code AS extra__boardCode"); // 게시판 코드
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint"); // 좋아요, 싫어요 합계
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint"); // 좋아요 개수
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint"); // 싫어요 개수
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'article'");
		sql.append("AND A.id = L.relId");
		sql.append("WHERE A.id = ?", id);
		sql.append("GROUP BY A.id");
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty() ) {
			return null;
		}
			return new Article(map);	
	}

	public Board getBoardById(int id) {
		// TODO Auto-generated method stub
		SecSql sql = new SecSql();
		sql.append("SELECT B.*"); // B의 모든것
		sql.append("FROM board AS B"); // board = B다
		sql.append("WHERE B.id = ?", id); 
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty() ) {
			return null;
		}
			return new Board(map);
	}
	//글쓰기
	public int write(Map<String, Object> args) { //args : arguments
		// TODO Auto-generated method stub
		SecSql sql = new SecSql();
		sql.append("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()"); 
		sql.append(", boardId = ?", args.get("boardId"));
		sql.append(", memberId = ?", args.get("memberId"));
		sql.append(", title = ?", args.get("title"));
		sql.append(", `body` = ?", args.get("body"));
		
		return MysqlUtil.insert(sql);

	}
	//글삭제
	public int delete(int id) {
		// TODO Auto-generated method stub
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		return MysqlUtil.delete(sql);
	}
	//글수정
	public int modify(Map<String, Object> args) {
		// TODO Auto-generated method stub
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		
		boolean needToUpdate = false;
		
		if(args.get("title") != null) { // title에 값이 들어왔을 떄 수정
			needToUpdate = true;
			sql.append(", title = ?", args.get("title"));
		}
		if(args.get("body") != null) { // body에 값이 들어왔을 떄 수정
			needToUpdate = true;
			sql.append(", `body` = ?", args.get("body"));
		}
		
		if(needToUpdate == false) {
		return 0;
		}
		
		sql.append("WHERE Id = ?", args.get("id"));
		
		return MysqlUtil.update(sql);
	}

	public int getArticlesCountByBoardId(int boardId, String searchKeyword, String searchKeywordType) {
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(*) AS cnt");
		sql.append("FROM article AS A");
		sql.append("WHERE 1"); // 실행되어야 할 부분이 2개(boardId, searchKeyword)일 땐 이렇게 sql문을 작성하는게 좋음
		
		if(boardId != 0) {
			sql.append("AND A.boardId = ?", boardId);
		}
		
		if(searchKeyword != null) {
			if(searchKeywordType == null || searchKeywordType.equals("title")) { // 제목에서 검색
				sql.append("AND A.title LIKE CONCAT('%', ? '%')", searchKeyword);
			}
			else if(searchKeywordType.equals("body")) {
				sql.append("AND A.body LIKE CONCAT('%', ? '%')", searchKeyword); // 내용에서 검색
			}
			else if(searchKeywordType.equals("titleAndbody")) {
				sql.append("AND (A.title LIKE CONCAT('%', ? '%') OR A.body LIKE CONCAT('%', ? '%'))", searchKeyword, searchKeyword); // 제목+내용에서 검색
			}
		}
		
		return MysqlUtil.selectRowIntValue(sql);
	}

	public Article getArticleById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append("FROM article AS A");
		sql.append("WHERE A.id = ?", id);
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty() ) {
			return null;
		}
			return new Article(map);	
	}

}
