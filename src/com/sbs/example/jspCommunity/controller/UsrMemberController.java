package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;

public class UsrMemberController {
	private MemberService memberService;
	
	public UsrMemberController() {
		memberService = Container.memberService;
		
	}
	public String showList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Member> members = memberService.getForPrintMembers(); // memberService 불러서 members에 넣주기 전 단계
	
		request.setAttribute("members", members); // members를 담는다
		
		return "usr/member/list";
	}
	
	public String showJoin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		return "usr/member/join";
	}
	//글 작성
	public String doJoin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		//String cellphoneNo = request.getParameter("cellphoneNo");
		
		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("name", name);
		joinArgs.put("nickname", nickname);
		joinArgs.put("email", email);
		//joinArgs.put("cellphoneNo", cellphoneNo);
		
		int newArticleId = memberService.join(joinArgs);
		
		request.setAttribute("alertMsg", newArticleId + "번 회원이 생성되었습니다.");
		request.setAttribute("replaceUrl", "join");
		return "common/redirect";
	}
}
