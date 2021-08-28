<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="com.sbs.example.util.Util" %> <!-- tomcat7 이상은 page import뒤에 ; 붙이면 안됌 -->
<%
Object data = request.getAttribute("data");
response.getWriter().print(Util.getJsonText(data));
%>