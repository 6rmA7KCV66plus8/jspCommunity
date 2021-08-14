<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="메인화면" />
<%@ include file="../../part/head.jspf" %>

<!-- 이건 이런 방법도 있으니 참고만
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/이름.css" />
<script src="${pageContext.request.contextPath}/static/이름.js" defer></script> <!-- difer : 나중에 실행 됨(지연)
 -->
	<section class="title-bar con-min-width">
		<h1 class="con">
			${pageTitle}
		</h1>
	</section>
	

<!-- body자식은 div쓰지말고 section을 사용 -->
<%@ include file="../../part/foot.jspf" %>