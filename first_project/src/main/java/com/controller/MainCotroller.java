package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.board.BoardDAO;
import com.dao.qna.QnaDAO;
import com.domain.Criteria;
import com.domain.Pagination;
import com.domain.board.BoardVO;
import com.domain.qna.QnaVO;
import com.service.board.BoardService;
import com.service.qna.QnaService;

@WebServlet("/main")
public class MainCotroller extends HttpServlet {

	BoardService boardService;
	QnaService qnaService;

	@Override
	public void init() throws ServletException {
		BoardDAO boardDAO = new BoardDAO();
		QnaDAO qnaDAO = new QnaDAO();
		boardService = new BoardService(boardDAO);
		qnaService = new QnaService(qnaDAO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String contextPath = request.getContextPath();
		final String PREFIX = "/WEB-INF/views/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/main")) {
			List<BoardVO> boardList = boardService.recentList();
			request.setAttribute("recentBoard", boardList);
			
			List<QnaVO> qnaList = qnaService.recentList();
			request.setAttribute("recentQna", qnaList);
			
			nextPage = "main";
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}