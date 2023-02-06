<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container my-5">
	<div class="text-center my-5">
		<h1>Today's To Do List✏</h1>
	</div>

	<form action="${contextPath}/todo/newCheckList" method="post"
		enctype="multipart/form-data">
		<div class="form-group">
			<b>Date</b>&nbsp;&nbsp;&nbsp;<b>${todayDate}</b>
		</div>
		<div class="form-group">
			<b>Check List✔</b><br>
			<textarea rows="10" class="form-control" name="content"></textarea>
		</div>
		<div class="text-center">
			<a href="${contextPath}/todo" class="btn btn-outline-dark">Back</a>
			<button class="btn btn-outline-primary">Update</button>
		</div>
	</form>
	<div class="preview"></div>
</div>

<%@include file="../layout/footer.jsp"%>
