<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css?v=20251207'/>"
	rel="stylesheet">
<title>레시피 등록하기</title>
</head>
<body>

	<main class="container py-5">

		<form action="<c:url value='/recipe/create'/>" method="post"
			enctype="multipart/form-data">

			<h2 class="text-1000 fs-4 fw-bold mb-4 border-bottom pb-2">레시피
				작성</h2>

			<section class="mb-5">
				<div class="row g-4">
					<div class="col-lg-7">

						<div class="mb-3">
							<label for="rTitle" class="form-label fw-bold">요리명</label> <input
								type="text" class="form-control form-control-lg" id="rTitle"
								name="rTitle" placeholder="요리명을 입력하세요" required>
						</div>

						<div class="mb-4">
							<label for="rDescription" class="form-label fw-bold">요리
								소개</label>
							<textarea class="form-control" id="rDescription"
								name="rDescription" rows="3" placeholder="레시피에 대한 간단한 소개를 적어주세요"></textarea>
						</div>

						<div class="row g-2">
							<div class="col-sm-6 col-md-3">
								<label for="rCookType" class="form-label fw-bold small">종류</label>
								<select class="form-select" id="rCookType" name="rCookType"
									required>
									<option value="한식">한식</option>
									<option value="중식">중식</option>
									<option value="양식">양식</option>
									<option value="일식">일식</option>
									<option value="빵">빵</option>
									<option value="샐러드">샐러드</option>
									<option value="디저트">디저트</option>
									<option value="음료">음료</option>
									<option value="기타">기타</option>
								</select>
							</div>

							<div class="col-sm-6 col-md-3">
								<label for="rCookTime" class="form-label fw-bold small">요리
									시간</label> <select class="form-select" id="rCookTime" name="rCookTime"
									required>
									<option value="10분이내">10분이내</option>
									<option value="30분이내">30분이내</option>
									<option value="1시간이내">1시간이내</option>
									<option value="1시간30분이내">1시간30분이내</option>
									<option value="2시간이내">2시간이내</option>
									<option value="2시간이상">2시간이상</option>
								</select>
							</div>

							<div class="col-sm-6 col-md-3">
								<label for="rServing" class="form-label fw-bold small">인원</label>
								<select class="form-select" id="rServing" name="rServings"
									required>
									<option value="1인분">1인분</option>
									<option value="2인분">2인분</option>
									<option value="3인분">3인분</option>
									<option value="4인분">4인분</option>
									<option value="5인분이상">5인분이상</option>
								</select>
							</div>

							<div class="col-sm-6 col-md-3">
								<label for="rLevel" class="form-label fw-bold small">난이도</label>
								<select class="form-select" id="rLevel" name="rLevel" required>
									<option value="쉬움">쉬움</option>
									<option value="보통">보통</option>
									<option value="어려움">어려움</option>
								</select>
							</div>
						</div>
					</div>

					<div class="col-lg-5">
						<div
							class="card bg-light p-4 h-100 d-flex flex-column justify-content-center align-items-center">
							<label for="rCenterImageFile" class="form-label fw-bold mb-3">대표
								이미지</label>
							<div class="border rounded w-100 mb-3 center-image-style">
								<img class="centerImagePreview" id="rCenterImagePreview" src="#"
									alt="이미지 미리보기"> <span id="rCenterImagePlaceholder"
									class="centerImagePlaceholder">이미지 미리보기</span>
							</div>
							<input class="form-control" type="file" id="rCenterImageFile"
								name="rCenterImageFile" accept="image/*" required>
						</div>
					</div>
				</div>
			</section>

			<section class="mb-5">
				<h3 class="fs-5 fw-bold mb-3">요리 재료 (재료 목록)</h3>
				<div class="card p-3">
					<textarea class="form-control" id="rIngredients"
						name="rIngredients" rows="5"
						placeholder="주재료, 양념재료, 소스재료등 따로 구분해서 작성 해주세요!"></textarea>
				</div>
			</section>

			<section class="mb-5">
				<h3 class="fs-5 fw-bold mb-3">요리 순서</h3>

				<div id="stepArea">
					<div class="step-block card p-4 shadow-sm mb-3 border">
						<div
							class="d-flex justify-content-between align-items-center mb-3">
							<h4 class="fs-6 fw-bold m-0">STEP 1</h4>
							<input type="hidden" name="cookingSteps[0].cStep" value="0">
							<button type="button" class="btn btn-sm btn-danger"
								onclick="removeStepField(this)">STEP 삭제</button>
						</div>

						<div class="row g-3">
							<div class="col-lg-7">
								<label for="cookingSteps[0].cInstructions"
									class="form-label small text-muted">요리 내용</label>
								<textarea class="form-control"
									id="cookingSteps[0].cInstructions"
									name="cookingSteps[0].cInstructions" rows="5"
									placeholder="이 단계의 요리 순서를 자세히 적어주세요." required></textarea>
							</div>

							<div class="col-lg-5">
								<div
									class="card bg-light p-3 h-100 d-flex flex-column justify-content-center">

									<label for="cookingSteps[0].cImageFile"
										class="form-label small text-muted">요리 이미지</label>

									<div
										class="image-preview-container mb-3 border rounded w-100 image-hidden">
										<img class="stepImagePreview image-cover" data-step-id="0"
											src="#" alt="단계 이미지 미리보기"> <span
											class="stepImagePlaceholder">이미지 미리보기</span>
									</div>

									<input class="form-control stepImageFile" type="file"
										id="cookingSteps[0].cImageFile"
										name="cookingSteps[0].cImageFile" accept="image/*"
										onchange="handleStepImagePreview(this)"> <input
										type="hidden" name="cookingSteps[0].cImage" value="" />
								</div>
							</div>

						</div>
					</div>
				</div>

				<div class="text-center mt-4">
					<button type="button" class="btn btn-success" id="addForm">요리
						순서 추가</button>
				</div>
			</section>

			<hr>
			<div class="d-flex justify-content-center mt-4 pt-3">
				<a href="<c:url value='/member/myPage'/>"
					class="btn btn-secondary btn-lg me-3">등록 취소</a>
				<button type="submit" class="btn btn-warning btn-lg">레시피 등록</button>
			</div>

		</form>
	</main>
	<script src="<c:url value='/resources/js/recipeForm.js'/>"></script>
</body>
</html>