package com.common;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.dao.board.BoardReplyDAO;
import com.dao.qna.QnaReplyDAO;
import com.service.board.BoardReplyService;
import com.service.qna.QnaReplyService;

@WebListener
public class ServletLoadContextListener implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		BoardReplyDAO boardReplyDAO = new BoardReplyDAO();
		BoardReplyService boardReplyService = new BoardReplyService(boardReplyDAO);
		context.setAttribute("boardReplyService", boardReplyService);
		
		QnaReplyDAO qnaReplyDAO = new QnaReplyDAO();
		QnaReplyService qnaReplyService = new QnaReplyService(qnaReplyDAO);
		context.setAttribute("qnaReplyService", qnaReplyService);
	}
	
}
