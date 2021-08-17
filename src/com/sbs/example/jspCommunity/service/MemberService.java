package com.sbs.example.jspCommunity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.App;
import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.util.Util;

public class MemberService {

	private MemberDao memberDao; // return 부분의  memberDao 필드 생성
	private EmailService emailService; 
	
	public MemberService() {
		memberDao = Container.memberDao;
		emailService = Container.emailService;
	}
	
	public List<Member> getForPrintMembers() {
		return memberDao.getForPrintMembers();
	}

	public int join(Map<String, Object> args) {
		return memberDao.join(args);
	}

	public Member getMemberByLoginId(String loginId) {
		// TODO Auto-generated method stub
		return memberDao.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		// TODO Auto-generated method stub
		return memberDao.getMemberById(id);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberDao.getMemberByNameAndEmail(name, email);
	}

	public void sendTempLoginPwToEmail(Member actor) { // actor : 수행자/ 서비스 레이어에서 member 대신 많이 씀
		// 메일 제목, 내용 만들기
		String siteName = App.getSite();
		String siteLoginUrl = App.getLoginUrl();
		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Util.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteLoginUrl + "\" target=\"_blank\">로그인 하러가기</a>";
		
		// 메일 발송
		emailService.send(actor.getEmail(), title, body);
		
		// 회원의 패스워드를 방금 생성한 임시패스워드로 변경
		setTempPassword(actor, tempPassword);
	}

	private void setTempPassword(Member actor, String tempPassword) {
		Map<String, Object> modifyParam = new HashMap<>(); // 해쉬 맵 생성
		modifyParam.put("id", actor.getId());
		modifyParam.put("loginPw", Util.sha256(tempPassword));
		modify(modifyParam);
	}

	private void modify(Map<String, Object> param) {
		memberDao.modify(param); // memberDao 한테 param을 넘김
	}
}
