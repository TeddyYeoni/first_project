let board_ReplyService = {

	list: function(bno) {
		$.ajax({
			type: 'get',
			url: `${contextPath}/boardReply/list`,
			data: { bno: bno },
			success: function(replyList) {
				replyListRender(replyList);
			},
			error: function() {
				alert('댓글 목록 조회 불가');
			}
		});
	},

	write: function(boardReplyVO) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/boardReply/write`,
			data: boardReplyVO,
			success: function(update) {
				$('.reply_content').val('');
				$('#feedback').find('.modal-body').html(update);
				$('#feedback').modal('show');

				board_ReplyService.list(boardReplyVO.bno);
			},
			error: function() {
				alert('댓글 등록 실패')
			}
		});
	},

	remove: function(b_rno, bno) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/boardReply/remove`,
			data: {
				b_rno: b_rno,
				bno: bno
			},
			success: function(remove_result) {
				$('#remove_feedback').find('.modal-body').html(remove_result);
				$('#remove_feedback').modal('show');
				board_ReplyService.list(bno);
			},
			error: function() {
				alert('댓글 삭제 실패!')
			}
		});

	},

	modify: function(boardReplyVO) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/boardReply/modify`,
			data: boardReplyVO,
			success: function(result) {
				alert('댓글이 수정되었습니다☺')
			},
			error: function() {
				alert('댓글 수정 실패!')
			}
		});

	},

	detail: function(b_rno) {
		$.ajax({
			type: 'post',
			url: `${contextPath}/boardReply/detail`,
			data: { b_rno: b_rno },
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
			output += `<div class="align-self-content" data-rno="${r.b_rno}">
							<button class="btn btn-sm btn-outline-success reply_modBtn">Edit</button>
							<button class="btn btn-sm btn-outline-danger reply_delBtn">Delete</button>
						</div>`;
		}

		// 관리자
		if (auth.grade == 'ASTRONAUT' && reply.writer != auth.id) {
			output += `<div class="align-self-content" data-rno="${r.b_rno}">
							<button class="btn btn-sm btn-outline-danger reply_delBtn">Delete</button>
						</div>`;
		}

	}

	output += '</li>';
	$('.replyList ul').html(output);

};