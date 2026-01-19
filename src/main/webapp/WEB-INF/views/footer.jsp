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
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&" />
<script src="https://kit.fontawesome.com/abd2984efb.js"></script>
<title>footer</title>
</head>
<body>
	<footer class="py-0 pt-7 bg-1000">
		<div class="container">
			<div>
				<a class="navbar-brand d-inline-flex" href="<c:url value='/'/>">
					<img class="d-inline-block"
					src="${pageContext.request.contextPath}/resources/images/logo.svg" alt="logo" />
				</a> <span class="text-1000 fs-5 fw-bold ms-2 text-gradient"> <a
					class="navbar-brand d-inline-flex" href="<c:url value='/'/>">CookLog</a>
				</span>
			</div>

			<div class="row">
				<div class="col-6 col-md-4 col-lg-3 col-xxl-2 mb-3">
					<h5 class="lh-lg fw-bold text-white">COMPANY</h5>
					<ul class="list-unstyled mb-md-4 mb-lg-0">
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">About Us</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Team</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Careers</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">blog</a></li>
					</ul>
				</div>
				<div class="col-6 col-md-4 col-lg-3 col-xxl-2 col-lg-3 mb-3">
					<h5 class="lh-lg fw-bold text-white">CONTACT</h5>
					<ul class="list-unstyled mb-md-4 mb-lg-0">
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Help &amp; Support</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Partner with us </a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Ride with us</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Ride with us</a></li>
					</ul>
				</div>
				<div class="col-6 col-md-4 col-lg-3 col-xxl-2 mb-3">
					<h5 class="lh-lg fw-bold text-white">LEGAL</h5>
					<ul class="list-unstyled mb-md-4 mb-lg-0">
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Terms &amp; Conditions</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Refund &amp; Cancellation</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Privacy Policy</a></li>
						<li class="lh-lg"><a class="text-200 text-decoration-none"
							href="#!">Cookie Policy</a></li>
					</ul>
				</div>
				<div class="col-6 col-md-4 col-lg-3 col-xxl-2 mb-3 fs-2">
					<h5 class="lh-lg fw-bold text-white">FOLLOW US</h5>
					<div class="text-start my-3 ">
						<a href="#!"><i class="fa-brands fa-instagram text-white me-3"></i></a>
						<a href="#!"><i class="fa-brands fa-facebook text-white me-3"></i></a>
						<a href="#!"><i class="fa-brands fa-twitter text-white me-3"></i></a>
					</div>
				</div>
			</div>
			<hr class="border border-800" />
			<div class="row flex-center pb-3">
				<div class="col-md-6 order-0">
					<p class="text-200 text-center text-md-start">All rights
						Reserved &copy; Your Company, 2025</p>
				</div>
				<div class="col-md-6 order-1">
					<p class="text-200 text-center text-md-end">
						Made with&nbsp; <i class="fa-solid fa-heart link-primary me-1"></i>&nbsp;by&nbsp;<a
							class="text-200 fw-bold" href="http://localhost:8080/CookLog/"
							target="_blank">CookLog </a>
					</p>
				</div>
			</div>
		</div>
	</footer>
	<script src="<c:url value='/resources/js/noticeRolling.js'/>"></script>
</body>
</html>