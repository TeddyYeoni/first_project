<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<div class="jumbotron">
		<h1>Diary</h1>
	</div>
	<form id="viewForm" enctype="multipart/form-data">
		<input type="hidden" name="bno" value="${diary.bno}">
		<table class="table">
			<tr>
				<th><b>Date</b></th>
				<td colspan="3">${diary.writeDate}</td>
			</tr>
			<tr>
				<th><b>Weather</b></th>
				<td>${diary.todayWeather}</td>
				<th>Emotion</th>
				<td>${diary.todayEmotion}</td>
			</tr>
			<tr>
				<th><b>Diary</b></th>
				<td colspan="3"><textarea rows="5" cols="50" name="content"
						class="form-control" readonly="readonly">${diary.content}</textarea></td>
			</tr>
			<tr>
				<td colspan="4" class="text-right">
					<button type="button" class="btn btn-outline-dark toList">
						<b>Back</b>
					</button> <c:if test="${auth.id eq board.writer}">
						<button type="button" class="btn btn-outline-success toModForm">
							<b>Edit</b>
						</button>
					</c:if> <c:if
						test="${auth.id eq board.writer or auth.grade eq 'ASTRONAUT'}">
						<button type="button" class="btn btn-outline-danger remove">
							<b>Delete</b>
						</button>
					</c:if>
				</td>
			</tr>

			<tr class="viewMode">
				<c:if test="${auth.id eq board.writer or auth.grade eq 'ASTRONAUT'}">
					<td colspan="4" class="text-right">
						<button type="button" class="btn btn-outline-primary modify">
							<b>EDIT</b>
						</button>
						<button type="button" class="btn btn-outline-primary backViewMode">
							<b>BACK</b>
						</button>
					</td>
				</c:if>
			</tr>
		</table>
	</form>



</div>


<%@ include file="../layout/footer.jsp"%>