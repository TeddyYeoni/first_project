package com.service.qna;

import java.util.List;

import com.dao.qna.QnaDAO;
import com.domain.Criteria;
import com.domain.qna.QnaVO;

public class QnaService {

	private QnaDAO qnaDAO;

	public QnaService(QnaDAO qnaDAO) {
		this.qnaDAO = qnaDAO;
	}

	public List<QnaVO> questionList(Criteria criteria) {
		return qnaDAO.questionList(criteria);
	}

	public QnaVO detailQuestion(int qno) {
		return qnaDAO.questionDetail(qno);
	}

	public int addQuestion(QnaVO vo) {
		return qnaDAO.writeQuestion(vo);
	}

	public void removeQuestion(int qno) {
		qnaDAO.deleteQuestion(qno);
	}

	public int totalCount() {
		return qnaDAO.getTotalCount();
	}
	
	public List<QnaVO> recentList() {
		return qnaDAO.recentList();
	}

}
