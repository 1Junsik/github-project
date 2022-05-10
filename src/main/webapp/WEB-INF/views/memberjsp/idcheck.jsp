<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>idcheck</title>
<link rel="stylesheet" type="text/css" href="../resources/css/default.css" />
<script>
function formCheck() {
	let id = document.getElementById('searchId');
	if (id.value == '') {
		alert('검색할 ID를 입력하세요.');
		return false;
	}
	return true;
}

function selectid() {
	opener.document.getElementById('id').value = '${searchId}';
	this.close();
}
</script>
</head>
<body>
<div class="centerdiv">
<h1>[ ID 중복검사 ]</h1>
<form action="idcheck" method="post" onSubmit="return formCheck();">
	검색할 ID <input type="text" name="searchId" id="searchId">
			<input type="submit" value="검색">
</form>

<!-- 검색 후  -->
<c:if test="${searchId != null}">
	<!-- 검색 결과 없음  -->
	<c:if test="${member == null}">
		<p>${searchId}는 사용할 수 있는 아이디입니다.</p>
		<p><input type="button" value="ID사용하기" onclick="selectid()"></p>
	</c:if>
	<!-- 검색 결과 있음 -->
	<c:if test="${member != null}">
		<p>${searchId}는 이미 사용중인 아이디입니다.</p>
	</c:if>
</c:if>
<br>
<br>
</div>

</body>
</html>