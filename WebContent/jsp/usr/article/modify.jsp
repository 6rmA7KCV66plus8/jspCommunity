<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="${board.name} 게시물 수정페이지" />
<!-- req.getAttribute한다음 board로 캐스팅하고 get.Name()한것과 같음, 이렇게 작성하면 board를 import할 필요 없다  -->
<%@ include file="../../part/head.jspf"%>

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span><i class="fas fa-list-ol"></i></span> <span>${pageTitle}</span>
	</h1>
</div>

<div class="article-modify-form-box form-box con-min-width padding-0-10">
	<script>
		let DoModifyForm__submited = false; // 중복  작성 방지
		let DoModifyForm__checkedLoginId = "";

		// 폼 발송전 체크
		function DoModifyForm__submit(form) {
			if (DoModifyForm__submited) { // true
				alert('처리중 입니다');
				return;
			}

			form.title.value = form.title.value.trim(); // 공백 제거
			if (form.title.value.length == 0) {
				alert('제목을 입력해주세요.');
				form.title.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
				return;
			}

			const editor = $(form).find('.toast-ui-editor').data(
					'data-toast-editor');
			const body = editor.getMarkdown().trim();

			if (body.length == 0) {
				alert('내용을 입력해주세요.');
				editor.focus();

				return;
			}

			form.body.value = body;

			form.submit();
			DoModifyForm__submited = true;
		}
	</script>
	<form class="con" action="doModify" method="post"
		onsubmit="DoModifyForm__submit(this); return false;">
		<input type="hidden" name="id" value="${article.id}" /> <input
			type="hidden" name="body" />

		<table>
			<colgroup>
				<col width="150">
			</colgroup>
			<tbody>
				<tr>
					<th><span>제목</span></th>
					<td>
						<div>
							<input type="text" name="title" maxlength="50" placeholder="제목" value="${article.title}"/>
						</div>
					</td>
				<tr>
					<th><span>내용</span></th>
					<td>
						<div>
							<div>
								<script type="text/x-template">${article.body}</script>
								<div class="toast-ui-editor"></div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th><span> </span></th>
					<td>
						<div>
							<div>
								<div class="btn-wrap" >
									<input class="btn btn-primary" type="submit" value="수정 완료" />
									<button class="btn btn-info" type="button" onclick="history.back();">취소</button>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<%@ include file="../../part/foot.jspf"%>