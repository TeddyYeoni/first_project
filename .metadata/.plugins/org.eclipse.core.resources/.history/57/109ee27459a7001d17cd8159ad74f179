$(function() {
	$('.viewMode').hide();

	let viewForm = $('#viewForm');
	let titleObj = $('input[name="title"]');
	let contentObj = $('textarea[name="content"]');
	let imageFile = $('.originFileName').val();
	let pTag = $('.preview p').html();

	let originImg = $('.originImg').clone();
	let titleVal = titleObj.val();
	let contentVal = contentObj.val();

	// Back 버튼
	$('.toList').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/board`,
			"method": "get"
		}).empty()
			.submit();
	});

	// Edit 버튼
	$('.toModForm').on('click', function() {
		$('input[name="title"],textarea[name="content"]').attr("readonly", false);
		$('.viewMode').show();
		$(this).closest('tr').hide();
	});

	// Delete 버튼
	$('.remove').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/board/delete`,
			"method": "post"
		}).submit();

	});

	// Edit 처리
	$('.modify').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/board/modify`,
			"method": "post"
		}).submit();
	});

	// 뷰 모드
	$('.backViewMode').on('click', function() {
		$('input[name="title"],textarea[name="content"]').attr("readonly", true);
		$('.viewMode').hide();
		$(this).closest('tr').prev().show();
		$('.preview').html(originImg);
		$('input[type="file"]').val();
		titleObj.val(titleVal);
		contentObj.val(contentVal);
		console.log(imageFile);
		if (imageFile == '' || imageFile == null) {
			$('.preview').html(pTag);
		}
	});

});

// 댓글

$(function() {
	let bno = $('input[name="bno"]').val();

	board_ReplyService.list(bno);

	// 댓글 등록 기능
	$('.reply_write').on('click', function() {
		let reply = $('.reply_content').val();
		let writer = $('.reply_writer').val();

		let boardReplyVO = {
			bno: bno,
			reply: reply,
			writer: writer
		}

		board_ReplyService.write(boardReplyVO);
	});

	// 댓글 삭제 기능
	$('.replyList').on('click', '.reply_delBtn', function() {
		let b_rno = $(this).closest('div').data('b_rno');
		board_ReplyService.remove(b_rno, bno);
	});


});
