<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<title>게시글 목록</title>
</head>
<body class="m-0 d-flex flex-column min-vh-100">
	<!-- header -->
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<!-- 공지사항 -->
	<jsp:include page="/WEB-INF/views/noticeRolling.jsp" />

	<!-- main -->
	<main class="container py-5 flex-grow-1">
		<div class="d-flex justify-content-between align-items-center">
			<h1 class="mb-4 fw-bold border-bottom pb-2 text-dark">공지사항</h1>
			<c:if test="${sessionScope.loginMember.mRole eq 1}">
				<a
					class="bg-warning text-white py-2 px-3 rounded-1 text-center text-decoration-none"
					href="<c:url value='/notice/create'/>">게시글 작성</a>
			</c:if>
		</div>
		<div class="d-flex justify-content-between align-items-center mb-4">

			<form action="<c:url value='/notice/readSome'/>" method="get"
				class="d-flex align-items-center me-3">
				<select name="noticeSearchType" class="form-select me-2"
					style="width: 120px">
					<option value="nTitle">제목</option>
					<option value="nContent">내용</option>
					<option value="mNickName">작성자</option>
				</select> <input type="text" name="noticeSearchKeyword"
					class="form-control me-2 flex-grow-1"
					value="${pageMaker.cri.noticeSearchKeyword}"
					placeholder="검색어를 입력하세요"> <input class="btn btn-secondary"
					type="submit" value="조회">
			</form>

		</div>

		<div class="card shadow-sm border">
			<div class="card-header bg-primary text-white fw-bold">공지사항 리스트
			</div>
			<div class="table-responsive">
				<table class="table table-hover mb-0">
					<thead class="table-light">
						<tr>
							<th>고유번호</th>
							<th>제목</th>
							<th>작성자</th>
							<th>작성일</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr>
								<td>${item.nNum}</td>
								<td><a
									href="<c:url value='/notice/readOne?nNum=${item.nNum}'/>">${item.nTitle}</a></td>
								<td>${item.mNickName}</td>
								<td>${item.nCreationDate}</td>
								<td>${item.nViewCount}</td>

							</tr>
						</c:forEach>


						<c:if test="${empty list}">
							<tr>
								<td colspan="5" class="text-center p-4 text-muted">등록된
									공지사항이 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>

		<!-- 페이징 -->
		<section class="container py-5">

			<nav aria-label="Notice Page navigation">
				<ul class="pagination justify-content-center">

					<!-- 이전 페이지 -->
					<li class="page-item ${paging.currentPage == 1 ? 'disabled' : ''}">
						<c:choose>
							<c:when test="${paging.currentPage == 1}">
								<span class="page-link">&laquo;</span>
							</c:when>
							<c:otherwise>
								<a class="page-link"
									href="<c:url value='/notice/readSome'>
							                <c:param name='page' value='${paging.currentPage - 1}'/>
							                <c:if test='${not empty noticeSearchType}'>
							                    <c:param name='noticeSearchType' value='${noticeSearchType}'/>
							                </c:if>
							                <c:if test='${not empty noticeSearchKeyword}'>
							                    <c:param name='noticeSearchKeyword' value='${noticeSearchKeyword}'/>
							                </c:if>
							            </c:url>">
									&laquo; </a>
							</c:otherwise>
						</c:choose>
					</li>

					<!-- 현재 페이지 -->
					<c:forEach begin="${paging.startPage}" end="${paging.endPage}"
						var="pageNum">
						<li
							class="page-item ${pageNum == paging.currentPage ? 'active' : ''}">
							<a class="page-link"
							href="<c:url value='/notice/readSome'>
							            <c:param name='page' value='${pageNum}'/>
							            <c:if test='${not empty noticeSearchType}'>
							                <c:param name='noticeSearchType' value='${noticeSearchType}'/>
							            </c:if>
							            <c:if test='${not empty noticeSearchKeyword}'>
							                <c:param name='noticeSearchKeyword' value='${noticeSearchKeyword}'/>
							            </c:if>
							        </c:url>">
								${pageNum} </a>
						</li>
					</c:forEach>

					<!-- 다음 페이지 -->
					<li
						class="page-item ${paging.currentPage == paging.totalPages ? 'disabled' : ''}">
						<c:choose>
							<c:when test="${paging.currentPage == paging.totalPages}">
								<span class="page-link">&raquo;</span>
							</c:when>
							<c:otherwise>
								<a class="page-link"
									href="<c:url value='/notice/readSome'>
							                <c:param name='page' value='${paging.currentPage + 1}'/>
							                <c:if test='${not empty noticeSearchType}'>
							                    <c:param name='noticeSearchType' value='${noticeSearchType}'/>
							                </c:if>
							                <c:if test='${not empty noticeSearchKeyword}'>
							                    <c:param name='noticeSearchKeyword' value='${noticeSearchKeyword}'/>
							                </c:if>
							            </c:url>">
									&raquo; </a>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</section>

	</main>

	<!--footer -->
	<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>