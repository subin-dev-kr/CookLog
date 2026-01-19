<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 공지사항 -->
<nav class="container my-5">
	<div class="notice-rolling-box my-3">
		<div class="notice-rolling-title">
			<h6>
				<a href="<c:url value='/notice/list'/>">공지사항</a>
			</h6>
		</div>
		<ul class="notice-rolling-list">
			<c:forEach items="${top5List}" var="notice">
				<li><a
					href="<c:url value='/notice/readOne?nNum=${notice.nNum}'/>">${notice.nTitle}</a></li>
			</c:forEach>
		</ul>
	</div>
</nav>