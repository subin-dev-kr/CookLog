<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header id="mainHeader" class="fixed-header">
	<div class="container mt-4 d-flex justify-content-between align-items-center">
		<div>
			<h1>
				<a href="<c:url value='/'/>" class="text-decoration-none text-dark">
					<c:if test="${sessionScope.loginMember.mRole ne 1}">
					  <img src="${pageContext.request.contextPath}/resources/images/logo.svg" alt="logo">
					</c:if>
					<strong class="text-1000 fs-5 fw-bold ms-2 text-gradient">CookLog</strong><br>
					<span class="d-block text-muted fs-1 fw-normal lh-1">당신의 요리를 기록해 보세요!</span>
				</a>
			</h1>
		</div>

		<div>
			<form action="<c:url value='/homeRecipeSearch'/>" method="get">
				<div class="drop-content">
					<select class="select-content" name="searchTab">
						<option value="title">요리명</option>
						<option value="nickName">작성자</option>
					</select> <input class="line" type="text" name="searchKeyword" placeholder="search">
					<button type="submit">
						<i class="material-symbols-outlined btn-icon">search</i>
					</button>
				</div>
			</form>
		</div>

		<div class="text-end">
			<!-- 모든 유저 뷰 -->
			<c:if test="${empty sessionScope.loginMember}">
				<a href="<c:url value='/member/joinForm'/>" class="btn btn-white shadow-warning text-warning me-2"> 
					<i class="fa-solid fa-user me-2"></i>회원가입
				</a>
				<a href="<c:url value='/login'/>" class="btn btn-white shadow-warning text-warning me-2"> 
					<i class="fa-solid fa-user me-2"></i>로그인
				</a>
			</c:if>
			<!-- 회원 유저 뷰 -->
			<c:if test="${not empty sessionScope.loginMember}">
				<a href="<c:url value='/logout'/>" class="btn btn-white shadow-warning text-warning me-2"> 
					<i class="fa-solid fa-right-from-bracket me-2"></i>로그아웃
				</a>
				<a href="<c:url value='/member/myPage'/>" class="btn btn-white shadow-warning text-warning me-2"> 
					<i class="fa-solid fa-house-user me-2"></i>마이 페이지
				</a>
			</c:if>
			<!-- 관리자 뷰 -->
			<c:if test="${sessionScope.loginMember.mRole eq 1}">
				<a href="<c:url value='/member/readAll'/>" class="btn btn-white shadow-warning text-warning me-2"> 
					<i class="fa-solid fa-users me-2 "></i>회원 리스트
				</a>
			</c:if>
		</div>

	</div>
</header>