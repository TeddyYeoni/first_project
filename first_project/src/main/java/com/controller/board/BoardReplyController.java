package com.controller.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.domain.board.BoardReplyVO;
import com.google.gson.Gson;
import com.service.board.BoardReplyService;

@WebServlet("/boardReply/*")
public class BoardReplyController extends HttpServlet {

	private BoardReplyService service;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		service = (BoardReplyService) context.getAttribute("boardReplyService");
		gson = new Gson();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String pathIfno = request.getPathInfo();
		String contextPath = request.getContextPath();

		PrintWriter out = response.getWriter();

		if (pathIfno.equals("/list")) {
			String paramBno = request.getParameter("bno");
			int bno = Integer.parseInt(paramBno);
			List<BoardReplyVO> replyList = service.list(bno);
			out.print(gson.toJson(replyList));
		} else if (pathIfno.equals("/write")) {
			String paramBno = request.getParameter("bno");
			int bno = Integer.parseInt(paramBno);
			String reply = request.getParameter("reply");
			String writer = request.getParameter("writer");

			long currentTime = System.currentTimeMillis();
			HttpSession session = request.getSession(false);
			if (session.getAttribute("lastWriting") != null) {
				Long lastWriting = (Long) session.getAttribute("lastWriting");
				if (currentTime - lastWriting < 10000) {
					out.print(gson.toJson("도배 금지"));
					return;
				}
			}
			session.setAttribute("lastWriting", currentTime);

			BoardReplyVO boardReplyVO = BoardReplyVO.builder().bno(bno).reply(reply).writer(writer).build();
			service.write(boardReplyVO);
			
			String update = gson.toJson("댓글 등록 성공 :)");
			out.print(update);
		}

		else if (pathIfno.equals("/detail")) {
			String paramRno = request.getParameter("b_rno");
			int b_rno = Integer.parseInt(paramRno);
			BoardReplyVO reply = service.deatil(b_rno);

			String reply_detail = gson.toJson(reply);
			out.print(reply_detail);
		}

		else if (pathIfno.equals("/remove")) {
			String paramRno = request.getParameter("b_rno");
			int b_rno = Integer.parseInt(paramRno);
			String paramBno = request.getParameter("bno");
			int bno = Integer.parseInt(paramBno);
			service.delete(b_rno, bno);

			String remove_result = gson.toJson("댓글 삭제 완료 :)");
			out.print(remove_result);
		} else if (pathIfno.equals("/modify")) {
			String paramRno = request.getParameter("b_rno");
			int b_rno = Integer.parseInt(paramRno);
			String reply = request.getParameter("reply");

			BoardReplyVO vo = BoardReplyVO.builder().b_rno(b_rno).reply(reply).build();
			service.modify(vo);
			
			String result = gson.toJson(vo);
			out.print(result);
		}

		else {
			System.out.println("댓글 없음");
		}

	}

}
