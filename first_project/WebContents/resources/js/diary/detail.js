$(function() {
	$('.viewMode').hide();

	let viewForm = $('#viewForm');
	let contentObj = $('textarea[name="content"]');

	let contentVal = contentObj.val();

	// Back 버튼
	$('.toList').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/diary`,
			"method": "get"
		}).empty()
			.submit();
	});

	// Edit 버튼
	$('.toModForm').on('click', function() {
		$('textarea[name="content"]').attr("readonly", false);
		$('input[name="praise"]').attr("readonly", false);
		$('input[name="thanks"]').attr("readonly", false);
		$('.viewMode').show();
		$(this).closest('tr').hide();
	});

	// Delete 버튼
	$('.remove').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/diary/delete`,
			"method": "post"
		}).submit();

	});

	// Edit 처리
	$('.modify').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/diary/modify`,
			"method": "post"
		}).submit();
	});

	// 뷰 모드
	$('.backViewMode').on('click', function() {
		$('textarea[name="content"]').attr("readonly", true);
		$('.viewMode').hide();
		$(this).closest('tr').prev().show();
		contentObj.val(contentVal);
	});

});

