package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;
import com.sbs.example.jspCommunity.dto.Member;

public class ArticleService {

	private ArticleDao articleDao;
	private LikeService likeService;
	
	public ArticleService()	{
		likeService = Container.likeService;
		articleDao = Container.articleDao;
	}
	public Article getForPrintArticleById(int id) {
		return getForPrintArticleById(id, null);
	}
	public Article getForPrintArticleById(int id, Member actor) {
		// TODO Auto-generated method stub
		Article article = articleDao.getForPrintArticleById(id);
		
		if(article == null) {
			return null;
		}
		if(actor != null) {
			updateInfoForPrint(article, actor); // 출력을 위해서 article에 정보를 추가
		}
		return article;
	}

	private void updateInfoForPrint(Article article, Member actor) {
		boolean actorCanLike = likeService.actorCanLike(article, actor);
		boolean actorCanCancelLike = likeService.actorCanCancelLike(article, actor);
		boolean actorCanDislike = likeService.actorCanDislike(article, actor);
		boolean actorCanCancelDislike = likeService.actorCanCancelDislike(article, actor);

		
		article.getExtra().put("actorCanLike", actorCanLike);
		article.getExtra().put("actorCanCancelLike", actorCanCancelLike);
		article.getExtra().put("actorCanDislike", actorCanDislike);
		article.getExtra().put("actorCanCancelDislike", actorCanCancelDislike);
	}

	private boolean actorCanLike(Article article, Member actor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean actorCanDisLike(Article article, Member actor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Board getBoardById(int id) {
		// TODO Auto-generated method stub
		return articleDao.getBoardById(id);
	}

	public int write(Map<String, Object> args) {
		// TODO Auto-generated method stub
		return articleDao.write(args);
	}

	public int delete(int id) {
		// TODO Auto-generated method stub
		return articleDao.delete(id);
	}

	public int modify(Map<String, Object> args) {
		// TODO Auto-generated method stub
		return articleDao.modify(args);
	}


	public int getArticlesCountByBoardId(int boardId, String searchKeyword, String searchKeywordType) {
		return articleDao.getArticlesCountByBoardId(boardId, searchKeyword, searchKeywordType);
	}

	public List<Article> getForPrintArticlesByBoardId(int boardId, int limitStart, int limitCount, String searchKeyword, String searchKeywordType) {
		return articleDao.getForPrintArticlesByBoardId(boardId, limitStart, limitCount, searchKeyword, searchKeywordType);
	}


}
