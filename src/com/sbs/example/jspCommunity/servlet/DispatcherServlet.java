package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;

public abstract class DispatcherServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		run(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(requset, response);
	}
	
	
	
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> doBeforeActionRs = dobeforeAction(request, response); //run에 의해서 호출이 되면 doBeforeActionRs에 정보가 들어감
			if(doBeforeActionRs == null) { // 그 정보가 없으면 Map<String, Object> 여기서 처리를 잘 못한거니까 
				return; // 리턴, 널이 아니면 doAction(71)으로 정보를 넘겨주는데
			}
		
		String jspPath = doAction(request, response, (String)doBeforeActionRs.get("controllerName"), (String)doBeforeActionRs.get("actionMethodName"));
			if(jspPath == null) {
				response.getWriter().append("jsp 정보가 없습니다");
				return;
			}
			
		doAfterAction(request, response, jspPath); // doAction에서 jspPath를 받아서 넣어줌
	}


	// 어떠한 컨트롤 액션이 실행되기 전에 무조건 해야하는 일들
	private Map<String, Object> dobeforeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 입력받은 req 셋팅
		response.setContentType("text/html; charset=UTF-8"); // 입력받은 resp 셋팅
		
		String requestUri = request.getRequestURI(); // URI : /usr/a/b/c 같은걸 말함
		String[] requestUriBits = requestUri.split("/"); // URI를 /로 나눔 
		
		if(requestUriBits.length < 5) {
			response.getWriter().append("올바른 요청이 아닙니다.");
			return null; // 올바른 요청이 아니면 null로 빠짐
		}
		//db 연결. db는 사용후 끊어줘야함 <48번줄>
		MysqlUtil.setDBInfo("127.0.0.1", "kjm", "1234", "jspCommunity");
		
		String controllerName = requestUriBits[3]; // member, article 같은 부분
		String actionMethodName = requestUriBits[4]; // list.jsp 파일 같은 부분
		
		boolean isLogined = false; // isLogined 가 false면 로그인을 안한것
		int loginedMemberId = 0; // 로그인을 안했다는 뜻
		Member loginedMember = null;

		HttpSession session = request.getSession();
		if(session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)session.getAttribute("loginedMemberId");
			loginedMember = Container.memberService.getMemberById(loginedMemberId);
		}
		
		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("loginedMember", loginedMember);
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("controllerName", controllerName); // 어떤 컨트롤과 액션메소드가 호출될지 정보를 정한다음 
		rs.put("actionMethodName", actionMethodName);

		return rs; // 그 정보를 리턴해줌
	}
	
// 추상메소드 abstract : private면 자식이 상속을 못 받음, 얘같은 경우는 자식 메소드가 무조건 오버라이드를 해야 함
	protected abstract String doAction(HttpServletRequest request, HttpServletResponse response, String controllerName, String actionMethodName);

	private void doAfterAction(HttpServletRequest request, HttpServletResponse response, String jspPath) throws ServletException, IOException {
		MysqlUtil.closeConnection();
		
																//UriBits = /1/2/3/4
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/" + jspPath +".jsp");
			rd.forward(request, response);
	}
}
