package com.service.board;

import java.util.List;

import com.dao.board.BoardDAO;
import com.domain.Criteria;
import com.domain.board.BoardVO;

public class BoardService {

	private BoardDAO boardDAO;

	public BoardService(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	public List<BoardVO> boardList(Criteria criteria) {
		return boardDAO.lookUpList(criteria);
	}
	
	public BoardVO boardDetail(int bno) {
		return boardDAO.Contentdetail(bno);
	}
	
	public int addContent(BoardVO vo) {
		return boardDAO.writeContent(vo);
	}
	
	public void removeContent(int bno) {
		boardDAO.deleteContet(bno);
	}
	
	public void updateContent(BoardVO vo) {
		boardDAO.modifyContent(vo);
	}
	
	public int totalCount() {
		return boardDAO.getTotalCount();
	}
	
	public List<BoardVO> recentList() {
		return boardDAO.recentList();
	}

}
