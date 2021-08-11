<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="${board.name} 게시물 수정페이지" />
				<!-- req.getAttribute한다음 board로 캐스팅하고 get.Name()한것과 같음, 이렇게 작성하면 board를 import할 필요 없다  -->
<%@ include file="../../part/head.jspf" %>
	<h1>${pageTitle}</h1>

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