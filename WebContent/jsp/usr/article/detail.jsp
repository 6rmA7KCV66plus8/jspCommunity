<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="${article.extra__boardName} 게시물 상세페이지" />
<%@ include file="../../part/head.jspf"%>

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span><i class="fas fa-list-ol"></i></span> <span>${pageTitle}</span>
	</h1>
</div>

<div class="article-detail-box detail-box padding-0-10 con-min-width">
	<div class="con">
		<table>
			<colgroup>
				<col width="150">
			</colgroup>
			<tbody>
				<tr>
					<th><span>번호</span></th>
					<td>
						<div>${article.id}</div> <!-- 이걸 쓰려면 getter setter을 만들어 줘야함 -->
					</td>
				</tr>
				<tr>
					<th><span>작성날짜</span></th>
					<td>
						<div>${article.regDate}</div>
					</td>
				</tr>
				<tr>
					<th><span>갱신날짜</span></th>
					<td>
						<div>${article.updateDate}</div>
					</td>
				</tr>
				<tr>
					<th><span>작성자</span></th>
					<td>
						<div>${article.extra__writer}</div>
					</td>
				</tr>
				<tr>
					<th><span>좋아요</span></th>
					<td>
						<div>${article.extra__likeOnlyPoint}</div>
					</td>
				</tr>
				<tr>
					<th><span>싫어요</span></th>
					<td>
						<div>${article.extra__dislikeOnlyPoint}</div>
					</td>
				</tr>
				<tr>
					<th><span>제목</span></th>
					<td>
						<div>${article.title}</div>
					</td>
				</tr>
				<tr>
					<td colspan="2"><script type="text/x-template">${article.body}</script>
						<div class="toast-ui-viewer"></div></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<div class="article-btn-box padding-0-10 con-min-width">
	<div class="con btn-wrap">
		<c:if test="${article.extra.actorCanLike}">
			<a class="btn btn-like"
				href="../like/doLike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
				onclick="if ( !confirm('좋아요를 누르시겠습니까?') ) return false;">
				<span>
					<i class="far fa-thumbs-up"></i>
				</span>
				<span>좋아요</span>
			</a>
		</c:if>
		<c:if test="${article.extra.actorCanCancelLike}">
			<a class="btn btn-like"
				href="../like/doCancelLike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
				onclick="if ( !confirm('좋아요를 취소 하시겠습니까?') ) return false;">
				<span>
					<i class="far fa-thumbs-up"></i>
				</span>
				<span>좋아요 취소</span>
			</a>
		</c:if>
		<c:if test="${article.extra.actorCanDislike}">
			<a class="btn btn-dislike"
				href="../like/doDislike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
				onclick="if ( !confirm('싫어요를 누르시겠습니까?') ) return false;">
				<span>
					<i class="far fa-thumbs-down"></i>
				</span>
				<span>싫어요</span>
			</a>
		</c:if>
		<c:if test="${article.extra.actorCanCancelDislike}">
			<a class="btn btn-dislike"
				href="../like/doCancelDislike?relTypeCode=article&relId=${article.id}&redirectUrl=${encodedCurrentUrl}"
				onclick="if( !confirm('싫어요를 취소 하시겠습니까?') ) return false;">
				<span>
					<i class="far fa-thumbs-down"></i>
				</span>
				<span>싫어요 취소</span>
			</a>
		</c:if>

		<a class="btn btn-info" href="${param.listUrl}">목록</a> <a
			class="btn btn-info" href="modify?id=${article.id}">수정</a> <a
			class="btn btn-danger"
			onclick="if (confirm('정말로 삭제하시겠습니까?') == false) {return false;}"
			href="doDelete?id=${article.id}">삭제</a>
		<!-- 취소(false) 버튼을 눌렀을 때 return false;가 실행 -->
	</div>
</div>
<!-- 댓글 작성 -->

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span><i class="fab fa-replyd"></i></span> <span>댓글</span>
	</h1>
</div>

<c:if test="${isLogined == false}">
	<div class="article-reply-write-form-box form-box con-min-width padding-0-10">
		<div class="con">
			댓글 작성은 먼저 
			<a class="udl hover-link" href="../member/login?afterLoginUrl=${encodedCurrentUrl}">로그인<i class="fas fa-hand-pointer"></i></a>
			 후 이용해주세요.
		</div>
	</div>
</c:if>

<c:if test="${isLogined}">
<div class="article-reply-write-form-box form-box con-min-width padding-0-10">
	<script>
	let Reply__DoWriteForm__submited = false; // 중복  작성 방지
	let Reply__DoWriteForm__checkedLoginId = "";

	// 폼 발송전 체크
	function Reply__DoWriteForm__submit(form){
		if(Reply__DoWriteForm__submited){ // true
			alert('처리중 입니다');	
			return;
		}

			const editor = $(form).find('.toast-ui-editor').data('data-toast-editor');
			const body = editor.getMarkdown().trim();

			if( body.length == 0) {
				alert('내용을 입력해주세요.');
				editor.focus();

				return;
			}
			
			form.body.value = body;
		
		form.submit();
		Reply__DoWriteForm__submited = true;
		}
	</script>
		<form class="con" action="../reply/doWrite" method="post" onsubmit="Reply__DoWriteForm__submit(this); return false;">
			<input type="hidden" name="redirectUrl" value="${currentUrl}"/>
			<input type="hidden" name="relTypeCode" value="article"/>
			<input type="hidden" name="relId" value="${article.id}"/>
			<input type="hidden" name="body" />
			
			<table>
			<colgroup>
				<col width="150">
			</colgroup>
			<tbody>
				<tr>
					<th><span> </span></th>
					<td>
						<div>
							<div>
								<script type="text/x-template"></script>
  								<div class="toast-ui-editor" data-height="200"></div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th><span> </span></th>
					<td>
						<div>
							<div class="btn-wrap">
								<input class="btn btn-primary" type="submit" value="완료" />
								<button class="btn btn-info" type="button" onclick="history.back();">취소</button>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
			</table>
		</form>
</div>
</c:if>
<%@ include file="../../part/foot.jspf"%>