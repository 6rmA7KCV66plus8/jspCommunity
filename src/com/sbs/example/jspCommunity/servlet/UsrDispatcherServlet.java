package com.sbs.example.jspCommunity.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrHomeController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;

// DispatcherServlet을 상속 받아야함 : extends DispatcherServlet
// ex) /usr/member/list : controllerName에 member가 들어가고 actionMethodName에 list가 들어간다 / adm도 동일함
@WebServlet("/usr/*") // /usr로 시작하는 모든 것을 가져옴
public class UsrDispatcherServlet extends DispatcherServlet {


	@Override
	protected String doAction(HttpServletRequest request, HttpServletResponse response, String controllerName, String actionMethodName) {
		String jspPath = null;

//		request.setCharacterEncoding("UTF-8");
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
//		MysqlUtil.setDBInfo("127.0.0.1", "kjm", "1234", "jspCommunity");
//		
//		String jspPath = null;
		if( controllerName.equals("home")) {
			UsrHomeController homeController = Container.homeController;
			
			if(actionMethodName.equals("main")) {
				jspPath = homeController.showMain(request, response); // memberController의 showList를 호출
			}
		}
		else if( controllerName.equals("member")) {
			UsrMemberController memberController = Container.memberController;
		
			if(actionMethodName.equals("list")) {
				jspPath = memberController.showList(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("join")) {
				jspPath = memberController.showJoin(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doJoin")) {
				jspPath = memberController.doJoin(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("login")) {
				jspPath = memberController.showLogin(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doLogin")) {
				jspPath = memberController.doLogin(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doLogout")) {
				jspPath = memberController.doLogout(request, response); // memberController의 showList를 호출
			}
		} else if (controllerName.equals("article")) {
			UsrArticleController articleController = Container.articleController;
			
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
		
		return jspPath;
//											
//		MysqlUtil.closeConnection();
//		
//													//UriBits = /1/2/3/4
//		RequestDispatcher rd = request.getRequestDispatcher("/jsp/" + jspPath +".jsp");
//		rd.forward(request, response);
//		
//	}
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//}
	}
}