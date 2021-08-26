package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.util.Util;

public class UsrArticleController extends Controller {

	private ArticleService articleService;

	public UsrArticleController() {
		articleService = Container.articleService;
	}
	
	
	public String showList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String searchKeyword = request.getParameter("searchKeyword");
		String searchKeywordType = request.getParameter("searchKeywordType");
		int page = Util.getAsInt(request.getParameter("page"), 1); // 페이지를 받는 부분
		// req.para(page)의 값이 null, 소수점, Long타입, 한글, 등 이어도 1
		int itemsInAPage = 30; // 게시판에 한 페이지당 몇개의 게시물을 보여주는지
		int limitStart = (page -1) * itemsInAPage; // 페이지가 1 페이지 일떄, 30개씩 건너뜀
				
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		
		Board board = articleService.getBoardById(boardId); // 게시물리스트에 게시판 이름 표시
		request.setAttribute("board", board);
		
		int totalCount = articleService.getArticlesCountByBoardId(boardId, searchKeyword, searchKeywordType);
		List<Article> articles = articleService.getForPrintArticlesByBoardId(boardId, limitStart, itemsInAPage, searchKeyword, searchKeywordType);
		
		int totalPage = (int)Math.ceil(totalCount / (double)itemsInAPage);
		
		int pageBoxSize = 10;
		
		// 현재 페이지 박스 시작, 끝 계산
				int previousPageBoxesCount = (page - 1) / pageBoxSize;
				int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
				int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;

				if (pageBoxEndPage > totalPage) {
					pageBoxEndPage = totalPage;
				}

				// 이전버튼 페이지 계산
				int pageBoxStartBeforePage = pageBoxStartPage - 1;
				if (pageBoxStartBeforePage < 1) {
					pageBoxStartBeforePage = 1;
				}

				// 다음버튼 페이지 계산
				int pageBoxEndAfterPage = pageBoxEndPage + 1;

				if (pageBoxEndAfterPage > totalPage) {
					pageBoxEndAfterPage = totalPage;
				}

				// 이전버튼 노출여부 계산
				boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
				// 다음버튼 노출여부 계산
				boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("articles", articles);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("page", page); // ${page.param}이 없을 수도 있으니까 변수를 만듦
		
		request.setAttribute("pageBoxStartBeforeBtnNeedToShow", pageBoxStartBeforeBtnNeedToShow);
		request.setAttribute("pageBoxEndAfterBtnNeedToShow", pageBoxEndAfterBtnNeedToShow);
		request.setAttribute("pageBoxStartBeforePage", pageBoxStartBeforePage);
		request.setAttribute("pageBoxEndAfterPage", pageBoxEndAfterPage);
		request.setAttribute("pageBoxStartPage", pageBoxStartPage);
		request.setAttribute("pageBoxEndPage", pageBoxEndPage);
		
