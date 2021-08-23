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

public class UsrMemberController extends Controller {
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
		return "usr/member/join";
	}
	// 회원가입 DispatcherServlet에서 로그인, 로그아웃 체크 함
	public String doJoin(HttpServletRequest request, HttpServletResponse response) {
		String loginId = request.getParameter("loginId");
		if(Util.isEmpty(loginId) ) {
			return msgAndBack(request, "아이디를 입력해주세요.");
		}
		String loginPw = request.getParameter("loginPwReal"); // 57강. 암호화한 비밀번호로 수정
		if(Util.isEmpty(loginPw) ) {
			return msgAndBack(request, "비밀번호를 입력해주세요.");
		}
		String name = request.getParameter("name");
		if(Util.isEmpty(name) ) {
			return msgAndBack(request, "이름을 입력해주세요.");
		}
		String nickname = request.getParameter("nickname");
		if(Util.isEmpty(nickname) ) {
			return msgAndBack(request, "닉네임을 입력해주세요.");
		}
		String email = request.getParameter("email");
		if(Util.isEmpty(email) ) {
			return msgAndBack(request, "이메일을 입력해주세요.");
		}
		String cellphoneNo = request.getParameter("cellphoneNo");
		if(Util.isEmpty(cellphoneNo) ) {
			return msgAndBack(request, "연락처를 입력해주세요.");
		}
		
			// 기존 멤버
		Member oldMember = memberService.getMemberByLoginId(loginId); // 아이디 중복검사
		if(oldMember != null) {
			return msgAndBack(request, "이미 존재하는 아이디 입니다.");
		}
		Map<String, Object> joinArgs = new HashMap<>();
		joinArgs.put("loginId", loginId);
		joinArgs.put("loginPw", loginPw);
		joinArgs.put("name", name);
		joinArgs.put("nickname", nickname);
		joinArgs.put("email", email);
		joinArgs.put("cellphoneNo", cellphoneNo);
		
		int newArticleId = memberService.join(joinArgs);

		return msgAndReplace(request, newArticleId + "번 회원이 생성되었습니다.", "../home/main");
	}
	
	public String showLogin(HttpServletRequest request, HttpServletResponse response) {
		return "usr/member/login";
	}
	
	public String doLogout(HttpServletRequest request, HttpServletResponse response) {
	
		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");

		return msgAndReplace(request, "로그아웃이 되었습니다.", "../home/main");

	}
	
	
	public String doLogin(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		
		String loginId = request.getParameter("loginId");
		if(Util.isEmpty(loginId)) {
			return msgAndBack(request, "아이디를 입력해주세요.");
		}
		String loginPw = request.getParameter("loginPwReal"); // 57강. 암호화한 비밀번호로 수정
		if(Util.isEmpty(loginPw)) {
			return msgAndBack(request, "비밀번호를 입력해주세요.");
		}	
		Member member = memberService.getMemberByLoginId(loginId); // 아이디 중복검사
		if(member == null) {
			return msgAndBack(request, "해당 계정이 존재하지 않습니다.");
		}
		if(member.getLoginPw().equals(loginPw) == false) {
			return msgAndBack(request, "비밀번호가 일치하지 않습니다.");
		}
		//로그인 처리
		session.setAttribute("loginedMemberId", member.getId());

		String alertMsg = String.format("%s님 환영합니다.", member.getNickname());
		String replaceUrl = "../home/main";
			// empty : 값이 없다 == false : 값이 있다
		if(Util.isEmpty(request.getParameter("afterLoginUrl")) == false) { // 값이 있다.
			replaceUrl = request.getParameter("afterLoginUrl");
		}
		
		boolean isUsingTempPassword = memberService.isUsingTempPassword(member.getId());
		
		if(isUsingTempPassword) {
			alertMsg = String.format("%s님은 현재 임시 비밀번호를 사용중 입니다. 비밀번호를 변경 후 이용해주세요.", member.getNickname());
			replaceUrl = "../member/modify";
		}
		
		boolean isNeedToModifyOldLoginPw = memberService.isNeedToModifyOldLoginPw(member.getId());
		
		if(isNeedToModifyOldLoginPw) {
			int oldPasswordDays = memberService.getOldPasswordDays();
			alertMsg = String.format("마지막 비밀번호 변경일로부터 " + oldPasswordDays + "일이 경과했습니다. 비밀번호를 변경해주세요.", member.getNickname());
			replaceUrl = "../member/modify";
		}
		
		return msgAndReplace(request, alertMsg, replaceUrl);
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

		return json(request, new ResultData(resultCode, msg, "loginId", loginId));
	}
	// 아이디 찾기
	public String showFindLoginId(HttpServletRequest request, HttpServletResponse response) {
		return "usr/member/findLoginId";
	}
	
	// 아이디 찾기
	public String doFindLoginId(HttpServletRequest request, HttpServletResponse response) {
		
		String name = request.getParameter("name");
		if(Util.isEmpty(name)) {
			return msgAndBack(request, "이름을 입력해주세요.");
		}
		String email = request.getParameter("email");
		if(Util.isEmpty(email)) {
			return msgAndBack(request, "이메일을 입력해주세요.");
		}
		
		Member member = memberService.getMemberByNameAndEmail(name, email); // 아이디 중복검사
		if(member == null) {
			return msgAndBack(request, "일치하는 회원이 존재하지 않습니다.");
		}

		return msgAndReplace(request, String.format("아이디는 %s 입니다.", member.getLoginId()), "../member/login");
	}
	// 비밀번호 찾기
		public String showFindLoginPw(HttpServletRequest request, HttpServletResponse response) {
			return "usr/member/findLoginPw";
		}
		// 비밀번호 찾기
		public String doFindLoginPw(HttpServletRequest request, HttpServletResponse response) {
			
			String loginId = request.getParameter("loginId");
			if(Util.isEmpty(loginId)) {
				return msgAndBack(request, "아이디를 입력해주세요.");
			}
			String email = request.getParameter("email");
			if(Util.isEmpty(email)) {
				return msgAndBack(request, "이메일을 입력해주세요.");
			}
			
			Member member = memberService.getMemberByLoginId(loginId);
			if(member == null) { // 작성한 아이디 유무 확인
				return msgAndBack(request, "일치하는 회원이 존재하지 않습니다.");
			}
			if(member.getEmail().equals(email) == false) {
				return msgAndBack(request, "일치하는 이메일이 존재하지 않습니다.");
			}
			// 임시 비밀번호 발송
			ResultData sendTempLoginPwToEmailRs = memberService.sendTempLoginPwToEmail(member);
			
			if(sendTempLoginPwToEmailRs.isFail()) { // 발송 실패한지 sendTempLoginPwToEmailRs에 물어봄
				return msgAndBack(request, sendTempLoginPwToEmailRs.getMsg());
			}
			return msgAndReplace(request, sendTempLoginPwToEmailRs.getMsg(), "../member/login");

		}
		public String showModify(HttpServletRequest request, HttpServletResponse response) {
			return "usr/member/modify";
		}
		public String doModify(HttpServletRequest request, HttpServletResponse response) {
			int loginedMemberId = (int)request.getAttribute("loginedMemberId"); // 아이디 값을 받아옴
			
			String loginPw = request.getParameter("loginPwReal"); // 정보 수정 시 비밀번호를 입력 안하는 경우도 있으니 체크할 필요 없음
			
			if( loginPw != null && loginPw.length() == 0) { // 입력한 패스워드 값이 null이 아닌데 길이가 0(입력을 안함)이면 
				loginPw = null; // 패스워드는 null;
			}
			String name = request.getParameter("name");
			if(Util.isEmpty(name)) {
				return msgAndBack(request, "이름을 입력해주세요.");
			}
			String nickname = request.getParameter("nickname");
			if(Util.isEmpty(nickname)) {
				return msgAndBack(request, "닉네임을 입력해주세요.");
			}
			String email = request.getParameter("email");
			if(Util.isEmpty(email)) {
				return msgAndBack(request, "이메일을 입력해주세요.");
			}
			String cellphoneNo = request.getParameter("cellphoneNo");
			if(Util.isEmpty(cellphoneNo)) {
				return msgAndBack(request, "연락처를 입력해주세요.");
			}
			
			
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

			return msgAndReplace(request, "정보수정이 완료 되었습니다.", "../home/main");
		}
	
}
