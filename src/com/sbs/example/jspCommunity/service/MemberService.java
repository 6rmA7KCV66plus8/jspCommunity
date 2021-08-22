package com.sbs.example.jspCommunity.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.App;
import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.jspCommunity.dto.ResultData;
import com.sbs.example.util.Util;

public class MemberService {

	private MemberDao memberDao; // return 부분의  memberDao 필드 생성
	private EmailService emailService; 
	private AttrService attrService; 
	
	public MemberService() {
		memberDao = Container.memberDao;
		emailService = Container.emailService;
		attrService = Container.attrService; 
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

	public ResultData sendTempLoginPwToEmail(Member actor) { // actor : 수행자/ 서비스 레이어에서 member 대신 많이 씀
		// 메일 제목, 내용 만들기
		String siteName = App.getSite();
		String siteLoginUrl = App.getLoginUrl();
		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Util.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteLoginUrl + "\" target=\"_blank\">로그인 하러가기</a>";
		
		Map<String, Object> rs = new HashMap<>();
		
		// 메일 발송
		int sendRs = emailService.send(actor.getEmail(), title, body);
		
		ResultData rd = null;
		
		if(sendRs != 1) {
			return new ResultData("F-1", "메일 발송에 실패하였습니다.");
		}
			// 회원의 패스워드를 방금 생성한 임시패스워드로 변경
			setTempPassword(actor, tempPassword);
		
		String resultMsg = String.format("임시 패스워드가 %s (으)로 발송되었습니다.", actor.getEmail());
		return new ResultData("S-1", resultMsg, "email", actor.getEmail());
		
	}

	private void setTempPassword(Member actor, String tempPassword) {
		Map<String, Object> modifyParam = new HashMap<>(); // 해쉬 맵 생성
		modifyParam.put("id", actor.getId());
		modifyParam.put("loginPw", Util.sha256(tempPassword));
		modify(modifyParam);
	
		setIsUsingTempPassword(actor.getId(), true);
	}

	public void setIsUsingTempPassword(int actorId, boolean use) {
		attrService.setValue("member__" + actorId + "__extra__isUsingTempPassword", use, null);
	}
	
	public boolean getIsUsingTempPassword(int actorId) {
		return attrService.getValueAsBoolean("member__" + actorId + "__extra__isUsingTempPassword");
	}

	public void modify(Map<String, Object> param) {
		memberDao.modify(param); // memberDao 한테 param을 넘김
	}
}
