package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Reply;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.ReplyService;
import com.sbs.example.util.Util;

public class UsrReplyController extends Controller {

	private ReplyService replyService;
	private ArticleService articleService;

	public UsrReplyController() {
		replyService = Container.replyService;
		articleService = Container.articleService;
	}
	
	public String doWrite(HttpServletRequest request, HttpServletResponse response) {
		String redirectUrl = request.getParameter("redirectUrl");
		
		int loginedmemberId = (int)request.getAttribute("loginedMemberId");
		
		String relTypeCode = request.getParameter("relTypeCode");
		
		if(relTypeCode == null) {
			return msgAndBack(request, "관련 데이터 타입 코드를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(request.getParameter("relId"), 0);
		
		if(relId == 0) {
			return msgAndBack(request, "관련 데이터 번호를 입력해주세요.");
		}
		
		if(relTypeCode.equals("article")) {
			Article article = articleService.getArticleById(relId); // 게시물리스트에 게시판 이름 표시
			
			if(article == null) {
				return msgAndBack(request, relId + "번 게시물은 존재하지 않습니다.");
			}
		}
		
		
		String body = request.getParameter("body");
		if(Util.isEmpty(body)) { // 타이틀이 공백인지 비어있는지 처리
			return msgAndBack(request, "내용을 입력해주세요.");
		}
		
		Map<String, Object> writeArgs = new HashMap<>();
		writeArgs.put("memberId", loginedmemberId);
		writeArgs.put("relId", relId);
		writeArgs.put("relTypeCode", relTypeCode);
		writeArgs.put("body", body);
		
		int newArticleId = replyService.write(writeArgs);
		
		redirectUrl = redirectUrl.replace("[NEW_REPLY_ID]", newArticleId + "");
		
		return msgAndReplace(request, newArticleId + "번 댓글이 생성되었습니다.", redirectUrl);
		
	}

	public String doDelete(HttpServletRequest request, HttpServletResponse response) {
		String redirectUrl = request.getParameter("redirectUrl");
		
		int loginedmemberId = (int)request.getAttribute("loginedMemberId");
		
		int id = Util.getAsInt(request.getParameter("id"), 0);
		
		if(id == 0) {
			return msgAndBack(request, "번호를 입력해주세요.");
		}
		
		Reply reply = replyService.getReply(id);
		
		if(reply == null) {
			return msgAndBack(request, id + "번 댓글은 존재하지 않습니다.");
		}
		
		if(replyService.actorCanDelete(reply, loginedmemberId) == false) {
			return msgAndBack(request, "삭제 권한이 없습니다.");
		}
		
		replyService.delete(id);
		
		return msgAndReplace(request, id + "번 댓글이 삭제되었습니다.", redirectUrl);
		
	}
	
	public String doModify(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}

}
