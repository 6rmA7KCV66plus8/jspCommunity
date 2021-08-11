package com.sbs.example.jspCommunity.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.usr.ArticleController;
import com.sbs.example.jspCommunity.controller.usr.MemberController;
import com.sbs.example.mysqlutil.MysqlUtil;

// HttpServlet을 상속 받아야함 : extends HttpServlet

@WebServlet("/usr/*") // /usr로 시작하는 모든 것을 가져옴
public class DispatcherServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String requestUri = request.getRequestURI(); // URI : /usr/a/b/c 같은걸 말함
		String[] requestUriBits = requestUri.split("/"); // URI를 /로 나눔 
		
		if(requestUriBits.length < 5) {
			response.getWriter().append("올바른 요청이 아닙니다.");
			return;
		}
		
		String controllerName = requestUriBits[3]; // member, article 같은 부분
		String actionMethodName = requestUriBits[4]; // list.jsp 파일 같은 부분
		
		//db 연결. db는 사용후 끊어줘야함 <43번줄>
		MysqlUtil.setDBInfo("127.0.0.1", "kjm", "1234", "jspCommunity");
		
		String jspPath = null;
		
		if( controllerName.equals("member")) {
			MemberController memberController = Container.memberController;
		
			if(actionMethodName.equals("list")) {
				jspPath = memberController.showList(request, response); // memberController의 showList를 호출
			}
		} else if (controllerName.equals("article")) {
			ArticleController articleController = Container.articleController;
			
			if(actionMethodName.equals("list")) {
				jspPath = articleController.showList(request, response); // memberController의 showList를 호출
			}
			else if (actionMethodName.equals("detail")) {
				jspPath = articleController.showDetail(request, response);
			} 
			else if (actionMethodName.equals("modify")) {
				jspPath = articleController.showModify(request, response);
			} 
			else if (actionMethodName.equals("doModify")) {
				jspPath = articleController.doModify(request, response);
			} 
			else if(actionMethodName.equals("write")) {
				jspPath = articleController.showWrite(request, response);
			}
			else if(actionMethodName.equals("doWrite")) {
				jspPath = articleController.doWrite(request, response);
			}
			else if(actionMethodName.equals("doDelete")) {
				jspPath = articleController.doDelete(request, response);
			}
		}
											
		MysqlUtil.closeConnection();
		
													//UriBits = /1/2/3/4
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/" + jspPath +".jsp");
		rd.forward(request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
