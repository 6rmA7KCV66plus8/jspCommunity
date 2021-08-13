<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../../part/head.jspf" %>
	<h1>${pageTitle}</h1>

	<div>
	<script>
	let DoJoinForm__submited = false; // 중복  작성 방지
	function DoJoinForm__submit(form){
		if(DoJoinForm__submited){ // true
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
		form.submit();
		DoJoinForm__submited = true;
		}
	</script> <!-- 여기서 this는 form 엘리먼트를 뜻함 -->
		<form action="doJoin" method="post" onsubmit="DoJoinForm__submit(this); return false;">
			<div>
				<div>아이디</div>
				<div><input type="text" name="loginId" maxlength="50" placeholder="사용할 아이디를 입력해주세요."/></div>
			</div>
			<hr />
			<div>
				<div>비밀번호</div>
				<div><input type="password" name="loginPw" maxlength="50" placeholder="비밀번호"></textarea></div>
			</div>
			<hr />
			<div>
				<div>비밀번호 확인</div>
				<div><input type="password" name="loginPwConfirm" maxlength="50" placeholder="비밀번호 확인"></textarea></div>
			</div>
			<hr />
			<div>
				<div>이름</div>
				<div><input type="text" name="name" maxlength="50" placeholder="이름."/></div>
			</div>
			<hr />
			<div>
				<div>닉네임</div>
				<div><input type="text" name="nickname" maxlength="50" placeholder="닉네임."/></div>
			</div>
			<hr />
			<div>
				<div>이메일</div>
				<div><input type="email" name="email" maxlength="100" placeholder="이메일."/></div>
			</div>
			<hr />
			<div>
				<div>연락처</div>
				<div><input type="number" name="cellphoneNo" maxlength="10" placeholder="연락처."/></div>
			</div>
			<hr />
			<div>
				<div>가입</div>
				<div>
					<input type="submit" value="완료" />
					<button type="button" onclick="history.back();">취소</button>
				</div>
			</div>
		</form>
	</div>


<%@ include file="../../part/foot.jspf" %>