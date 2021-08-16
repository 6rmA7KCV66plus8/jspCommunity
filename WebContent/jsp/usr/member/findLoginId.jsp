<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="아이디 찾기" />
<%@ include file="../../part/head.jspf" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
	<h1>${pageTitle}</h1>

	<div>
	<script>
	let DoFindLoginIdForm__submited = false; // 중복  작성 방지
	function DoFindLoginIdForm__submit(form){
		if(DoFindLoginIdForm__submited){ // true
			alert('처리중 입니다');	
			return;
		}
		
		form.loginId.value = form.loginId.value.trim(); // 공백 제거
		if(form.loginId.value.length == 0){
			alert('아이디를 입력해주세요.');
				form.loginId.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		form.loginPw.value = form.loginPw.value.trim(); // 공백 제거
		if(form.loginPw.value.length == 0){
			alert('비밀번호를 입력해주세요.');
				form.loginPw.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		form.loginPwReal.value = sha256(form.loginPw.value); // 그 비밀번호를 Real한테 보낸다
		form.loginPw.value = ""; // 비밀번호를 발송 안한다
		
		form.submit();
		DoFindLoginIdForm__submited = true;
		}
	</script> <!-- 여기서 this는 form 엘리먼트를 뜻함 -->
		<form action="doFindLoginId" method="post" onsubmit="DoFindLoginIdForm__submit(this); return false;">
			<input type="hidden" name="loginPwReal" />
			<div>
				<div>이름</div>
				<div><input type="text" name="name" maxlength="50" placeholder="이름을 입력해주세요."/></div>
			</div>
			<hr />
			<div>
				<div>이메일</div>
				<div><input type="email" name="email" maxlength="50" placeholder="이메일을 입력해주세요."></textarea></div>
			</div>
			<hr />
			<div>
				<div>아이디 찾기</div>
				<div>
					<input type="submit" value="아이디 찾기" />
					<button type="button" onclick="history.back();">취소</button>
				</div>
			</div>
		</form>
	</div>


<%@ include file="../../part/foot.jspf" %>