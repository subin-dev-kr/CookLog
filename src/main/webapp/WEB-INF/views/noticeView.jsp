<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<title>게시글</title>
</head>
<body>

	<main class="container py-5">

		<h1 class="mb-4 fw-bold border-bottom pb-2 text-dark">공지사항</h1>

		<div class="card p-4 shadow-sm">
			<div class="d-flex justify-content-between align-items-end mb-2">		
				<h3 class="fw-bold mb-0">${notice.nTitle}</h3>
				<div class="text-muted small">
					${notice.nCreationDate} | 조회수: ${notice.nViewCount}
				</div>
			</div>
			<div class="text-muted small mb-4">
				<span>작성자: ${notice.mNickName}</span> 
			</div>
			<!-- 내용 -->
			<pre class="notice-content border-top pt-4">${notice.nContent}</pre>
			<!-- 버튼 영역 -->
			<div class="d-flex justify-content-between pt-4 border-top mt-4">

				<a class="btn btn-primary" href="<c:url value='/notice/list'/>">돌아가기</a>

				<c:if test="${sessionScope.loginMember.mRole eq 1}">
					<div>
						<a class="btn btn-secondary me-2"
							href="<c:url value='/notice/updateNoticeForm?nNum=${notice.nNum}'/>">수정</a>

						<form action="<c:url value='/notice/delete?nNum=${notice.nNum}'/>"
							method="post" class="d-inline">
							<button class="btn btn-danger" type="submit"
								onclick="return confirm('정말로 삭제하시겠습니까?');">삭제</button>
						</form>

					</div>
				</c:if>

			</div>

		</div>

	</main>

</body>
</html>