<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/cookLog.css'/>" rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&" />
<title>myRecipe</title>
</head>
<body>

	<header class="container">
		<h1
			class="fs-5 fw-bold ms-2 my-4 d-flex align-items-center text-danger">
			My 레시피<i class="material-symbols-outlined fs-5">chef_hat</i>
		</h1>
		<div
			class="mb-5 p-3 d-flex justify-content-between align-items-center shadow-sm">
			<h1 class="display-5 fw-bold">요리명: ${recipe.rTitle}</h1>
			<span class="text-primary fw-bold">작성자: ${recipe.mNickName}</span>
		</div>
	</header>

	<main class="container py-5">

		<section class="mb-5">
			<div class="row g-4">

				<div class="col-lg-6">
					<div class="card shadow-sm border-0">
						<img
							src="<c:url value='/uploadedImages/${recipe.rCenterImagePath}'/>"
							class="card-img-top rounded center-image-view" alt="대표 이미지">
					</div>
				</div>

				<div class="col-lg-6 d-flex flex-column justify-content-between">
					<div>
						<h2 class="fs-4 fw-bold mb-3">레시피 소개</h2>
						<p class="text-success">${recipe.rDescription}</p>
					</div>

					<hr class="my-3">

					<div class="row text-center">
						<div class="col-3 border-end">
							<p class="mb-0 fw-bold text-primary">종류</p>
							<p class="mb-0">${recipe.rCookType}</p>
						</div>
						<div class="col-3 border-end">
							<p class="mb-0 fw-bold text-primary">요리 시간</p>
							<p class="mb-0">${recipe.rCookTime}</p>
						</div>
						<div class="col-3 border-end">
							<p class="mb-0 fw-bold text-primary">인분</p>
							<p class="mb-0">${recipe.rServings}</p>
						</div>
						<div class="col-3">
							<p class="mb-0 fw-bold text-primary">난이도</p>
							<p class="mb-0">${recipe.rLevel}</p>
						</div>
					</div>
				</div>
			</div>
		</section>

		<section class="mb-5">
			<h2 class="fs-3 fw-bold mb-3">
				<i class="material-symbols-outlined">shopping_cart</i> 재료
			</h2>
			<div class="card p-4 shadow-sm bg-light">
				<p class="recipe-ingredient lead mb-0">${recipe.rIngredients}</p>
			</div>
		</section> 

		<section class="mb-5">
			<h2 class="fs-3 fw-bold mb-4">
				<i class="material-symbols-outlined">list_alt</i> 요리 순서
			</h2>

			<c:forEach var="step" items="${recipe.cookingSteps}"
				varStatus="status">
				<div class="card p-4 mb-3 shadow-sm">
					<div class="row g-4">

						<div class="col-lg-8">
							<h3 class="fs-5 fw-bold text-primary">STEP ${status.count}</h3>
							<p class="recipe-ingredient">${step.cInstructions}</p>
						</div>

						<div class="col-lg-4 text-center">
							<c:if test="${not empty step.cImage}">
								<img src="<c:url value='/uploadedImages/${step.cImage}'/>"
									alt="STEP ${status.count} 이미지"
									class="img-fluid rounded border step-image-view">
							</c:if>
						</div>

					</div>
				</div>
			</c:forEach>
			<div class="mt-3 text-end text-muted small">
				<div>등록일 : ${recipe.rCreationDate}</div>
				<div>수정일 : ${recipe.rUpdateDate}</div>
			</div>
		</section>

		<!-- 추후 업데이트 예정 -->
		<section class="mb-5 pt-3 border-top">
			<div class="d-flex justify-content-end align-items-center mb-4">
				<!-- contextPath 전달용 -->
				<input type="hidden" id="ctx" value="${pageContext.request.contextPath}">
				<button class="btn btn-danger me-2 d-flex justify-content-center align-items-center"
					type="button" id="likeBtn" data-rnum="${recipe.rNum}">
					좋아요<i class="material-symbols-outlined ms-1">favorite</i>
				</button>
				<button class="btn btn-secondary me-2 d-flex justify-content-center align-items-center">
					스크랩<i class="material-symbols-outlined ms-1">bookmark_star</i>
				</button>
			</div>

			<h3 class="fs-5 fw-bold mb-3">댓글</h3>
			<div class="border p-3 mb-3 bg-light rounded">
				<form class="d-flex justify-content-between align-items-center">
					<textarea class="form-control mb-2" rows="3"
						placeholder="댓글을 작성하세요"></textarea>
					<button type="submit" class="btn btn-primary btn-sm mx-4">등록</button>
				</form>
			</div>
		</section>
		<div class="container d-flex align-items-center">
			<a href="<c:url value='/member/myPage'/>"
				class="btn btn-success py-2 px-3 text-nowrap me-2">마이페이지</a>
			<div class="d-flex align-items-center ms-auto">
				<a href="<c:url value='/recipe/updateForm?rNum=${recipe.rNum}'/>"
					class="btn btn-primary py-2 px-3 text-nowrap me-2">수정하기</a>
				<form action="<c:url value='/recipe/delete?rNum=${recipe.rNum}'/>"
					 onsubmit="return confirm('정말로 삭제하시겠습니까?');" method="post" class="d-inline-flex">
					<button type="submit" class="btn btn-danger py-2 px-3 text-nowrap">삭제</button>
				</form>
			</div>
		</div>
	</main>
<script src="<c:url value='/resources/js/recipeLike.js'/>"></script>

</body>
</html>