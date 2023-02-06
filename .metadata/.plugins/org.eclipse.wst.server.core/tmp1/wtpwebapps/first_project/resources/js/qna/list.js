$(function() {
	let listForm = $('#listForm');
	$('.title').on('click', function(e) {
		e.preventDefault();
		$('[name="qno"]').remove();
		let qnoData = "<input type='hidden' name='qno' value='" + $(this).attr('href') + "'/>";
		listForm.append(qnoData)
			.attr("action", `${contextPath}/qna/detail`)
			.submit();
	});
});