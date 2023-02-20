<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<c:set var="auth" value="${sessionScope.auth}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
	const contextPath = "${contextPath}";
	let auth = {
		id : "${auth.id}",
		grade : "${auth.grade}"
	};
</script>
<script src="${contextPath}/resources/js/common.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/css/style.css">
</head>
<style>
body {
	width: auto;
	max-height: 100vh;
	background-image: url('${contextPath}/resources/img/moon.jpg');
	background-size: cover;
	color: silver;
	font-family:'DungGeunMo';
}
</style>
<body>
	<div class="d-flex justify-content-center">
		<nav class="navbar navbar-expand-sm my-2">
			<!-- Brand/logo -->
			<a class="navbar-brand font-weight-bold text-light mx-5 "
				href="${contextPath}/main">
				<h1>MY ONLY UNIVERSE🚀</h1>
			</a>
		</nav>
	</div>