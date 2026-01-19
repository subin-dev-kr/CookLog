<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<title>게시글 수정</title>
</head>
<body>

	<main class="container py-5">

		<h1 class="mb-4 fw-bold border-bottom pb-2 text-dark">게시글 수정</h1>

		<div class="card p-4 shadow-sm">
			<form action="<c:url value='/notice/update'/>" method="post">

				<div class="mb-3">
					<label for="nTitle" class="form-label fw-bold">제목</label>
					<input type="text" class="form-control" id="nTitle" name="nTitle"
						value="${notice.nTitle}" placeholder="공지사항 제목을 입력하세요" required>
				</div>

				<div class="mb-3">
					<label for="mNickName" class="form-label fw-bold">작성자</label> <input
						type="text" class="form-control" id="mNickName" name="mNickName"
						value="${notice.mNickName}" readonly> <input type="hidden"
						name="nNum" value="${notice.nNum}">
				</div>

				<div class="mb-4">
					<label for="nContent" class="form-label fw-bold">내용</label>
					<textarea class="form-control" id="nContent" name="nContent"
						rows="15" placeholder="공지 내용을 입력하세요" required>${notice.nContent}</textarea>
				</div>

				<div class="d-flex justify-content-center pt-3 border-top">
					<a href="<c:url value='/notice/list'/>"
						class="btn btn-secondary me-3">수정취소</a> <input
						class="btn btn-primary" type="submit" value="완료">
				</div>

			</form>
		</div>

	</main>

</body>
</html>