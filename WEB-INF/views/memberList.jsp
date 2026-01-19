<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/theme.css'/>" rel="stylesheet">
<title>전체회원조회</title>
</head>
<body>
	<!-- header -->
	<jsp:include page="/WEB-INF/views/header.jsp" />

	<!-- 공지사항 -->
	<jsp:include page="/WEB-INF/views/noticeRolling.jsp" />

	<main class="main" id="top">

		<section class="pt-4 pb-5">
			<div class="container">
				<div class="card shadow-sm p-4">
					<h4 class="card-title mb-4">회원 리스트</h4>
					<!-- 회원 검색 -->
					<form class="d-flex mb-4 align-items-center"
						action="<c:url value='/member/readAll'/>" method="get">
						<div class="me-3">
							<select class="form-select" name="memberSearchType"
								style="width: 130px" aria-label="검색 조건">
								<option selected value="mId">아이디</option>
								<option value="mNickName">닉네임</option>
								<option value="email">이메일</option>
								<option value="phone">전화번호</option>
							</select>
						</div>

						<input class="form-control me-2 flex-grow-1" type="text"
							name="memberSearchKeyword" placeholder="검색어를 입력하세요"> <input
							class="btn btn-warning" type="submit" value="조회" />
					</form>
					<!-- 회원 리스트 -->
					<div class="table-responsive">
						<table class="table table-striped table-hover">
							<thead>
								<tr>
									<th scope="col">고유번호</th>
									<th scope="col">아이디</th>
									<th scope="col">닉네임</th>
									<th scope="col">이메일</th>
									<th scope="col">전화번호</th>
									<th scope="col">권한</th>
									<th scope="col">레시피 관리</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list}" var="member">
									<tr>
										<td>${member.mNum}</td>
										<td>${member.mId}</td>
										<td>${member.mNickName}</td>
										<td>${member.email}</td>
										<td>${member.phone}</td>
										<td>${member.mRole}</td>
										<td>
						                    <a href="<c:url value='/recipe/myList'>
						                                <c:param name='keyword' value='${member.mId}'/>
						                            </c:url>" 
						                       class="btn btn-primary btn-sm">
						                        레시피 보기
						                    </a>
						                </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<!-- 페이징 -->
					<nav aria-label="member Page navigation">
						<ul class="pagination justify-content-center">

							<!-- 이전 페이지 -->
							<li
								class="page-item ${paging.currentPage == 1 ? 'disabled' : ''}">
								<c:choose>
									<c:when test="${paging.currentPage == 1}">
										<span class="page-link">&laquo;</span>
									</c:when>
									<c:otherwise>
										<a class="page-link"
											href="<c:url value='/member/readAll'>
									                <c:param name='page' value='${paging.currentPage - 1}'/>
									                <c:if test='${not empty memberSearchType}'>
									                    <c:param name='memberSearchType' value='${memberSearchType}'/>
									                </c:if>
									                <c:if test='${not empty memberSearchKeyword}'>
									                    <c:param name='memberSearchKeyword' value='${memberSearchKeyword}'/>
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
									href="<c:url value='/member/readAll'>
									            <c:param name='page' value='${pageNum}'/>
									            <c:if test='${not empty memberSearchType}'>
									                <c:param name='memberSearchType' value='${memberSearchType}'/>
									            </c:if>
									            <c:if test='${not empty memberSearchKeyword}'>
									                <c:param name='memberSearchKeyword' value='${memberSearchKeyword}'/>
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
											href="<c:url value='/member/readAll'>
									                <c:param name='page' value='${paging.currentPage + 1}'/>
									                <c:if test='${not empty memberSearchType}'>
									                    <c:param name='memberSearchType' value='${memberSearchType}'/>
									                </c:if>
									                <c:if test='${not empty memberSearchKeyword}'>
									                    <c:param name='memberSearchKeyword' value='${memberSearchKeyword}'/>
									                </c:if>
									            </c:url>">
											&raquo; </a>
									</c:otherwise>
								</c:choose>
							</li>
						</ul>
					</nav>

				</div>
			</div>
		</section>
	</main>
	<!--footer -->
	<jsp:include page="/WEB-INF/views/footer.jsp" />
</body>
</html>