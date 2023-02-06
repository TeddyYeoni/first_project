<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<div class="jumbotron">
		<h1>Diary</h1>
	</div>

	<div class="holder d-flex align-items-center justify-content-center">
		<div class="container">
			<!-- 체크리스트 -->
			<div class="row">
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
									checked> <label class="form-check-label"
									for="flexCheck1"><span class="fst-italic pl-1">Buy
										a new sweatshirt</span></label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>


	<%@ include file="../layout/footer.jsp"%>