package com.sbs.example.jspCommunity.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrHomeController;
import com.sbs.example.jspCommunity.controller.UsrLikeController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.jspCommunity.controller.UsrReplyController;

// DispatcherServlet을 상속 받아야함 : extends DispatcherServlet
// ex) /usr/member/list : controllerName에 member가 들어가고 actionMethodName에 list가 들어간다 / adm도 동일함
@WebServlet("/usr/*") // /usr로 시작하는 모든 것을 가져옴
public class UsrDispatcherServlet extends DispatcherServlet {


	@Override
	protected String doAction(HttpServletRequest request, HttpServletResponse response, String controllerName, String actionMethodName) {
		String jspPath = null;

		if( controllerName.equals("home")) {
			UsrHomeController homeController = Container.usrHomeController;
			
			if(actionMethodName.equals("main")) {
				jspPath = homeController.showMain(request, response); // memberController의 showList를 호출
			}
		}
		else if( controllerName.equals("member")) {
			UsrMemberController memberController = Container.usrMemberController;
		
			if(actionMethodName.equals("list")) {
				jspPath = memberController.showList(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("findLoginId")) {
				jspPath = memberController.showFindLoginId(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doFindLoginId")) {
				jspPath = memberController.doFindLoginId(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("findLoginPw")) {
				jspPath = memberController.showFindLoginPw(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doFindLoginPw")) {
				jspPath = memberController.doFindLoginPw(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("modify")) {
				jspPath = memberController.showModify(request, response); // memberController의 showList를 호출
			}
			else if(actionMethodName.equals("doModify")) {
				jspPath = memberController.doModify(request, response); // memberController의 showList를 호출
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
			else if(actionMethodName.equals("getLoginIdDup")) {
				jspPath = memberController.getLoginIdDup(request, response); // memberController의 showList를 호출
			}
			
		} else if (controllerName.equals("article")) {
			UsrArticleController articleController = Container.usrArticleController;
			
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
			else if(actionMethodName.equals("doDelete")) {
				jspPath = articleController.doDelete(request, response);
			}
		} else if (controllerName.equals("like")) {
			UsrLikeController likeController = Container.usrLikeController;
		
			if(actionMethodName.equals("doLike")) { // 좋아요 싫어요
				jspPath = likeController.doLike(request, response);
			}
			else if (actionMethodName.equals("doCancelLike")) {
				jspPath = likeController.doCancelLike(request, response);
			} 
			else if (actionMethodName.equals("doDislike")) {
				jspPath = likeController.doDislike(request, response);
			} 
			else if (actionMethodName.equals("doCancelDislike")) {
				jspPath = likeController.doCancelDislike(request, response);
			}
		} else if (controllerName.equals("reply")) { // 댓글 
			UsrReplyController replyController = Container.usrReplyController;
			
			if(actionMethodName.equals("doWrite")) {
				jspPath = replyController.doWrite(request, response);
			}
			else if (actionMethodName.equals("doDelete")) {
				jspPath = replyController.doDelete(request, response);
			} 
			else if (actionMethodName.equals("doModify")) {
				jspPath = replyController.doModify(request, response);
			}
		}
		return jspPath;

	}
}