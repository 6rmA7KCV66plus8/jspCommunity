package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.AdmMemberController;
import com.sbs.example.mysqlutil.MysqlUtil;

// DispatcherServlet을 상속 받아야함 : extends DispatcherServlet
// /adm/member/list에 요청이 들어옴 : @Override부분이 실행이 되고 DispatcherServlet(부모)에 doGet(자식)이 실행이 되면서 public void run부분이 실행이 됨
@WebServlet("/adm/*") // /adm로 시작하는 모든 것을 가져옴
public class AdmDispatcherServlet extends DispatcherServlet {


	@Override
	protected String doAction(HttpServletRequest request, HttpServletResponse response, String controllerName, String actionMethodName) {
//		request.setCharacterEncoding("UTF-8"); // 중복  제거로 DisatcherServlet 으로 이동
//		response.setContentType("text/html; charset=UTF-8");
//		
//		String requestUri = request.getRequestURI(); // URI : /usr/a/b/c 같은걸 말함
//		String[] requestUriBits = requestUri.split("/"); // URI를 /로 나눔 
//		
//		if(requestUriBits.length < 5) {
//			response.getWriter().append("올바른 요청이 아닙니다.");
//			return;
//		}
//		
//		String controllerName = requestUriBits[3]; // member, article 같은 부분
//		String actionMethodName = requestUriBits[4]; // list.jsp 파일 같은 부분
//		
//		//db 연결. db는 사용후 끊어줘야함 <43번줄>
//		MysqlUtil.setDBInfo("127.0.0.1", "kjm", "1234", "jspCommunity");
//		
		String jspPath = null;
		
		if( controllerName.equals("member")) {
			AdmMemberController memberController = Container.admMemberController;
		
			if(actionMethodName.equals("list")) {
				jspPath = memberController.showList(request, response); // memberController의 showList를 호출
			}
		}
		return jspPath;
											
//		MysqlUtil.closeConnection();
//		
//													//UriBits = /1/2/3/4
//		RequestDispatcher rd = request.getRequestDispatcher("/jsp/" + jspPath +".jsp");
//		rd.forward(request, response);
		
	}
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
}
