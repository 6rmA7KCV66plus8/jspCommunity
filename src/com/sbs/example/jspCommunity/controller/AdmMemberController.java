package com.sbs.example.jspCommunity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.service.MemberService;

public class AdmMemberController extends Controller {
	private MemberService memberService;
	
	public AdmMemberController() {
		memberService = Container.memberService;
		
	}
	public String showList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Member> members = memberService.getForPrintMembers(); // memberService 불러서 members에 넣주기 전 단계
	
		request.setAttribute("members", members); // members를 담는다
		
		return "adm/member/list";
	}
}
