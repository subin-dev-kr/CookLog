<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&" />
<script src="https://kit.fontawesome.com/abd2984efb.js"></script>
<title>My 페이지</title>
</head>
<body>
	<!-- header -->
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<!-- 공지사항 -->
	<jsp:include page="/WEB-INF/views/noticeRolling.jsp" />

	<!-- main -->
	<main>
		<section class="container py-5">
			<div class="row">
				<div class="col-lg-8">
					<div class="d-flex justify-content-between align-items-end mb-4 border-bottom pb-2">
						<h2 class="text-1000 fs-4 fw-bold">마이홈</h2>
						<a href="<c:url value='/recipe/create'/>"
							class="btn btn-warning rounded-1">레시피 등록</a>
					</div>
					<!-- 검색창 -->
					<form class="mb-5" action="<c:url value='/recipe/readSome'/>"
						method="get">
						<input type="hidden" name="page" value="1">
						<div class="input-group">
							<input class="form-control" type="text"
								name="recipeSearchKeyword" placeholder="요리명을 검색해주세요"
								aria-label="Search">
							<button class="btn btn-primary" type="submit">
								<i class="material-symbols-outlined input-box-icon text-white">search</i>
							</button>
						</div>
					</form>
				</div>

				<div class="col-md-4">
					<form class="p-4 border rounded shadow-sm">
						<c:if test="${not empty sessionScope.loginMember}">
							<h2>
								<a href="<c:url value='/member/updateForm'/>">프로필</a>
							</h2>
							<p>
								<a href="<c:url value='/member/updateForm'/>"
									class="fs-4 fw-bold text-primary">${sessionScope.loginMember.mNickName}
									님</a>
							</p>
						</c:if>
					</form>
				</div>

			</div>
		</section>

		<hr class="mt-0">
		<!-- 레시피리스트 -->
		<section class="container py-4">
			<div class="row gx-5 gy-5">
				<c:choose>
					<c:when test="${empty recipeList}">
						<div class="col-12 text-center text-muted py-5">
							<p class="fs-5">등록된 레시피가 없습니다.</p>
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${recipeList}" var="recipe">
							<div class="col-sm-6 col-lg-4">
								<div class="card h-100 shadow-sm border">

									<div class="position-relative card-img-fixed-height">
										<c:if test="${not empty recipe.rCenterImagePath}">
											<a href="<c:url value='/recipe/myView?rNum=${recipe.rNum}'/>">
												<img
												src="<c:url value='/uploadedImages/${recipe.rCenterImagePath}'/>"
												class="img-fluid rounded-top w-100" alt="레시피 대표 이미지">
											</a>
										</c:if>
									</div>

									<div class="card-body p-3">
										<h3 class="fs-4 fw-bold text-truncate mb-2">
											<a href="<c:url value='/recipe/myView?rNum=${recipe.rNum}'/>"
												class="text-decoration-none text-dark"> ${recipe.rTitle}
											</a>
										</h3>
										<p class="card-text text-muted small mb-1">작성자:
											${recipe.mNickName}</p>
									</div>

									<div class="card-footer bg-white border-top p-3 d-flex justify-content-between align-items-center">
										<span class="text-danger small"><i class="fas fa-heart me-1"></i> 좋아요(${recipe.likeCount})</span> 
										<span class="text-muted small">조회수(${recipe.rViewCount})</span>
										
										<c:if test="${not empty sessionScope.loginMember}">
										    <c:if test="${sessionScope.loginMember.mRole eq 1}">
										        <form action="<c:url value='/recipe/delete'/>"
										              method="post"
										              class="m-0 p-0"
										              style="display: contents;"
										              onsubmit="return confirm('관리자 권한으로 이 레시피를 삭제하시겠습니까?');">
										            <input type="hidden" name="rNum" value="${recipe.rNum}">
										            <input type="hidden" name="targetId" value="${targetId}">
										            <button type="submit" class="btn btn-danger btn-sm">관리자 삭제</button>
										        </form>
										    </c:if>
										</c:if>
									</div>
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</section>

		<!-- 페이징 -->
		<section class="container py-5">
			<nav aria-label="Recipe Page navigation">
				<ul class="pagination justify-content-center">
					<!-- 이전 페이지 -->
					<li class="page-item ${paging.currentPage == 1 ? 'disabled' : ''}">
						<c:choose>
							<c:when test="${paging.currentPage == 1}">
								<span class="page-link">&laquo;</span>
							</c:when>
							<c:otherwise>
								<a class="page-link"
									href="<c:url value='/recipe/readSome?page=${paging.currentPage - 1}'/>">&laquo;</a>
							</c:otherwise>
						</c:choose>
					</li>
					<!-- 현재 페이지 -->
					<c:forEach begin="${paging.startPage}" end="${paging.endPage}"
						var="pageNum">
						<li
							class="page-item ${pageNum == paging.currentPage ? 'active' : ''}">
							<a class="page-link"
							href="<c:url value='/recipe/readSome'>
						               <c:param name='page' value='${pageNum}'/>
						               <c:if test='${not empty param.recipeSearchKeyword}'>
						                   <c:param name='recipeSearchKeyword' value='${param.recipeSearchKeyword}'/>
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
									href="<c:url value='/recipe/readSome?page=${paging.currentPage + 1}'/>">
									&raquo; </a>
							</c:otherwise>
						</c:choose>
					</li>
				</ul>
			</nav>
		</section>
	</main>

	<!-- footer -->
	<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>