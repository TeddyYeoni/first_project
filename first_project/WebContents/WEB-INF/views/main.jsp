<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>

<style>
.holder {
	min-height: 100vh;
}

body {
	width: auto;
	max-height: 100vh;
	background-image: url('${contextPath}/resources/img/moon.jpg');
	background-size: cover;
	color: silver;
}

.holder a {
	color: white;
}

.recentList {
	background-color: rgba(0, 0, 0, 0.5);
	color: white;
}

.listLink {
	float: right;
	text-decoration: underline;
}
</style>

<!-- Page Content -->
<div class="holder">
	<!-- Portfolio Item Heading -->
	<h1 class="my-4 text-center">News Updates</h1>

	<!-- Portfolio Item Row -->
	<div class="row d-flex justify-content-center">

		<div class="col-md-4 recentList mx-3">
			<div class="text-center my-3">
				<h3 class="sm-b-0">Board📋</h3>
			</div>
			<c:forEach items="${recentBoard}" var="board">
				<ul>
					<li class="d-flex no-block my-3"><i
						class="fa fa-check-circle w-30px m-t-5"></i>
						<div class="title">
							<a href="${contextPath}/board/detail?bno=${board.bno}"
								class="m-b-0 font-medium p-0" data-abc="true">${board.title}</a>
						</div>
						<div class="ml-auto">
							<div class="tetx-right date">
								<h7 class="text-muted m-b-0">${board.writeDate}</h7>
							</div>
						</div></li>
				</ul>
			</c:forEach>
			<div class="listLink my-4">
				<a href="${contextPath}/board">View More</a>
			</div>
		</div>

		<div class="col-md-4 recentList mx-3">
			<div class="text-center my-3">
				<h3 class="sm-b-0">Q&A🖐🏻</h3>
			</div>
			<c:forEach items="${recentQna}" var="qna">
				<ul>
					<li class="d-flex no-block my-3"><i
						class="fa fa-check-circle w-30px m-t-5"></i>
						<div class="title">
							<a href="${contextPath}/qna/detail?qno=${qna.qno}"
								class="m-b-0 font-medium p-0" data-abc="true"><b>${qna.title}</b></a>
						</div>
						<div class="ml-auto">
							<div class="tetx-right">
								<h7 class="text-muted m-b-0">${qna.writeDate}</h7>
							</div>
						</div></li>
				</ul>
			</c:forEach>
			<div class="listLink my-4">
				<a href="${contextPath}/qna">View More</a>
			</div>
		</div>


	</div>
	<!-- /.row -->

	<!-- Related Projects Row -->
	<h3 class="my-4 text-center" style="color: rgba(0, 0, 0, 1);">My
		Space🌍</h3>

	<div class="row d-flex justify-content-center">

		<div class="col-md-3 col-sm-6 mb-4">
			<a href="#"> <img class="img-fluid"
				src="https://via.placeholder.com/500x300" alt="">
			</a>
		</div>

		<div class="col-md-3 col-sm-6 mb-4">
			<a href="#"> <img class="img-fluid"
				src="https://via.placeholder.com/500x300" alt="">
			</a>
		</div>

		<div class="col-md-3 col-sm-6 mb-4">
			<a href="#"> <img class="img-fluid"
				src="https://via.placeholder.com/500x300" alt="">
			</a>
		</div>

	</div>
	<!-- /.row -->

</div>
<!-- /.container -->

<%@ include file="layout/footer.jsp"%>