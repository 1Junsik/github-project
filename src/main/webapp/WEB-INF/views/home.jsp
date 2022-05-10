<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	[ practice5 Spring Project ]
</h1>

<c:if test="${sessionScope.loginId != null}">
	<b>* ${sessionScope.loginName}(${sessionScope.loginId})님 로그인</b>
	<p><a href="member/logout">로그아웃</a></p>
	<p><a href="member/update">개인정보 수정</a></p>
</c:if>

<c:if test="${sessionScope.loginId == null}">
	<p><a href="member/join">회원가입</a></p>
	<p><a href="member/login">로그인</a></p>
</c:if>
<p><a href="board/list">게시판</a></p>

</body>
</html>
