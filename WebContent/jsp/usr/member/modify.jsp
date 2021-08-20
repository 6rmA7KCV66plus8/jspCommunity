<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="정보수정" />
<%@ include file="../../part/head.jspf" %>
<!-- 자바스크립트 임호화 라이브러리 cdnjs -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
	<h1>${pageTitle}</h1>

	<div>
	<script>
	let DoModifyForm__submited = false; // 중복  작성 방지
	let DoModifyForm__checkedLoginId = "";

	// 폼 발송전 체크
	function DoModifyForm__submit(form){
		if(DoModifyForm__submited){ // true
			alert('처리중 입니다');	
			return;
		}

		form.loginPw.value = form.loginPw.value.trim(); // 공백 제거

		if(form.loginPw.value.length > 0) { // 입력한 비밀번호가 0보다 클 때만 비밀번호 확인 즉, 비밀번호를 수정 할 때만
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim(); // 공백 제거
			if(form.loginPwConfirm.value.length == 0){
				alert('비밀번호 확인을 입력해주세요.');
					form.loginPwConfirm.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
				return;
			}
			if(form.loginPw.value != form.loginPwConfirm.value){ // 비밀번호와 비밀번호 확인이 같은지 체크함
				alert('비밀번호가 일치하지 않습니다.');
					form.loginPwConfirm.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
				return;
			}
		}
		form.name.value = form.name.value.trim(); // 공백 제거
		if(form.name.value.length == 0){
			alert('이름을 입력해주세요.');
				form.name.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		form.nickname.value = form.nickname.value.trim(); // 공백 제거
		if(form.nickname.value.length == 0){
			alert('닉네임을 입력해주세요.');
				form.nickname.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		form.email.value = form.email.value.trim(); // 공백 제거
		if(form.email.value.length == 0){
			alert('이메일을 입력해주세요.');
				form.email.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		form.cellphoneNo.value = form.cellphoneNo.value.trim(); // 공백 제거
		if(form.cellphoneNo.value.length == 0){
			alert('연락처를 입력해주세요.');
				form.cellphoneNo.focus(); // 알림창을 닫으면 바로 아이디 부분에 글을 쓸 수 있음
			return;
			}
		if(form.loginPw.value.length > 0) { // 입력한 비밀번호가 0보다 클 때만 비밀번호 확인 즉, 비밀번호를 수정 할 때만
			form.loginPwReal.value = sha256(form.loginPw.value); // 사용자가 입력한 비밀번호를 암호화
			form.loginPw.value = ""; // 사용자가 입력한 비밀번호
			form.loginPwConfirm.value = ""; // 이걸 비워줘야 비밀번호가 안날라감
		}
		
		form.submit();
		DoModifyForm__submited = true;
		}
	</script> <!-- 여기서 this는 form 엘리먼트를 뜻함 -->
		<form action="doModify" method="post" onsubmit="DoModifyForm__submit(this); return false;">
			<input type="hidden" name="loginPwReal" />
			<div>
				<div>아이디</div>
				<div>
					${loginedMember.loginId}
				</div>
			</div>
			<hr />
			<div>
				<div>비밀번호</div>
				<div><input type="password" name="loginPw" maxlength="50" placeholder="비밀번호" />
				</div>
			</div>
			<hr />
			<div>
				<div>비밀번호 확인</div>
				<div><input type="password" name="loginPwConfirm" maxlength="50" placeholder="비밀번호 확인" />
				</div>
			</div>
			<hr />
			<div>
				<div>이름</div>
				<div><input type="text" name="name" maxlength="50" placeholder="이름." value="${loginedMember.name}" />
				</div>
			</div>
			<hr />
			<div>
				<div>닉네임</div>
				<div><input type="text" name="nickname" maxlength="50" value="${loginedMember.nickname}"/>
				</div>
			</div>
			<hr />
			<div>
				<div>이메일</div>
				<div><input type="email" name="email" maxlength="100" value="${loginedMember.email}"/>
				</div>
			</div>
			<hr />
			<div>
				<div>연락처</div>
				<div><input type="tel" name="cellphoneNo" maxlength="11" value="${loginedMember.cellphoneNo}">
				</div>
			</div>
			<hr />
			<div>
				<div>정보수정</div>
				<div>
					<input type="submit" value="완료" />
					<button type="button" onclick="history.back();">취소</button>
				</div>
			</div>
		</form>
	</div>


<%@ include file="../../part/foot.jspf" %>