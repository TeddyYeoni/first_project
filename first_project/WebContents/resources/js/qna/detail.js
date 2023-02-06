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
			"action": `${contextPath}/qna`,
			"method": "get"
		}).empty()
			.submit();
	});

	// Delete 버튼
	$('.remove').on('click', function() {
		viewForm.attr({
			"action": `${contextPath}/qna/delete`,
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
		if (imageFile == '' || imageFile == null) {
			$('.preview').html(pTag);
		}
	});

});

// 댓글

$(function() {
	let qno = $('input[name="qno"]').val();

	qna_ReplyService.list(qno);

	// 댓글 등록 기능
	$('.reply_write').on('click', function() {
		let reply = $('.reply_content').val();
		let writer = $('.reply_writer').val();

		let qnaReplyVO = {
			qno: qno,
			reply: reply,
			writer: writer
		}

		qna_ReplyService.write(qnaReplyVO);
	});

	// 댓글 삭제 기능
	$('.replyList').on('click', '.reply_delBtn', function() {
		let q_rno = $(this).closest('div').data('q_rno');
		qna_ReplyService.remove(q_rno, qno);
	});


});
