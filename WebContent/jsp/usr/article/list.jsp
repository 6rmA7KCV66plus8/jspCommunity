<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../../part/head.jspf" %>
	<h1>${pageTitle}</h1>
	
	<hr />
	
	<div>
		<a href="write?boardId=${param.boardId}">게시물 작성</a>
	</div>
	
	<hr />
	
	<div>
		<script>
		let DoSearchForm__submited = false; // 중복  작성 방지
		function DoSearchForm__submit(form){
			if(DoSearchForm__submited){
				alert('처리중 입니다.');
				return;
			}
			
			form.searchKeyword.value = form.searchKeyword.value.trim(); // 공백 처리

			if(form.searchKeyword.value.length == 0 ){
				alert('검색어를 입력해주세요.');
				form.searchKeyword.focus();
				return;
				}
			form.submit();
			DoSearchForm__submited = true;
		}
		</script>
		<form action="" onsubmit="DoSearchForm__submit(this); return false;">
			<input type="hidden" name="boardId" value="${param.boardId}" />
			
			<select name="searchKeywordType">
				<option value="titleAndbody">제목+내용</option>
				<option value="title">제목</option>
				<option value="body">내용</option>
			</select>
			<script>
			const param__searchKeywordType = '${param.searchKeywordType}';
			if(param__searchKeywordType){
				$('select[name="searchKeywordType"]').val('${param.searchKeywordType}');	
			}
			</script>
			<input value="${param.searchKeyword}" type="text" name="searchKeyword" placeholder="검색어를 입력해주세요." />
			<input type="submit" value="검색" />
		</form>
	</div>
	
	<hr />
	
	<div>
		총 게시물 수 : ${totalCount}
	</div>
	
	<hr />
	
	<c:forEach items="${articles}" var="article">
	<div>
		번호 : 
		${article.id}
		<br />
		작성날짜 : 
		${article.regDate}
		<br />
	 	갱신날짜 : 
		${article.updateDate}
		<br />
		작성자 : 
		${article.extra__writer}
		<br />
		제목 : 
		<a href="detail?id=${article.id}">${article.title}</a>
		<hr />
	</div>
	</c:forEach>
	
<style>
.red{
	color : red;
}
</style>

	<div class="con">
	<!--
		<c:set var="aUrl" value="?page=1&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
		<a href="${aUrl}">◀◀</a> // 1페이지로
	 -->
		
		<c:if test="${pageBoxStartBeforeBtnNeedToShow}">
			<c:set var="aUrl" value="?page=${pageBoxStartBeforePage}&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
			<a href="${aUrl}">◀</a> <!-- 10 단위로 이전 페이지 -->
		</c:if>
		
		<c:forEach var="i" begin="${pageBoxStartPage}" end="${pageBoxEndPage}" step="1"> <!-- 번수: i, 1부터 페이지 끝까지 1페이지씩 증가 -->
			<c:set var="aClass" value="${page == i ? 'red' : ''}" />
			<c:set var="aUrl" value="?page=${i}&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
			<a class="${aClass}" href="${aUrl}">${i}</a>
		</c:forEach>
		
		<c:if test="${pageBoxEndAfterBtnNeedToShow}">
			<c:set var="aUrl" value="?page=${pageBoxEndAfterPage}&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
			<a href="${aUrl}">▶</a> <!-- 10 단위로 다음 페이지 -->
		</c:if>
		
	<!-- <c:set var="aUrl" value="?page=${totalPage}&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
		<a href="${aUrl}">▶▶</a> // 마지막 페이지로
	-->
	</div>
<%@ include file="../../part/foot.jspf" %>