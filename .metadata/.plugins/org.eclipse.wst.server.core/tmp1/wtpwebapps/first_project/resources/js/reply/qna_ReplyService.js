let qna_ReplyService = {

	list: function(qno) {
		$.ajax({
			type: 'get',
			url: `${contextPath}/qnaReply/list`,
			data: { qno: qno },
			success: function(replyList) {
				replyListRender(replyList);
			},
			error: function() {
				alert('댓글 목록 조회 불가');
			}
		});
	},

	write: function(qnaReplyVO) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/qnaReply/write`,
			data: qnaReplyVO,
			success: function(update) {
				$('.reply_content').val('');
				$('#feedback').find('.modal-body').html(update);
				$('#feedback').modal('show');

				qna_ReplyService.list(qnaReplyVO.qno);
			},
			error: function() {
				alert('댓글 등록 실패')
			}
		});
	},

	remove: function(q_rno, qno) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/qnaReply/remove`,
			data: {
				q_rno: q_rno,
				qno: qno
			},
			success: function(remove_result) {
				$('#remove_feedback').find('.modal-body').html(remove_result);
				$('#remove_feedback').modal('show');
				qna_ReplyService.list(qno);
			},
			error: function() {
				alert('댓글 삭제 실패!')
			}
		});

	},

	modify: function(qnaReplyVO) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/qnaReply/modify`,
			data: qnaReplyVO,
			success: function(result) {
				alert('댓글이 수정되었습니다☺')
			},
			error: function() {
				alert('댓글 수정 실패!')
			}
		});

	},

	detail: function(q_rno) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/qnaReply/detail`,
			data: { q_rno: q_rno },
			sucess: function(reply_detail) {
				$('.replyList').find('p').html('<input type="text">');
			},
			error: function() {
				alert('댓글 조회 실패')
			}
		});

	}

};

function replyListRender(replyList) {
	let output = '';

	for (let r of replyList) {
		// 모든 사용자
		output += `<li class="list-group-item d-flex justify-content-between">
					<div class="my-2">
						<p>${r.reply}</p>
						<span class="badge badge-pill badge-secondary">${r.replyDate}</span>
						<span class="badge badge-pill badge-light">${r.modifyDate}</span>
						<span class="badge badge-pill badge-info">${auth.grade}</span>
						<span class="badge badge-pill badge-dark">${r.writer}</span>
					</div>`;

		// 글쓴이
		if (r.writer == auth.id) {
			output += `<div class="align-self-content" data-rno="${r.q_rno}">
							<button class="btn btn-sm btn-outline-success reply_modBtn">Edit</button>
							<button class="btn btn-sm btn-outline-danger reply_delBtn">Delete</button>
						</div>`;
		}

		// 관리자
		if (auth.grade == 'ASTRONAUT' && reply.writer != auth.id) {
			output += `<div class="align-self-content" data-rno="${r.q_rno}">
							<button class="btn btn-sm btn-outline-danger reply_delBtn">Delete</button>
						</div>`;
		}

	}

	output += '</li>';
	$('.replyList ul').html(output);

};