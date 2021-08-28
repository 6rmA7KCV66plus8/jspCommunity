package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.example.jspCommunity.App;
import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.util.Util;

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
		Map<String, Object> doBeforeActionRs = doBeforeAction(request, response); //run에 의해서 호출이 되면 doBeforeActionRs에 정보가 들어감
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
	private Map<String, Object> doBeforeAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 입력받은 req 셋팅
		response.setContentType("text/html; charset=UTF-8"); // 입력받은 resp 셋팅
		
		String requestUri = request.getRequestURI(); // URI : /usr/a/b/c 같은걸 말함
		String[] requestUriBits = requestUri.split("/"); // URI를 /로 나눔 
		
		int minBitsCount = 5;
		
		if(App.isProductMode()) { //참이면
			minBitsCount = 4;
		}
		
		if(requestUriBits.length < minBitsCount) {
			response.getWriter().append("올바른 요청이 아닙니다.");
			return null; // 올바른 요청이 아니면 null로 빠짐
		}
		
		if(App.isProductMode()) {
		
			MysqlUtil.setDBInfo("127.0.0.1", "kjmLocal", "1234", "jspCommunityReal"); // production 용
		} else {
			//db 연결. db는 사용후 끊어줘야함<line:178>
			MysqlUtil.setDBInfo("127.0.0.1", "kjm", "1234", "jspCommunity"); // 내 pc 톰캣
			MysqlUtil.setDevMode(true);
		}
		
		int controllerTypeNameIndex = 2;
		int controllerNameIndex = 3;
		int actionMethodNameIndex = 4;
		
		if(App.isProductMode()) { //개발모드가 참이면 = 개발모드 이면
			controllerTypeNameIndex = 1;
			controllerNameIndex = 2;
			actionMethodNameIndex = 3;
		}
		
		String controllerTypeName = requestUriBits[controllerTypeNameIndex]; // usr
		String controllerName = requestUriBits[controllerNameIndex]; // member, article 같은 부분
		String actionMethodName = requestUriBits[actionMethodNameIndex]; // list.jsp 파일 같은 부분
		
		String actionUrl = "/" + controllerTypeName + "/" + controllerName + "/" + actionMethodName;
		
		//데이터 추가 인터셉터 시작
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
		
		
		String currentUrl = request.getRequestURI();

		if (request.getQueryString() != null) {
			currentUrl += "?" + request.getQueryString();
		}

		String encodedCurrentUrl = Util.getUrlEncoded(currentUrl);

		request.setAttribute("currentUrl", currentUrl);
		request.setAttribute("encodedCurrentUrl", encodedCurrentUrl);
		
		Map<String, Object> param = Util.getParamMap(request);
		String paramJson = Util.getJsonText(param);
		
		request.setAttribute("paramMap", param);
		request.setAttribute("paramJson", paramJson);
		
		// 데이터 추가 인터셉터 끝
		
		// 로그인 필요 필터링 인터셉터 시작
		List<String> needToLoginActionUrls = new ArrayList<>();
		
		// 로그인을 해야 들어갈 수 있는 부분들
		needToLoginActionUrls.add("/usr/member/doLogout");
		needToLoginActionUrls.add("/usr/member/modify");
		needToLoginActionUrls.add("/usr/member/doModify");
		needToLoginActionUrls.add("/usr/article/write");
		needToLoginActionUrls.add("/usr/article/doWrite");
		needToLoginActionUrls.add("/usr/article/modify");
		needToLoginActionUrls.add("/usr/article/doModify");
		needToLoginActionUrls.add("/usr/article/doDelete");
		
		needToLoginActionUrls.add("/usr/reply/doWrite");
		needToLoginActionUrls.add("/usr/reply/doDelete");
		needToLoginActionUrls.add("/usr/reply/doModify");
	
		if(needToLoginActionUrls.contains(actionUrl)) { // 내가 이동하려는 곳이 리스트 6곳이면
			if((boolean)request.getAttribute("isLogined") == false) { // 로그인이 안되어있음
				request.setAttribute("alertMsg", "로그인을 해주세요.");
				request.setAttribute("replaceUrl", "../member/login?afterLoginUrl=" + encodedCurrentUrl);
				
				RequestDispatcher rd = request.getRequestDispatcher(getJspDirPath() + "/common/redirect.jsp");
				rd.forward(request, response);
			}
		}
		// 로그아웃 필요 인터셉터 시작
		List<String> needToLogoutActionUrls = new ArrayList<>();
		
		// 로그아웃을 해야 들어갈 수 있는 부분들
		needToLogoutActionUrls.add("/usr/member/login");
		needToLogoutActionUrls.add("/usr/member/doLogin");
		needToLogoutActionUrls.add("/usr/member/join");
		needToLogoutActionUrls.add("/usr/member/doJoin");
		needToLogoutActionUrls.add("/usr/member/findLoginId");
		needToLogoutActionUrls.add("/usr/member/dofindLoginId");
		needToLogoutActionUrls.add("/usr/member/findLoginPw");
		needToLogoutActionUrls.add("/usr/member/dofindLoginPw");
		
		if(needToLogoutActionUrls.contains(actionUrl)) { // 내가 이동하려는 곳이 리스트 4곳이면
			if((boolean)request.getAttribute("isLogined")) { // 로그인이 되어있음
				request.setAttribute("alertMsg", "로그아웃을 해주세요.");
				request.setAttribute("historyBack", true);
				
				RequestDispatcher rd = request.getRequestDispatcher(getJspDirPath() + "/common/redirect.jsp");
				rd.forward(request, response);
			}
		}
		
		// 로그인, 로그아웃 필요 필터링 인터셉터 끝
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
			RequestDispatcher rd = request.getRequestDispatcher(getJspDirPath() + "/" + jspPath +".jsp");
			rd.forward(request, response);
	}
	
	private String getJspDirPath() {
		return "/WEB-INF/jsp";
	}
}

