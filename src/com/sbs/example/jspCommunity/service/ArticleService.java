package com.sbs.example.jspCommunity.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.jspCommunity.container.Container;
import com.sbs.example.jspCommunity.dao.ArticleDao;
import com.sbs.example.jspCommunity.dto.Article;
import com.sbs.example.jspCommunity.dto.Board;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService()	{
		articleDao = Container.articleDao;
	}

	public List<Article> getForPrintArticlesByBoardId(int boardId) { // 출력을 위한 부분
		return articleDao.getForPrintArticlesByBoardId(boardId);
		
	}

	public Article getForPrintArticleById(int id) {
		// TODO Auto-generated method stub
		return articleDao.getForPrintArticleById(id);
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

	public int getArticlesCountByBoardId(int boardId) {
		return articleDao.getArticlesCountByBoardId(boardId);
	}


}
