package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Reply;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ReplyDao {

	public int write(Map<String, Object> args) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO reply");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()"); 
		sql.append(", relTypeCode = ?", args.get("relTypeCode"));
		sql.append(", relId = ?", args.get("relId"));
		sql.append(", memberId = ?", args.get("memberId"));
		sql.append(", `body` = ?", args.get("body"));
		
		return MysqlUtil.insert(sql);

	}

	public List<Reply> getForPrintReplise(String relTypeCode, int relId) {
		List<Reply> replise = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT R.*");
		sql.append(", M.name AS extra__writer"); // 작성자 이름
		sql.append(", IFNULL(SUM(L.point), 0) AS extra__likePoint"); // 좋아요, 싫어요 합계
		sql.append(", IFNULL(SUM(IF(L.point > 0, L.point, 0)), 0) AS extra__likeOnlyPoint"); // 좋아요 개수
		sql.append(", IFNULL(SUM(IF(L.point < 0, L.point * -1, 0)), 0) extra__dislikeOnlyPoint"); // 싫어요 개수
		sql.append("FROM reply AS R");
		sql.append("INNER JOIN `member` AS M");
		sql.append("ON R.memberId = M.id");
		sql.append("LEFT JOIN `like` AS L");
		sql.append("ON L.relTypeCode = 'article'");
		sql.append("AND R.id = L.relId");
		sql.append("WHERE 1");
		sql.append("AND R.relTypeCode = ?", relTypeCode);
		sql.append("AND R.relId = ?", relId);
		sql.append("GROUP BY R.id"); //WHERE가 끝난 다음
		sql.append("ORDER BY R.id DESC");
		
		List<Map<String, Object>> mapList = MysqlUtil.selectRows(sql);
		
		for (Map<String, Object> map : mapList) {
			replise.add(new Reply(map));
		}
		
		return replise;
	}
}
