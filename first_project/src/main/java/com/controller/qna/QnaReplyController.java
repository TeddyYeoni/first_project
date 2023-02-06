package com.controller.qna;

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
import com.domain.qna.QnaReplyVO;
import com.google.gson.Gson;
import com.service.qna.QnaReplyService;

@WebServlet("/qnaReply/*")
public class QnaReplyController extends HttpServlet {

	private QnaReplyService service;
	private Gson gson;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		service = (QnaReplyService) context.getAttribute("qnaReplyService");
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
			String paramQno = request.getParameter("qno");
			int qno = Integer.parseInt(paramQno);
			List<QnaReplyVO> replyList = service.list(qno);
			out.print(gson.toJson(replyList));
		} else if (pathIfno.equals("/write")) {
			String paramQno = request.getParameter("qno");
			int qno = Integer.parseInt(paramQno);
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

			QnaReplyVO qnaReplyVO = QnaReplyVO.builder().qno(qno).reply(reply).writer(writer).build();
			service.write(qnaReplyVO);
			
			String update = gson.toJson("댓글 등록 성공 :)");
			out.print(update);
		}

		else if (pathIfno.equals("/detail")) {
			String paramRno = request.getParameter("q_rno");
			int q_rno = Integer.parseInt(paramRno);
			QnaReplyVO reply = service.deatil(q_rno);

			String reply_detail = gson.toJson(reply);
			out.print(reply_detail);
		}

		else if (pathIfno.equals("/remove")) {
			String paramRno = request.getParameter("q_rno");
			int q_rno = Integer.parseInt(paramRno);
			String paramQno = request.getParameter("qno");
			int qno = Integer.parseInt(paramQno);
			service.delete(q_rno, qno);

			String remove_result = gson.toJson("댓글 삭제 완료 :)");
			out.print(remove_result);
		} else if (pathIfno.equals("/modify")) {
			String paramRno = request.getParameter("q_rno");
			int q_rno = Integer.parseInt(paramRno);
			String reply = request.getParameter("reply");

			QnaReplyVO vo = QnaReplyVO.builder().q_rno(q_rno).reply(reply).build();
			service.modify(vo);
			
			String result = gson.toJson(vo);
			out.print(result);
		}

		else {
			System.out.println("댓글 없음");
		}

	}

}
