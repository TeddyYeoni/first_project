<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<style>
.holder {
	min-height: 100vh;
}

.rounded {
	border-radius: 1rem !important;
}

.text-gray {
	color: #aaa;
}

input:checked+label {
	text-decoration: line-through;
	color: #adb5bd;
}

label {
	cursor: pointer;
}

a {
	text-decoration: none;
}
</style>

<div class="container text-center my-3">
	<h2>To Do List</h2>
	<p>오늘 해야할 일 정리하기🎈</p>
</div>


<div class="holder container">
	<div>
		<a href="${contextPath}/todo/addForm" class="btn btn-outline-info">new</a>
	</div>
	<!-- 체크리스트 -->
	<div class="row my-3">
		<div class="col-lg-5 mx-auto">

			<!-- CHECKBOX LIST -->
			<div class="card rounded border-0 shadow-sm position-relative">
				<div class="card-body p-5">
					<div class="d-flex align-items-center mb-4 pb-4 border-bottom">
						<i class="far fa-calendar-alt fa-3x"></i>
						<div class="ms-3">
							<h4 class="text-uppercase fw-weight-bold mb-0">Wednesday</h4>
							<p class="text-gray fst-italic mb-0">05 December 2020</p>
						</div>
					</div>
					<div class="form-check mb-3">
						<input class="form-check-input" id="flexCheck1" type="checkbox"
							checked> <label class="form-check-label" for="flexCheck1"><span
							class="fst-italic pl-1">Buy a new sweatshirt</span></label>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>


<%@ include file="../layout/footer.jsp"%>