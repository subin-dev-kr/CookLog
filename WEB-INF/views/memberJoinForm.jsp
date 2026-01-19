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
<title>회원가입</title>
</head>
<body>
	<section class="py-5">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-7 col-lg-6 col-xl-6">

					<div class="card shadow-lg p-4 p-md-5">
						<div class="card-body text-center">
							<h2 class="fw-bold mb-4 text-warning">회원가입</h2>

							<form class="text-start" action="<c:url value='/member/join'/>"
								method="post">

								<div class="mb-3">
									<label class="form-label fw-bold" for="mId">아이디</label>
									<div class="input-group">
										<input class="form-control form-control-lg rounded-1"
											type="text" id="mId" name="mId" placeholder="아이디를 입력하세요"
											required />
										<button class="btn btn-warning rounded-1 ms-2" type="button"
											id="checkIdBtn">중복확인</button>
										<p id="idCheckMsg"></p>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label fw-bold" for="mPw">비밀번호</label> <input
										class="form-control form-control-lg rounded-1 w-100"
										type="password" id="mPw" name="mPw" placeholder="비밀번호를 입력하세요"
										required />
								</div>

								<div class="mb-3">
									<label class="form-label fw-bold" for="mPwConfirm">비밀번호
										재확인</label>
									<div class="input-group">
										<input class="form-control form-control-lg rounded-1"
											type="password" id="mPwConfirm" name="mPwConfirm"
											placeholder="비밀번호를 한번 더 입력하세요" required />
										<button class="btn btn-warning rounded-1 ms-2" type="button"
											id="pwCheckBtn">재확인</button>
										<p id="pwCheckMsg"></p>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label fw-bold" for="mNickName">닉네임</label>
									<div class="input-group">
										<input class="form-control form-control-lg rounded-1"
											type="text" id="mNickName" name="mNickName" placeholder="닉네임"
											required />
										<button class="btn btn-warning rounded-1 ms-2" type="button"
											id="checkNickBtn">중복확인</button>
										<p id="nickCheckMsg"></p>
									</div>
								</div>

								<div class="mb-3">
									<label class="form-label fw-bold" for="email">이메일</label>
									<div class="input-group">
										<input class="form-control form-control-lg rounded-1"
											type="email" id="email" name="email" placeholder="이메일 입력하세요"
											required />
										<button class="btn btn-warning rounded-1 ms-2" type="button"
											id="checkEmailBtn">중복확인</button>
										<p id="emailCheckMsg"></p>
									</div>
								</div>

								<div class="mb-4">
									<label class="form-label fw-bold" for="phone">전화번호</label> <input
										class="form-control form-control-lg rounded-1 w-100"
										type="tel" id="phone" name="phone" placeholder="-없이 숫자만 입력하세요" />
								</div>

								<div class="d-flex justify-content-between mt-5">
									<a class="btn btn-primary btn-lg rounded-1 col-5"
										href="<c:url value='/'/>">가입 취소</a>
									<button class="btn btn-warning btn-lg rounded-1 col-5"
										type="submit">가입 하기</button>
								</div>

							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="<c:url value='/resources/js/memberJoinForm.js'/>"></script>
</body>
</html>