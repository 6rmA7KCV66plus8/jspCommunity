package com.sbs.example.jspCommunity.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class MemberDao {

	public List<Member> getForPrintMembers() {
		List<Member> members = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT M.*"); // M.전부를 가져옴
		sql.append("FROM member AS M");
		sql.append("ORDER BY M.id DESC");

		//		System.out.println("sql.getRawSql() : " + sql.getRawSql()); // getRawSql : 최종 형태의 쿼리를 출력할 수 있음
		
		List<Map<String, Object>> mapList = MysqlUtil.selectRows(sql);
		
		for (Map<String, Object> map : mapList) {
			members.add(new Member(map));
		}
		
		return members;
	}


}
