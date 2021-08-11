<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import ="com.sbs.example.jspCommunity.dto.Board" %>
<%@ page import ="com.sbs.example.jspCommunity.dto.Article" %>
<%
	Board board = (Board) request.getAttribute("board");
	//Article article = (Article) request.getAttribute("article"); // el : ${}로 바꾸기 위해 주석처리, dto에 getter/setter을 만들어야 사용 가능
	
	String pageTitle = board.getName() + " 게시물 수정페이지";
%>
<%@ include file="../../part/head.jspf" %>
	<h1><%=pageTitle%></h1>

	<div>
		<form action="doModify" method="post">
			<input type="hidden" name="id" value="${article.id}"/>
			<input type="hidden" name="memberId" value="1"/>

				<div>제목</div>
				<div><input type="text" name="title" maxlength="50" placeholder="제목" value="${article.title}"/></div>
			</div>
			<hr />
			<div>
				<div>내용</div>
				<div><textarea name="body" maxlength="5000" placeholder="내용">${article.title}</textarea></div>
			</div>
			<hr />
			<div>
				<div>
					<input type="submit" value="수정 완료" />
					<button type="button" onclick="history.back();">취소</button>
				</div>
			</div>
		</form>
	</div>

<%@ include file="../../part/foot.jspf" %>