<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../../part/head.jspf"%>
<!-- 자바스크립트 임호화 라이브러리 cdnjs -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<div class="title-bar padding-0-10 con-min-width">
	<h1 class="con">
		<span><i class="fas fa-user-plus"></i></span> <span>${pageTitle}</span>
	</h1>
</div>

<div class="join-form-box form-box padding-0-10 con-min-width">
	<script>
	let DoJoinForm__submited = false; // 중복  작성 방지
	let DoJoinForm__checkedLoginId = "";

	// 로그인 아이디 중복검사
	function DoJoinForm__checkLoginIdDup(el) { // DoJoinForm__checkLoginIdDup = el
		const from = $(el).closest('form').get(0); // 엘리먼트(el) 조상 중에서 가장 가까운 form을 찾아라
		const loginId = from.loginId.value;
		
		$.get(	
			"getLoginIdDup", // 순수한 정보를 얻을 땐 get을 사용, 보는건 show, 처리하는건 do
			{
				//loginId: loginId 이렇게 적어도 되고 // 서로 다름 "loginId"(문자열) : loginId(const loginId)를 뜻함
				loginId // 이렇게 적어도 됌
			},
			function(data){
				if(data.msg){
					alert(data.msg);
				}
				//data.resultCode를 자른다 앞에서부터 2자(S-)
				if(data.success) {
					DoJoinForm__checkedLoginId = data.body.loginId;
				}
			},
			"json"
		);
	}
	// 폼 발송전 체크
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
		if(form.loginId.value != DoJoinForm__checkedLoginId){ // 지금 입력한 아이디(form.loginId.value)가 인증된 로그인 아이디와 다르면
				alert('아이디 중복체크를 해주세요.');
				form.btnLoginIdDupCheck.focus();
				return false;
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
			form.loginPwReal.value = sha256(form.loginPw.value); // 사용자가 입력한 비밀번호를 암호화
			form.loginPw.value = ""; // 사용자가 입력한 비밀번호
			form.loginPwConfirm.value = ""; // 이걸 비워줘야 비밀번호가 안날라감
		
		form.submit();
		DoJoinForm__submited = true;
		}
	</script>
	<!-- 여기서 this는 form 엘리먼트를 뜻함 -->
	<form class="con" action="doJoin" method="post"
		onsubmit="DoJoinForm__submit(this); return false;">
		<input type="hidden" name="loginPwReal" />
		<table>
			<colgroup>
				<col width="150">
			</colgroup>
			<tbody>
				<tr>
					<th><span>아이디</span></th>
					<td>
						<div>
							<input type="text" name="loginId" maxlength="50"
								placeholder="사용할 아이디를 입력해주세요." />
							<button class="btn btn-primary"
								onclick="DoJoinForm__checkLoginIdDup(this);"
								name="btnLoginIdDupCheck" type="button">중복체크</button>
							<!-- 여기있는 this는 <button을 말함 -->
						</div>
					</td>
				</tr>
				<tr>
					<th><span>비밀번호</span></th>
					<td>
						<div>
							<input type="password" name="loginPw" maxlength="50"
								placeholder="비밀번호를 입력해주세요" />
						</div>
					</td>
				</tr>
				<tr>
					<th><span>비밀번호 확인</span></th>
					<td>
						<div>
							<input type="password" name="loginPwConfirm" maxlength="50"
								placeholder="비밀번호를 입력해주세요" />
						</div>
					</td>
				</tr>

				<tr>
					<th><span>이름</span></th>
					<td>
						<div>
							<input type="text" name="name" maxlength="50"
								placeholder="이름을 입력해주세요" />
						</div>
					</td>
				</tr>

				<tr>
					<th><span>닉네임</span></th>
					<td>
						<div>
							<input type="text" name="nickname" maxlength="50"
								placeholder="닉네임을 입력해주세요" />
						</div>
					</td>
				</tr>

				<tr>
					<th><span>이메일</span></th>
					<td>
						<div>
							<input type="email" name="email" maxlength="100"
								placeholder="이메일을 입력해주세요" />
						</div>

					</td>
				</tr>

				<tr>
					<th><span>연락처</span></th>
					<td>
						<div>
							<input type="tel" name="cellphoneNo" maxlength="10"
								placeholder="연락처를 입력해주세요" />
						</div>
					</td>
				</tr>

				<tr>
					<th></th>
					<td>
						<div>
							<div class="btn-wrap">
								<input class="btn btn-primary" type="submit" value="완료" />
								<button class="btn btn-info" type="button"
									onclick="history.back();">취소</button>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


<%@ include file="../../part/foot.jspf"%>