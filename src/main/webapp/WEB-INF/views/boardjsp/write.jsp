<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link rel="stylesheet" type="text/css" href="../resources/css/default.css" />
</head>
<body>
<div class="centerdiv">
	<h1>[ 글쓰기 ]</h1>
	<form action="write" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text" name="title" id="title" style="width:400px;"></td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td><textarea name="contents" style="width:400px;height:200px;resize:none;"></textarea></td>
			</tr>
			
			<tr>
				<th>파일첨부</th>
				<td><input type="file" name="upload" size="30"></td>
			</tr>
		</table>
		<input type="submit" value="저장">
	</form>
</div>
</body>
</html>