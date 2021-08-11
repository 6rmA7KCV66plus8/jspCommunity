package com.sbs.example.jspCommunity.controller.usr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.service.ArticleService;

public class ArticleController {

	private ArticleService articleService;

	public ArticleController() {
		articleService = Container.articleService;
	}
	public String showList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		
		Board board = articleService.getBoardById(boardId); // 게시물리스트에 게시판 이름 표시
		request.setAttribute("board", board);
		
		List<Article> articles = articleService.getForPrintArticlesByBoardId(boardId);
		
		request.setAttribute("articles", articles);
		
		return "usr/article/list";
	}
	
	public String showDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getForPrintArticleById(id);
		
		if(article == null) {
			request.setAttribute("alertMsg", id + "번 게시물은 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		request.setAttribute("article", article);
		
		return "usr/article/detail";
	}
	//해당 게시판 글 목록 보기
	public String showWrite(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		
		Board board = articleService.getBoardById(boardId); // 게시물리스트에 게시판 이름 표시
		request.setAttribute("board", board);
		
//		int boardId = Integer.parseInt(request.getParameter("boardId")); // 굳이 이런거 적을 필요 없음, 적어도 똑같이 결과가 나옴
//		request.setAttribute("boardId", boardId); // jsp에서도 한번에 파라미터에 접근할 수 있음
		return "usr/article/write";
	}
	//글 작성
	public String doWrite(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		Map<String, Object> writeArgs = new HashMap<>();
		writeArgs.put("memberId", memberId);
		writeArgs.put("boardId", boardId);
		writeArgs.put("title", title);
		writeArgs.put("body", body);
		
		int newArticleId = articleService.write(writeArgs);
		
		request.setAttribute("alertMsg", newArticleId + "번 게시물이 생성되었습니다.");
		request.setAttribute("replaceUrl", String.format("detail?id=%d", newArticleId));
		return "common/redirect";
	}
	//글 삭제
	public String doDelete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		
		Article article = articleService.getForPrintArticleById(id);
		
		if(article == null) { // 게시물이 있는지 없는지 확인 
			request.setAttribute("alertMsg", id + "번 게시물은 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		articleService.delete(id); // articleService에 delete 요청
		
		int boardId = article.getBoardId();
		
		request.setAttribute("alertMsg", id + "번 게시물이 삭제되었습니다.");
		request.setAttribute("replaceUrl", String.format("list?boardId=%d", boardId));
		return "common/redirect";
	}
	//글 수정
	public String showModify(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));

		Article article = articleService.getForPrintArticleById(id);
		
		if(article == null) {
			request.setAttribute("alertMsg", id + "번 게시물은 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		Board board = articleService.getBoardById(article.getBoardId());
		
		request.setAttribute("article", article);
		request.setAttribute("board", board);
		return "usr/article/modify";
	}
	//글 수정
	public String doModify(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Integer.parseInt(request.getParameter("id"));
		
		Article article = articleService.getForPrintArticleById(id);
		
		if(article == null) {
			request.setAttribute("alertMsg", id + "번 게시물은 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		int memberId = Integer.parseInt(request.getParameter("memberId"));
		if(article.getMemberId() != memberId) {
			request.setAttribute("alertMsg", id + "번 게시물에 대한 권한이 없습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		
		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", id);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);
		
		articleService.modify(modifyArgs);
		
		request.setAttribute("alertMsg", id + "번 게시물이 수정되었습니다.");
		request.setAttribute("replaceUrl", String.format("detail?id=%d", id));
		return "common/redirect";
	}

}
