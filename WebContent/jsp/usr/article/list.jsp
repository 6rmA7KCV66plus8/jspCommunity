<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${board.name} 게시물 리스트" />
<%@ include file="../../part/head.jspf"%>

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span><i class="fas fa-list-ol"></i></span> <span>${pageTitle}</span>
	</h1>
</div>

<hr />

<div class="article-search-form-box form-box con-min-width padding-0-10">
	<script>
		let DoSearchForm__submited = false; // 중복  작성 방지
		function DoSearchForm__submit(form) {
			if (DoSearchForm__submited) {
				alert('처리중 입니다.');
				return;
			}

			form.searchKeyword.value = form.searchKeyword.value.trim(); // 공백 처리

			if (form.searchKeyword.value.length == 0) {
				alert('검색어를 입력해주세요.');
				form.searchKeyword.focus();
				return;
			}
			form.submit();
			DoSearchForm__submited = true;
		}
	</script>
	<form class="con" onsubmit="DoSearchForm__submit(this); return false;">
		<input type="hidden" name="boardId" value="${param.boardId}" />

		<table>
			<colgroup>
				<col width="150">
			</colgroup>
			<tbody>
				<tr>
					<th><span>검색조건</span></th>
					<td>
						<div>
							<select name="searchKeywordType">
								<option value="titleAndbody">제목+내용</option>
								<option value="title">제목</option>
								<option value="body">내용</option>
							</select>
						</div>
						<script>
							const param__searchKeywordType = '${param.searchKeywordType}';
							if (param__searchKeywordType) {
								$('select[name="searchKeywordType"]').val('${param.searchKeywordType}');
							}
						</script>
					</td>
				</tr>
				<tr>
					<th><span>검색어</span></th>
					<td>
						<div>
							<input value="${param.searchKeyword}" type="text" name="searchKeyword" placeholder="검색어를 입력해주세요." />
						</div>
					</td>
				</tr>
				<tr>
					<th><span></span></th>
					<td>
						<div>
							<input class="btn btn-primary" type="submit" value="검색" />
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div class="article-list-total-count-box con-min-width padding-0-10">
<div class="con">
	<div>
		<span><i class="fas fa-eye"></i></span>
		<span>총 게시물 수 : </span>
		<span class="color-red">${totalCount}</span>	
	</div>
</div>
</div>

<div class="article-list-box response-list-box con-min-width padding-0-10">
	<div class="con">
		<table>
			<colgroup>
				<col width="100">
				<col width="200">
				<col width="150">
				<col width="150">
			</colgroup>
			<thead class="visible-md-up">
				<tr>
					<th>번호</th>
					<th>날짜</th>
					<th>작성자</th>
					<th>좋아요</th>
					<th>제목</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${articles}" var="article">
					<tr>
						<td><span class="response-list-box__id">${article.id}</span></td>
						<td><span class="response-list-box__reg-date">${article.regDate}</span></td>
					<!--<td><span class="response-list-box__update-date">${article.updateDate}</span></td> -->
						<td><span class="response-list-box__writer">${article.extra__writer}</span></td>
						<td>
							<span class="response-list-box__likeOnlyPoint">
								<span><i class="far fa-thumbs-up"></i></span>
								<span>${article.extra__likeOnlyPoint}</span>
							</span>						
							<span class="response-list-box__dislikeOnlyPoint">
								<span><i class="far fa-thumbs-down"></i></span>
								<span>${article.extra__dislikeOnlyPoint}</span>
							</span>
						</td>
						<td><a href="../article/detail?id=${article.id}&listUrl=${encodedCurrentUrl}"
							class="response-list-box__title response-list-box__title--pc hover-link">${article.title}</a></td>

						<!-- 모바일 -->
						<td class="visible-sm-down">
							<div class="flex">
								<span class="response-list-box__id response-list-box__id--mobile">${article.id}</span>
								<a href="../article/detail?id=${article.id}&listUrl=${encodedCurrentUrl}"
									class="response-list-box__title response-list-box__title--mobile flex-grow-1 hover-link">${article.title}</a>
							</div>
							<div class="flex">
									<span class="response-list-box__likeOnlyPoint">
										<span><i class="far fa-thumbs-up"></i></span>
										<span>${article.extra__likeOnlyPoint}</span>
									</span>						
									<span class="response-list-box__dislikeOnlyPoint">
										<span><i class="far fa-thumbs-down"></i></span>
										<span>${article.extra__dislikeOnlyPoint}</span>
									</span>
							</div>
							<div class="flex">
								<span
									class="response-list-box__writer response-list-box__writer--mobile">${article.extra__writer}</span>
								<span>&nbsp;|&nbsp;</span> <span
									class="response-list-box__reg-date response-list-box__reg-date--mobile">${article.regDate}</span>
							</div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div class="article-btn-box padding-0-10 con-min-width">
	<div class="con btn-wrap">
		<a class="btn btn-primary" href="write?boardId=${param.boardId}">글쓰기</a>
	</div>
</div>

<div class="article-list-page-box con-min-width padding-0-10">
	<div class="con flex flex-jc-c">

		<!--
		<c:set var="aUrl" value="?page=1&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
		<a href="${aUrl}">◀◀</a> // 1페이지로
	 -->

		<c:if test="${pageBoxStartBeforeBtnNeedToShow}">
			<c:set var="aUrl"
				value="?page=${pageBoxStartBeforePage}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
			<a href="${aUrl}">◀</a>
			<!-- 10 단위로 이전 페이지 -->
		</c:if>

		<c:forEach var="i" begin="${pageBoxStartPage}" end="${pageBoxEndPage}"
			step="1">
			<!-- 번수: i, 1부터 페이지 끝까지 1페이지씩 증가 -->
			<c:set var="aClass" value="${page == i ? 'color-red' : ''}" />
			<c:set var="aUrl"
				value="?page=${i}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
			<a class="${aClass} article-list-page-box__page-btn--no"
				href="${aUrl}">${i}</a>
		</c:forEach>

		<c:if test="${pageBoxEndAfterBtnNeedToShow}">
			<c:set var="aUrl"
				value="?page=${pageBoxEndAfterPage}&boardId=${param.boardId}&searchKeywordType=${param.searchKeywordType}&searchKeyword=${param.searchKeyword}" />
			<a href="${aUrl}">▶</a>
			<!-- 10 단위로 다음 페이지 -->
		</c:if>

		<!-- <c:set var="aUrl" value="?page=${totalPage}&boardId=${param.boardId}&serchKeywordType=${param.searchKeywordType}&serchKeyword=${param.searchKeyword}" />
		<a href="${aUrl}">▶▶</a> // 마지막 페이지로
	-->
	</div>
</div>

<%@ include file="../../part/foot.jspf"%>