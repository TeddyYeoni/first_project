<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<script src="${contextPath}/resources/js/album/detail.js"></script>

<div class="container">
	<div class="jumbotron">
		<h1>Album</h1>
	</div>
	<form id="viewForm" enctype="multipart/form-data">
		<table class="table">
			<tr>
				<th><b>No</b></th>
				<td>${album.ano}<input type="hidden" name="ano"
					value="${album.ano}"></td>
			</tr>
			<tr>
				<th>Date</th>
				<td>${album.uploadDate}</td>
			</tr>
			<tr>
				<th><b>Title</b></th>
				<td colspan="3"><input type="text" name="title"
					class="form-control" value="${album.title}" readonly="readonly"></td>
			</tr>
			<tr>
				<th><b>Contents</b></th>
				<td colspan="3"><textarea rows="5" cols="50" name="content"
						class="form-control" readonly="readonly">${album.content}</textarea></td>
			</tr>
			<tr>
				<div>
					<th><b>Images</b></th>
					<td colspan="3"><input type="file" name="imageFileName"
						class="form-control viewMode">
						<div class="imageLayout my-3">
							<input type="hidden" name="originFileName" class="originFileName"
								value="${album.imageFileName}">
							<div class="preview">
								<img class="originImg"
									src="${contextPath}/fileDownload?no=${album.ano}&imageFileName=${album.imageFileName}&path=album">
							</div>
						</div></td>
			</tr>
			<tr>
				<td colspan="4" class="text-right">
					<button type="button" class="btn btn-outline-dark toList">
						<b>Back</b>
					</button>
					<button type="button" class="btn btn-outline-success toModForm">
						<b>Edit</b>
					</button>
					<button type="button" class="btn btn-outline-danger remove">
						<b>Delete</b>
					</button>
				</td>
			</tr>

			<tr class="viewMode">
				<td colspan="4" class="text-right">
					<button type="button" class="btn btn-outline-primary modify">
						<b>EDIT</b>
					</button>
					<button type="button" class="btn btn-outline-primary backViewMode">
						<b>BACK</b>
					</button>
				</td>
			</tr>
		</table>
	</form>

</div>


<%@ include file="../layout/footer.jsp"%>