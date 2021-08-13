package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {

	public List<Article> getForPrintArticlesByBoardId(int boardId) {
		List<Article> articles = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT A.*");
		sql.append(", M.name AS extra__writer"); // 회원이름
		sql.append(", B.name AS extra__boardName"); // 게시판 이름
		sql.append(", B.code AS extra__boardCode"); // 게시판 코드
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		if(boardId != 0) {
			sql.append("WHERE A.boardId = ?", boardId);
		}
		sql.append("ORDER BY A.id DESC");
//		System.out.println("sql.getRawSql() : " + sql.getRawSql()); // getRawSql : 최종 형태의 쿼리를 출력할 수 있음
		
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
		sql.append("FROM article AS A");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON A.memberId = M.id");
		sql.append("INNER JOIN `board` AS B");
		sql.append("ON A.boardId = B.id");
		sql.append("WHERE A.id = ?", id);
		
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

}
