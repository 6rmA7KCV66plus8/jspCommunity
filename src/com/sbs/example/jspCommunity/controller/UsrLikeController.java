package com.sbs.example.jspCommunity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.service.LikeService;
import com.sbs.example.util.Util;

public class UsrLikeController extends Controller {
	
	private LikeService likeService;
	
	public UsrLikeController() {
		likeService = Container.likeService;
	}

	public String doLike(HttpServletRequest request, HttpServletResponse response) {
		String relTypeCode = request.getParameter("relTypeCode");
		if(relTypeCode == null) {
			return msgAndBack(request, "관련 데이터 코드를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(request.getParameter("relId"), 0);
		if(relId == 0) {
			return msgAndBack(request, "관련 데이터 번호를 입력해주세요.");
		}
		int actorId = (int)request.getAttribute("loginedMemberId");
		likeService.setLikePoint(relTypeCode, relId, actorId, 1);
		
		return msgAndReplace(request, "좋아요 처리되었습니다.", request.getParameter("redirectUrl"));
	}

	public String doCancelLike(HttpServletRequest request, HttpServletResponse response) {
		String relTypeCode = request.getParameter("relTypeCode");
		if(relTypeCode == null) {
			return msgAndBack(request, "관련 데이터 코드를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(request.getParameter("relId"), 0);
		if(relId == 0) {
			return msgAndBack(request, "관련 데이터 번호를 입력해주세요.");
		}
		int actorId = (int)request.getAttribute("loginedMemberId");
		likeService.setLikePoint(relTypeCode, relId, actorId, 0);
		
		return msgAndReplace(request, "좋아요 취소 처리되었습니다.", request.getParameter("redirectUrl"));
	
	}

	public String doDislike(HttpServletRequest request, HttpServletResponse response) {
		String relTypeCode = request.getParameter("relTypeCode");
		if(relTypeCode == null) {
			return msgAndBack(request, "관련 데이터 코드를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(request.getParameter("relId"), 0);
		if(relId == 0) {
			return msgAndBack(request, "관련 데이터 번호를 입력해주세요.");
		}
		int actorId = (int)request.getAttribute("loginedMemberId");
		likeService.setLikePoint(relTypeCode, relId, actorId, -1);
		
		return msgAndReplace(request, "싫어요 처리되었습니다.", request.getParameter("redirectUrl"));
	
	}

	public String doCancelDislike(HttpServletRequest request, HttpServletResponse response) {
		String relTypeCode = request.getParameter("relTypeCode");
		if(relTypeCode == null) {
			return msgAndBack(request, "관련 데이터 코드를 입력해주세요.");
		}
		
		int relId = Util.getAsInt(request.getParameter("relId"), 0);
		if(relId == 0) {
			return msgAndBack(request, "관련 데이터 번호를 입력해주세요.");
		}
		int actorId = (int)request.getAttribute("loginedMemberId");
		likeService.setLikePoint(relTypeCode, relId, actorId, 0);
		
		return msgAndReplace(request, "싫어요 취소 처리되었습니다.", request.getParameter("redirectUrl"));
	
	}

}
