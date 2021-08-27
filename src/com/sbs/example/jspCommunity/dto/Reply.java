package com.sbs.example.jspCommunity.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Reply {

	private int id;
	private String regDate;
	private String updateDate;
	private String relTypeCode;
	private int relId;
	private String body;
	private int memberId;
	
	private Map<String, Object> extra;
	
	private String extra__writer; // 작성자
	private int extra__likePoint; // 좋아요, 싫어요 총합 개수
	private int extra__likeOnlyPoint; // 좋아요 개수
	private int extra__dislikeOnlyPoint; // 싫어요 개수

	public Reply(Map<String, Object> map) {
		this.id = (int) map.get("id");
		this.regDate = (String) map.get("regDate");
		this.updateDate = (String) map.get("updateDate");
		this.relTypeCode = (String) map.get("relTypeCode");
		this.relId = (int) map.get("relId");
		this.body = (String) map.get("body");
		this.memberId = (int) map.get("memberId");

		if (map.containsKey("extra__writer")) {
			this.extra__writer = (String) map.get("extra__writer");
		}

		if (map.containsKey("extra__likePoint")) {
			this.extra__likePoint = (int) map.get("extra__likePoint");
		}

		if (map.containsKey("extra__likeOnlyPoint")) {
			this.extra__likeOnlyPoint = (int) map.get("extra__likeOnlyPoint");
		}

		if (map.containsKey("extra__dislikeOnlyPoint")) {
			this.extra__dislikeOnlyPoint = (int) map.get("extra__dislikeOnlyPoint");
		}

		this.extra = new LinkedHashMap<>();
	}
}
