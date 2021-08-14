<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${board.name} 게시물 작성" />
<%@ include file="../../part/head.jspf" %>
	<h1>${pageTitle}</h1>

	<div>
		<form action="doWrite" method="post">
			<input type="hidden" name="boardId" value="${board.id }"/>

				<div>제목</div>
				<div><input type="text" name="title" maxlength="50" placeholder="제목"/></div>
			</div>
			<hr />
			<div>
				<div>내용</div>
				<div><textarea name="body" maxlength="5000" placeholder="내용"></textarea></div>
			</div>
			<hr />
			<div>
				<div>
					<input type="submit" value="완료" />
					<button type="button" onclick="history.back();">취소</button>
				</div>
			</div>
		</form>
	</div>


<%@ include file="../../part/foot.jspf" %>