<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/cookLog.css'/>" rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&" />
<script src="https://kit.fontawesome.com/abd2984efb.js"></script>
<meta charset="UTF-8">
<script>
    const contextPath = "${pageContext.request.contextPath}";
</script>
<title>CookLog</title>
</head>
<body>
	<!-- header -->
	<jsp:include page="/WEB-INF/views/header.jsp" />
	<!-- 공지사항 -->
	<jsp:include page="/WEB-INF/views/noticeRolling.jsp" />

	<main>
		<!-- 배너 -->
		<section class="py-5 overflow-hidden bg-primary" id="home">
			<div class="container">
				<div class="row flex-center">
					<div class="col-md-5 col-lg-6 order-0 order-md-1 mt-8 mt-md-0">
						<a class="img-landing-banner" href="<c:url value='/'/>"> <img
							class="img-fluid" src="${pageContext.request.contextPath}/resources/images/hero-header.png"
							alt="hero-header" />
						</a>
					</div>
					<div class="col-md-7 col-lg-6 py-8 text-md-start text-center">
						<h1 class="display-1 fs-md-5 fs-lg-6 fs-xl-8 text-light">배고프세요?</h1>
						<h1 class="text-800 mb-5 fs-4">
							CookLog에서 쉽고 빠르게 <br class="d-none d-xxl-block" />레시피를 찾아보세요!
						</h1>

						<!-- 레시피 검색 -->
						<form class="search-tab"
							action="<c:url value='/homeRecipeSearch'/>" method="get">
							<!--라디오 버튼-->
							<input type="radio" id="tab-title" name="searchTab" value="title"
								checked> <input type="radio" id="tab-nickname"
								name="searchTab" value="nickName">
							<!-- 탭 버튼 -->
							<div class="search-tab-buttons">
								<label for="tab-title"><i
									class="material-symbols-outlined tab-icon">chef_hat</i>요리명</label> <label
									for="tab-nickname"><i
									class="material-symbols-outlined tab-icon">id_card</i>닉네임</label>
							</div>
							<!--검색창 영역-->
							<div class="search-tab-content">
								<!-- 요리명 검색 -->
								<div class="search-box box-title">
									<input class="no-line" type="text" name="searchKeyword"
										id="input-title" placeholder="찾으려는 요리명을 입력해주세요">
									<button type="submit">검색</button>
								</div>
								<!-- 닉네임 검색 -->
								<div class="search-box box-nickName">
									<input class="no-line" type="text" name="searchKeyword"
										id="input-nickName" placeholder="찾으려는 닉네임을 입력해주세요">
									<button type="submit">검색</button>
								</div>
							</div>
						</form>
						<!-- 검색 코드 끝! -->
					</div>
				</div>
			</div>
		</section>
		<!-- 레시피 리스트 -->
		<section>
			<div class="container py-4">
				<!-- 라디오 버튼 -->
				<input type="radio" name="tab" id="recent"
					<c:if test="${sort eq 'recent' || empty sort}">checked</c:if>>

				<input type="radio" name="tab" id="like"
					<c:if test="${sort eq 'like'}">checked</c:if>> <input
					type="radio" name="tab" id="view"
					<c:if test="${sort eq 'view'}">checked</c:if>> <input
					type="radio" name="tab" id="suggestion"
					<c:if test="${sort eq 'suggestion'}">checked</c:if>> <input
					type="radio" name="tab" id="scrap"
					<c:if test="${sort eq 'scrap'}">checked</c:if>>

				<!-- 탭 버튼 -->
				<div class="tab-button">
					<label for="recent" class="re" data-sort="recent">최신순</label> <label
						for="like" class="li" data-sort="like">인기순</label> <label
						for="view" class="vi" data-sort="view">조회순</label> <label
						for="suggestion" class="su" data-sort="suggestion">추천순</label> <label
						for="scrap" class="sc" data-sort="scrap">스크랩순</label>
				</div>
				<!-- 탭 버튼 컨텐츠 -->
				<div class="tab-content row gx-5 gy-5">
					<c:choose>
						<c:when test="${empty recipeList}">
							<div class="col-12 text-center text-muted py-5">
								<p class="fs-5">등록된 레시피가 없습니다.</p>
							</div>
						</c:when>
						<c:otherwise>
							<c:forEach items="${recipeList}" var="recipe">
								<div class="col-sm-6 col-lg-4 ">
									<div class="card h-100 shadow-sm border">

										<div class="position-relative card-img-fixed-height">
											<c:if test="${not empty recipe.rCenterImagePath}">
												<a href="<c:url value='/recipe/view?rNum=${recipe.rNum}'/>">
													<img
													src="<c:url value='/uploadedImages/${recipe.rCenterImagePath}'/>"
													class="img-fluid rounded-top w-100" alt="레시피 대표 이미지">
												</a>
											</c:if>
										</div>

										<div class="card-body p-3">
											<h3 class="fs-4 fw-bold text-truncate mb-2">
												<a href="<c:url value='/recipe/view?rNum=${recipe.rNum}'/>"
													class="text-decoration-none text-dark">
													${recipe.rTitle} </a>
											</h3>
											<p class="card-text text-muted small mb-1">작성자:
												${recipe.mNickName}</p>
										</div>

										<div class="card-footer bg-white border-top p-3 d-flex justify-content-between align-items-center">
											<span class="text-danger small"><i class="fas fa-heart me-1"></i> 좋아요(${recipe.likeCount})</span> 
											<span class="text-muted small">조회수(${recipe.rViewCount})</span>
										</div>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</section>

		<!-- 페이징 -->
		<section class="container py-5">
			<nav aria-label="Recipe Page navigation">
				<ul class="pagination justify-content-center">
					<!-- 이전 버튼 -->
					<li class="page-item ${paging.currentPage == 1 ? 'disabled' : ''}">
						<c:choose>
							<c:when test="${paging.currentPage == 1}">
								<span class="page-link">&laquo;</span>
							</c:when>
							<c:otherwise>
								<a class="page-link"
									href="<c:url value='${empty searchKeyword ? "/" : "/homeRecipeSearch"}'>
							          <c:param name='page' value='${paging.currentPage - 1}'/>
							          <c:param name='sort' value='${sort}'/>
							          <c:if test='${not empty searchKeyword}'>
							              <c:param name='searchTab' value='${searchTab}'/>
							              <c:param name='searchKeyword' value='${searchKeyword}'/>
							          </c:if></c:url>">
									&laquo; </a>
							</c:otherwise>
						</c:choose>
					</li>
					<!-- 페이지 번호 -->
					<c:forEach begin="${paging.startPage}" end="${paging.endPage}"
						var="pageNum">
						<li
							class="page-item <c:if test="${pageNum == paging.currentPage}">active</c:if>">
							<a class="page-link"
							href="<c:url value='${empty searchKeyword ? "/" : "/homeRecipeSearch"}'>
	                        	<c:param name='page' value='${pageNum}'/>
	                        	<c:param name='sort' value='${sort}'/>
							    <c:if test='${not empty searchKeyword}'>
							        <c:param name='searchTab' value='${searchTab}'/>
							        <c:param name='searchKeyword' value='${searchKeyword}'/>
							    </c:if></c:url>">
								${pageNum} </a>
					</c:forEach>
					<!-- 다음 버튼 -->
					<li
						class="page-item ${paging.currentPage == paging.totalPages ? 'disabled' : ''}">
						<!-- disabled = 비활성화(클릭안됨) --> <c:choose>
							<c:when test="${paging.currentPage == paging.totalPages}">
								<!-- 마지막 페이지: 클릭 불가 -->
								<span class="page-link">&raquo;</span>
							</c:when>
							<c:otherwise>
								<!-- 다음 페이지 있음 -->
								<a class="page-link"
									href="<c:url value='${empty searchKeyword ? "/" : "/homeRecipeSearch"}'>
					                   <c:param name='page' value='${paging.currentPage + 1}'/>
					                   <c:param name='sort' value='${sort}'/>
					                   <c:if test='${not empty searchKeyword}'>
					                       <c:param name='searchTab' value='${searchTab}'/>
					                       <c:param name='searchKeyword' value='${searchKeyword}'/>
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

	<script src="<c:url value='/resources/js/homeRecipeSearch.js'/>"></script>
	<script src="<c:url value='/resources/js/recipeTab.js'/>"></script>
</body>