<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="<c:url value='/resources/css/bootstrap.min.css'/>"
	rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
</head>
<body>
	<section class="py-5">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-7 col-lg-6 col-xl-6">

					<div class="card shadow-lg p-4 p-md-5">
						<div class="card-body text-center">

							<h2 class="fw-bold mb-4 text-primary">COOK LOG</h2>

							<form class="text-start" action="login" method="post">

								<div class="mb-3">
									<label class="form-label fw-bold" for="mId">아이디</label> <input
										class="form-control form-control-lg rounded-1 w-100"
										type="text" id="mId" name="mId" placeholder="아이디를 입력해주세요"
										required />
								</div>

								<div class="mb-4">
									<label class="form-label fw-bold" for="mPw">비밀번호</label> <input
										class="form-control form-control-lg rounded-1 w-100"
										type="password" id="mPw" name="mPw" placeholder="비밀번호를 입력해주세요"
										required />
								</div>

								<div class="d-grid mb-4">
									<button class="btn btn-primary btn-lg rounded-1" type="submit">로그인</button>
								</div>
								<c:if test="${not empty error}">
								    <p class="text-danger text-center">${error}</p>
								</c:if>

								<div class="d-flex justify-content-between">
									<a class="btn btn-secondary btn-sm rounded-1 col-5" href="#">아이디
										찾기</a> <a class="btn btn-secondary btn-sm rounded-1 col-5"
										href="#">비밀번호 찾기</a>
								</div>
								<div class="d-grid mt-4">
									<a class="btn btn-success btn-lg rounded-1"
										href="<c:url value='/member/join'/>">회원가입</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>