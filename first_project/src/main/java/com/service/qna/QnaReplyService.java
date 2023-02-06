package com.service.qna;

import java.util.List;

import com.dao.qna.QnaReplyDAO;
import com.domain.qna.QnaReplyVO;

public class QnaReplyService {

	private QnaReplyDAO qnaReplyDAO;

	public QnaReplyService(QnaReplyDAO dao) {
		this.qnaReplyDAO = dao;
	}

	public List<QnaReplyVO> list(int bno) {
		return qnaReplyDAO.list(bno);
	}
	
	public void write(QnaReplyVO qnaReplyVO) {
		qnaReplyDAO.writeReply(qnaReplyVO);
	}
	
	public QnaReplyVO deatil(int q_rno) {
		return qnaReplyDAO.detail(q_rno);
	}
	
	public void delete(int q_rno, int qno) {
		qnaReplyDAO.delete(q_rno, qno);
	}
	
	public void modify(QnaReplyVO boardReplyVO) {
		qnaReplyDAO.modify(boardReplyVO);
	}

}
