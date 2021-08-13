<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="회원가입" />
<%@ include file="../../part/head.jspf" %>
	<h1>${pageTitle}</h1>

	<div>
		<form action="doJoin" method="post">
			<div>
				<div>아이디</div>
				<div><input type="text" name="loginId" maxlength="50" placeholder="사용할 아이디를 입력해주세요."/></div>
			</div>
			<hr />
			<div>
				<div>비밀번호</div>
				<div><input type="password" name="loginPw" maxlength="5000" placeholder="비밀번호"></textarea></div>
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