		return "usr/article/list";
	}
	
	public String showDetail(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id = Util.getAsInt(request.getParameter("id"), 0);
		
		if(id == 0) {
			return msgAndBack(request, "게시물 번호를 입력해주세요.");
			
		}
		
		Member loginedMember = (Member)request.getAttribute("loginedMember");
		
		Article article = articleService.getForPrintArticleById(id, loginedMember);
		
		if(article == null) {
			return msgAndBack(request, id + "번 게시물은 존재하지 않습니다.");
			
		}
		
		request.setAttribute("article", article);
		
		return "usr/article/detail";
	}
	
	
	//해당 게시판 글 목록 보기
	public String showWrite(HttpServletRequest request, HttpServletResponse response) {
		
		int boardId = Util.getAsInt(request.getParameter("boardId"), 0);
		
		if(boardId == 0) {
			return msgAndBack(request, "게시판 번호를 입력해주세요.");
			
		}
		
		Board board = articleService.getBoardById(boardId); // 게시물리스트에 게시판 이름 표시
		
		if(board == null) {
			return msgAndBack(request, boardId + "번 게시판은 존재하지 않습니다.");
		}
		
		request.setAttribute("board", board);
		
//		int boardId = Integer.parseInt(request.getParameter("boardId")); // 굳이 이런거 적을 필요 없음, 적어도 똑같이 결과가 나옴
//		request.setAttribute("boardId", boardId); // jsp에서도 한번에 파라미터에 접근할 수 있음
		return "usr/article/write";
	}
	//글 작성
	public String doWrite(HttpServletRequest request, HttpServletResponse response) {
		
		int loginedmemberId = (int)request.getAttribute("loginedMemberId");
		int boardId = Util.getAsInt(request.getParameter("boardId"), 0);
		
		if(boardId == 0) {
			return msgAndBack(request, "게시판 번호를 입력해주세요.");
			
		}
		
		Board board = articleService.getBoardById(boardId); // 게시물리스트에 게시판 이름 표시
		if(board == null) {
			return msgAndBack(request, boardId + "번 게시판은 존재하지 않습니다.");
		}
		
		String title = request.getParameter("title");
		if(Util.isEmpty(title)) { // 타이틀이 공백인지 비어있는지 처리
			return msgAndBack(request, "제목을 입력해주세요.");
		}
		
		String body = request.getParameter("body");
		if(Util.isEmpty(body)) { // 타이틀이 공백인지 비어있는지 처리
			return msgAndBack(request, "내용을 입력해주세요.");
		}
		
		Map<String, Object> writeArgs = new HashMap<>();
		writeArgs.put("memberId", loginedmemberId);
		writeArgs.put("boardId", boardId);
		writeArgs.put("title", title);
		writeArgs.put("body", body);
		
		int newArticleId = articleService.write(writeArgs);
		
		return msgAndReplace(request, newArticleId + "번 게시물이 생성되었습니다.", String.format("detail?id=%d", newArticleId));
	}


	//글 삭제
	public String doDelete(HttpServletRequest request, HttpServletResponse response) {
	
		int id = Util.getAsInt(request.getParameter("id"), 0);
		
		if(id == 0) {
			return msgAndBack(request, "번호를 입력해주세요.");
		}
		
		Article article = articleService.getForPrintArticleById(id);
		if(article == null) {
			return msgAndBack(request, id + "번 게시물은 존재하지 않습니다.");
		}

		int loginedMemberId = (int)request.getAttribute("loginedMemberId");
		if(article.getMemberId() != loginedMemberId) {//작성자와 로그인된 아이디가 같은지 다른지 체크
			return msgAndBack(request, id + "번 게시물에 대한 권한이 없습니다.");
		}
		
		articleService.delete(id); // articleService에 delete 요청
		
		int boardId = article.getBoardId();
	
		return msgAndReplace(request, id + "번 게시물이 삭제되었습니다.", String.format("list?boardId=%d", boardId));
	}
	//글 수정 화면
	public String showModify(HttpServletRequest request, HttpServletResponse response) {
		int id = Util.getAsInt(request.getParameter("id"), 0);
		
		if(id == 0) {
			return msgAndBack(request, "번호를 입력해주세요.");
		}
		
		Article article = articleService.getForPrintArticleById(id);
		if(article == null) {
			return msgAndBack(request, id + "번 게시물은 존재하지 않습니다.");
		}

		int loginedMemberId = (int)request.getAttribute("loginedMemberId");
		if(article.getMemberId() != loginedMemberId) { // 게시물id(작성자)와 로그인id가 같은지 확인, 권한 체크
			return msgAndBack(request, id + "번 게시물에 대한 권한이 없습니다.");
		}
		
		Board board = articleService.getBoardById(article.getBoardId());
		
		request.setAttribute("article", article);
		request.setAttribute("board", board);
		return "usr/article/modify";
	}
	//글 수정
	public String doModify(HttpServletRequest request, HttpServletResponse response) {
		int id = Util.getAsInt(request.getParameter("id"), 0);
		
		if(id == 0) {
			return msgAndBack(request, "번호를 입력해주세요.");
		}
		
		Article article = articleService.getForPrintArticleById(id);
		if(article == null) {
			return msgAndBack(request, id + "번 게시물은 존재하지 않습니다.");
		}
		
		int loginedMemberId = (int)request.getAttribute("loginedMemberId");
		if(article.getMemberId() != loginedMemberId) {
			return msgAndBack(request, id + "번 게시물에 대한 권한이 없습니다.");
		}
		
		String title = request.getParameter("title");
		if(Util.isEmpty(title)) {
			return msgAndBack(request, "제목을 입력해주세요.");
		}
		String body = request.getParameter("body");
		if(Util.isEmpty(body)) {
			return msgAndBack(request, "내용을 입력해주세요.");
		}
		
		Map<String, Object> modifyArgs = new HashMap<>();
		modifyArgs.put("id", id);
		modifyArgs.put("title", title);
		modifyArgs.put("body", body);
		
		articleService.modify(modifyArgs);
		
		return msgAndReplace(request, id + "번 게시물이 수정되었습니다.", String.format("detail?id=%d", id));
	}

}
