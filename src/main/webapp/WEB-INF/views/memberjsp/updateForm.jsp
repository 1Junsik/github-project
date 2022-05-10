<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>개인정보수정</title>
<link href="${pageContext.request.contextPath}/resources/css/default.css" rel="stylesheet" type="text/css">
<script>
function formCheck() {
	let pw1 = document.getElementById('password');
	let pw2 = document.getElementById('password2');
	let name = document.getElementById('name');
	
	if (pw1.value.length < 3 || pw1.value.length > 10) {
		alert('비밀번호는 3~10자로 입력하세요.');
		return false;
	}
	if (pw1.value != pw2.value) {
		alert('비밀번호를 확인하세요.');
		return false;
	}
	if (name.value == '') {
		alert('이름을 입력하세요.');
		return false;
	}
	return true;
}

</script>
</head>
<body>
	<div class="centerdiv">
		<h1>[ 개인 정보 수정 ]</h1>
				
		<form id="updateform" action="update" method="post" onsubmit="return formCheck()">
			<table>
				<tr>
					<th>ID</th>
					<td>
						${member.id}
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" name="password" id="password" placeholder="비밀번호 입력"><br>
						<input type="password" id="password2" placeholder="비밀번호 다시 입력">
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" value="${member.name}" name="name" id="name" placeholder="이름 입력"></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="text" value="${member.phone}" name="phone" placeholder="전화번호 입력"></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" value="${member.address}" name="address" placeholder="주소 입력" style="width:300px;"></td>
				</tr>
				<tr>
					<th>이메일</th>
					<td><input type="text" value="${member.email}" name="email" id="email" placeholder="이메일 입력"></td>
				</tr>
			</table>
				<br>
			<input type="submit" value="수정">
			<input type="reset" value="다시 쓰기">
		</form>
	</div>
</body>
</html>