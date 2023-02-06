package com.service.board;

import java.util.List;

import com.dao.board.BoardReplyDAO;
import com.domain.board.BoardReplyVO;

public class BoardReplyService {

	private BoardReplyDAO boardReplyDAO;

	public BoardReplyService(BoardReplyDAO dao) {
		this.boardReplyDAO = dao;
	}

	public List<BoardReplyVO> list(int bno) {
		return boardReplyDAO.list(bno);
	}
	
	public void write(BoardReplyVO boardReplyVO) {
		boardReplyDAO.writeReply(boardReplyVO);
	}
	
	public BoardReplyVO deatil(int b_rno) {
		return boardReplyDAO.detail(b_rno);
	}
	
	public void delete(int b_rno, int bno) {
		boardReplyDAO.delete(b_rno, bno);
	}
	
	public void modify(BoardReplyVO boardReplyVO) {
		boardReplyDAO.modify(boardReplyVO);
	}

}
