<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판 글읽기</title>
<link rel="stylesheet" type="text/css" href="../resources/css/default.css" />
<script>
function deleteBoard(num) {
	if (confirm('정말 삭제하시겠습니까?')) {
		location.href = 'delete?boardnum=' + num;
	}
}
</script>
</head>
<body>
<div class="centerdiv">
	<h1>[ 게시판 글읽기 ]</h1>
	<table>
		<tr>
			<th style="width:100px;">작성자</th>
			<td style="width:600px;">${board.id}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${board.inputdate}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${board.hits}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${board.title}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td><pre>${board.contents}</pre></td>
		</tr>
		<tr>
			<th>파일첨부</th>
			<td><a href="download?boardnum=${board.boardnum}">${board.originalfile}</a></td>
		</tr>
	</table>
	
	<!-- 본인 글 수정/삭제  -->
	<c:if test="${sessionScope.loginId == board.id}">
		<a href="edit?boardnum=${board.boardnum}">수정</a>
		<a href="javascript:deleteBoard(${board.boardnum})">삭제</a>
	</c:if>
	
	<!-- 목록보기  -->
	<a href="list">목록보기</a>
	
	<!-- 리플 작성 폼  -->
	<c:if test="${sessionScope.loginId != null}">
		<form action="replyWrite" method="post">
			리플내용
				<input type="hidden" name="boardnum" value="${board.boardnum}">
				<input type="text" name="text" style="width:500px;">
				<input type="submit" value="확인">
		</form>
	</c:if>
	
	<!-- 리플 목록 출력  -->
	<table class="reply">
		<c:forEach var="reply" items="${replyList}">
			<tr>
				<td class="replyid"><b>${reply.id}</b></td>
				<td class="replytext"><b>${reply.text}</b></td>
			</tr>
		</c:forEach>
	</table>

</div>
</body>
</html>