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

		String jspPath = null;
		
		if( controllerName.equals("member")) {
			AdmMemberController memberController = Container.admMemberController;
		
			if(actionMethodName.equals("list")) {
				jspPath = memberController.showList(request, response); // memberController의 showList를 호출
			}
		}
		return jspPath;
	}

}
