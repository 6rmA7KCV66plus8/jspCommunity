package com.sbs.example.jspCommunity.container;

import com.sbs.example.jspCommunity.controller.AdmMemberController;
import com.sbs.example.jspCommunity.controller.UsrArticleController;
import com.sbs.example.jspCommunity.controller.UsrHomeController;
import com.sbs.example.jspCommunity.controller.UsrLikeController;
import com.sbs.example.jspCommunity.controller.UsrMemberController;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dao.AttrDao;
import com.sbs.example.jspCommunity.dao.LikeDao;
import com.sbs.example.jspCommunity.dao.MemberDao;
import com.sbs.example.jspCommunity.service.ArticleService;
import com.sbs.example.jspCommunity.service.AttrService;
import com.sbs.example.jspCommunity.service.EmailService;
import com.sbs.example.jspCommunity.service.LikeService;
import com.sbs.example.jspCommunity.service.MemberService;

public class Container {
	public static ArticleService articleService;
	public static ArticleDao articleDao;
	public static UsrArticleController articleController; 
	
	public static MemberDao memberDao;
	public static MemberService memberService;
	private static UsrLikeController usrLikeController;
	public static UsrMemberController memberController;
	public static AdmMemberController admMemberController; // #똑같은 이름의 클래스는 import를 할수 없음
	public static UsrHomeController homeController;
	public static LikeService likeService;
	public static LikeDao likeDao;
	
	public static AttrService attrService;
	public static AttrDao attrDao;
	
	public static EmailService emailService;
	

	static { // 얘가 가장 먼저 실행 됨, 그다음 ConfigServlet의 init부분
		
		attrDao = new AttrDao();
		likeDao = new LikeDao();
		memberDao = new MemberDao();
		articleDao = new ArticleDao(); // dao가 더 먼저 만들어져야 함
		
		attrService = new AttrService(); // 동적인 애 일수록 위로 
		likeService = new LikeService();
		emailService = new EmailService();
		memberService = new MemberService();
		articleService = new ArticleService();
		
		usrLikeController = new UsrLikeController();
		admMemberController = new AdmMemberController();
		memberController = new UsrMemberController();
		articleController = new UsrArticleController();
		homeController = new UsrHomeController();
	}
}
