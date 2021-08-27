package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ReplyDao;
import com.sbs.example.jspCommunity.dto.Reply;

public class ReplyService {

	private ReplyDao replyDao;

	public ReplyService() {
		replyDao = Container.replyDao;
	}
	public int write(Map<String, Object> args) {
		return replyDao.write(args);
	}
	public List<Reply> getForPrintReplise(String relTypeCode, int relId) {
		return replyDao.getForPrintReplise(relTypeCode, relId);
	}

}
