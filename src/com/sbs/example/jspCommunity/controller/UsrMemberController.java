package com.sbs.example.jspCommunity.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;
import com.sbs.example.util.Util;

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
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) {
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		return "usr/member/join";
	}
	//
	public String doJoin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) {
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String cellphoneNo = request.getParameter("cellphoneNo");
			// 기존 멤버
		Member oldMember = memberService.getMemberByLoginId(loginId); // 아이디 중복검사
		if(oldMember != null) {
			request.setAttribute("alertMsg", "이미 존재하는 아이디 입니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("name", name);
		joinArgs.put("nickname", nickname);
		joinArgs.put("email", email);
		joinArgs.put("cellphoneNo", cellphoneNo);
		
		int newArticleId = memberService.join(joinArgs);
		
		request.setAttribute("alertMsg", newArticleId + "번 회원이 생성되었습니다.");
		request.setAttribute("replaceUrl", "../home/main");
		return "common/redirect";
	}
	
	public String showLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) {
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		return "usr/member/login";
	}
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
	
		if((boolean)request.getAttribute("isLogined") == false) {
			request.setAttribute("alertMsg", "이미 로그아웃이 되어있습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");
		
		request.setAttribute("alertMsg", "로그아웃이 되었습니다.");
		request.setAttribute("replaceUrl", "../home/main");
		return "common/redirect";
	}
	
	
	public String doLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) {
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		
		Member member = memberService.getMemberByLoginId(loginId); // 아이디 중복검사
		if(member == null) {
			request.setAttribute("alertMsg", "해당 계정이 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		if(member.getLoginPw().equals(loginPw) == false) {
			request.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		//로그인 처리
		session.setAttribute("loginedMemberId", member.getId());

		request.setAttribute("alertMsg", String.format("%s님 환영합니다.", member.getNickname()));
		request.setAttribute("replaceUrl", "../home/main");
		return "common/redirect";
	}
	
	// 아이디 중복검사
	public String getLoginIdDup(HttpServletRequest request, HttpServletResponse response) {
		String loginId = request.getParameter("loginId");
		
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		Map<String, Object> rs = new HashMap<>();
		
		String resultCode = null; // 이런거 할 땐 널을 넣어주는게 좋음
		String msg = null;
		
		if(member != null) { // 널이 아니니깐 값은 1이므로 계정이 이미 사용중인 상태
			resultCode ="F-1";
			msg ="이미 사용중인 아이디 입니다.";
		} else {
			resultCode ="S-1";
			msg ="사용이 가능한 아이디 입니다.";
		}
		rs.put("resultCode", resultCode);
		rs.put("msg", msg);
		rs.put("loginId", loginId);
		
		request.setAttribute("data", Util.getJsonText(rs));
		return "common/pure";
	}
}
