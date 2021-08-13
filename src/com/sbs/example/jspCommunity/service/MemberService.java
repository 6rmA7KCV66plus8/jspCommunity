package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.dto.Member;

public class MemberService {

	private MemberDao memberDao; // return 부분의  memberDao 필드 생성

	public MemberService() {
		memberDao = Container.memberDao;
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

}
