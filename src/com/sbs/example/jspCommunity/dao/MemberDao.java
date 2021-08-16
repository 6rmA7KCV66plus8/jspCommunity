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

	public int join(Map<String, Object> args) {
		SecSql sql = new SecSql();
		sql.append("INSERT INTO member");
		sql.append("SET regDate = NOW()");
		sql.append(", updateDate = NOW()"); 
		sql.append(", loginId = ?", args.get("loginId"));
		sql.append(", loginPw = ?", args.get("loginPw"));
		sql.append(", `name` = ?", args.get("name"));
		sql.append(", nickname = ?", args.get("nickname"));
		sql.append(", email = ?", args.get("email"));
		sql.append(", cellphoneNo = ?", args.get("cellphoneNo"));
		
		return MysqlUtil.insert(sql);
	}

	public Member getMemberByLoginId(String loginId) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*"); // M.전부를 가져옴
		sql.append("FROM `member` AS M");
		sql.append("WHERE loginId = ?", loginId);
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty()) {
			return null;	
		}
		return new Member(map);	
	}

	public Member getMemberById(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*"); // M.전부를 가져옴
		sql.append("FROM member AS M");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty()) {
			return null;	
		}
		return new Member(map);	
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		SecSql sql = new SecSql();
		sql.append("SELECT M.*");
		sql.append("FROM `member` AS M");
		sql.append("WHERE name = ?", name);
		sql.append("AND email = ?", email);
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT 1");
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if ( map.isEmpty() ) {
			return null;
		}
		
		return new Member(map);
	}

}
