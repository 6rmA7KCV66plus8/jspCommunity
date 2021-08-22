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
import com.sbs.example.jspCommunity.dto.ResultData;
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
		String loginPw = request.getParameter("loginPwReal"); // 57강. 암호화한 비밀번호로 수정
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
		String loginPw = request.getParameter("loginPwReal"); // 57강. 암호화한 비밀번호로 수정
		
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

		boolean IsUsingTempPassword = memberService.getIsUsingTempPassword(member.getId());
		
		String alertMsg = String.format("%s님 환영합니다.", member.getNickname());
		String replaceUrl = "../home/main";
				
		if(IsUsingTempPassword) {
			alertMsg = String.format("%s님은 현재 임시 비밀번호를 사용중 입니다. 비밀번호를 변경 후 이용해주세요.", member.getNickname());
			replaceUrl = "../member/modify";
		}
		
		request.setAttribute("alertMsg", alertMsg);
		request.setAttribute("replaceUrl", replaceUrl);
		return "common/redirect";
	}
	
	// 아이디 중복검사
	public String getLoginIdDup(HttpServletRequest request, HttpServletResponse response) {
		String loginId = request.getParameter("loginId");

		Member member = memberService.getMemberByLoginId(loginId);
		
		String resultCode = null; // 이런거 할 땐 널을 넣어주는게 좋음
		String msg = null;
		
		if(member != null) { // 널이 아니니깐 값은 1이므로 계정이 이미 사용중인 상태
			resultCode ="F-1";
			msg ="이미 사용중인 아이디 입니다.";
		} else {
			resultCode ="S-1";
			msg ="사용이 가능한 아이디 입니다.";
		}

		request.setAttribute("data", new ResultData(resultCode, msg, "loginId", loginId));
		return "common/json";
	}
	// 아이디 찾기
	public String showFindLoginId(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) { // 로그아웃 상태인지 확인
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		return "usr/member/findLoginId";
	}
	// 아이디 찾기
	public String doFindLoginId(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) { // 로그아웃 상태인지 확인
			request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		Member member = memberService.getMemberByNameAndEmail(name, email); // 아이디 중복검사
		if(member == null) {
			request.setAttribute("alertMsg", "일치하는 회원이 존재하지 않습니다.");
			request.setAttribute("historyBack", true);
			return "common/redirect";
		}

		request.setAttribute("alertMsg", String.format("아이디는 %s 입니다.", member.getLoginId()));
		request.setAttribute("replaceUrl", "../member/login");
		return "common/redirect";
	}
	// 비밀번호 찾기
		public String showFindLoginPw(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			if(session.getAttribute("loginedMemberId") != null) { // 로그아웃 상태인지 확인
				request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
				request.setAttribute("historyBack", true);
				return "common/redirect";
			}
			
			return "usr/member/findLoginPw";
		}
		// 비밀번호 찾기
		public String doFindLoginPw(HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession();
			if(session.getAttribute("loginedMemberId") != null) { // 로그아웃 상태인지 확인
				request.setAttribute("alertMsg", "로그아웃 후 진행해주세요.");
				request.setAttribute("historyBack", true);
				return "common/redirect";
			}
			
			String loginId = request.getParameter("loginId");
			String email = request.getParameter("email");
			
			Member member = memberService.getMemberByLoginId(loginId);
			if(member == null) { // 작성한 아이디 유무 확인
				request.setAttribute("alertMsg", "일치하는 회원이 존재하지 않습니다.");
				request.setAttribute("historyBack", true);
				return "common/redirect";
			}
			if(member.getEmail().equals(email) == false) {
				request.setAttribute("alertMsg", "일치하는 이메일이 존재하지 않습니다.");
				request.setAttribute("historyBack", true);
				return "common/redirect";
			}
			// 임시 비밀번호 발송
			ResultData sendTempLoginPwToEmailRs = memberService.sendTempLoginPwToEmail(member);
			
			if(sendTempLoginPwToEmailRs.isFail()) { // 발송 실패한지 sendTempLoginPwToEmailRs에 물어봄
				request.setAttribute("alertMsg", sendTempLoginPwToEmailRs.getMsg());
				request.setAttribute("historyBack", true);
				return "common/redirect";
			}
			request.setAttribute("alertMsg", sendTempLoginPwToEmailRs.getMsg());
			request.setAttribute("replaceUrl", "../member/login");
			return "common/redirect";
		}
		public String showModify(HttpServletRequest request, HttpServletResponse response) {
			return "usr/member/modify";
		}
		public String doModify(HttpServletRequest request, HttpServletResponse response) {
			int loginedMemberId = (int)request.getAttribute("loginedMemberId"); // 아이디 값을 받아옴
			
			String loginPw = (String)request.getParameter("loginPwReal");
			
			if( loginPw != null && loginPw.length() == 0) { // 입력한 패스워드 값이 null이 아닌데 길이가 0(입력을 안함)이면 
				loginPw = null; // 패스워드는 null;
			}
			String name = (String)request.getParameter("name");
			String nickname = (String)request.getParameter("nickname");
			String email = (String)request.getParameter("email");
			String cellphoneNo = (String)request.getParameter("cellphoneNo");
			
			
			Map<String, Object> modifyParam = new HashMap<>();
			modifyParam.put("loginPw", loginPw);
			modifyParam.put("name", name);
			modifyParam.put("nickname", nickname);
			modifyParam.put("email", email);
			modifyParam.put("cellphoneNo", cellphoneNo);
			modifyParam.put("id", loginedMemberId);
			
			memberService.modify(modifyParam);
			
			if(loginPw != null) {
				memberService.setIsUsingTempPassword(loginedMemberId, false);
			}
			
			request.setAttribute("alertMsg", "정보수정이 완료 되었습니다.");
			request.setAttribute("replaceUrl", "../home/main");
			return "common/redirect";
		}
	
}
