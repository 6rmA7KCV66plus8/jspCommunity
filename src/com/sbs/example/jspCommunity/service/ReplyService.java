package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.dto.Reply;

public class ReplyService {

	private ReplyDao replyDao;
	private MemberService memberService;

	public ReplyService() {
		replyDao = Container.replyDao;
		memberService = Container.memberService; // 컨테이너에서 리플리 서비스가 만들어지기 전에(보다 위에 위치) 멤버 서비스가 있어야 함
	}
	public int write(Map<String, Object> args) {
		return replyDao.write(args);
	}
	public List<Reply> getForPrintReplise(String relTypeCode, int relId) {
		return replyDao.getForPrintReplise(relTypeCode, relId);
	}
	public Reply getReply(int id) {
		return replyDao.getReply(id);
	}
	public boolean actorCanDelete(Reply reply, int actorId) {
		if(memberService.isAdmin(actorId) ) { // 소유권을 따지기전에 actorId가 관리자면
			return true; // true
		}
		return reply.getMemberId() == actorId;
	}
	public int delete(int id) {
		return replyDao.delete(id);
	}

}
