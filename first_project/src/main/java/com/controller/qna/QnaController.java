package com.controller.qna;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.FileUpload;
import com.dao.qna.QnaDAO;
import com.domain.Criteria;
import com.domain.Pagination;
import com.domain.qna.QnaVO;
import com.service.qna.QnaService;

@WebServlet("/qna/*")
public class QnaController extends HttpServlet {

	private QnaService service;
	private FileUpload multiReq;

	@Override
	public void init() throws ServletException {
		QnaDAO dao = new QnaDAO();
		service = new QnaService(dao);
		multiReq = new FileUpload("qna/");
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
		final String PREFIX = "/WEB-INF/views/qna/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			Criteria criteria = new Criteria(1,10);
			String paramPageNum = request.getParameter("nowPageNum");
			if (paramPageNum != null) {
				criteria.setNowPageNum(Integer.parseInt(paramPageNum));
			}
			List<QnaVO> qnaList = service.questionList(criteria);
			int totalCount = service.totalCount();
			Pagination pagination = new Pagination(criteria, totalCount);

			request.setAttribute("qna_list", qnaList);
			request.setAttribute("page", pagination);
			nextPage = "list";
		} else if (pathInfo.equals("/detail")) {
			String paramQno = request.getParameter("qno");
			int qno = Integer.parseInt(paramQno);
			QnaVO qna = service.detailQuestion(qno);
			request.setAttribute("qna", qna);
			nextPage = "detail";
		} else if (pathInfo.equals("/writeForm")) {
			nextPage = "writeForm";
		} else if (pathInfo.equals("/newPost")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String imageFileName = req.get("imageFileName");
			QnaVO vo = QnaVO.builder().title(req.get("title")).content(req.get("content")).writer(req.get("writer"))
					.imageFileName(req.get("imageFileName")).build();
			int qnaNo = service.addQuestion(vo);

			if (imageFileName != null && imageFileName.length() > 0) {
				multiReq.imageUpload(qnaNo, imageFileName);
			}
			response.sendRedirect(contextPath + "/qna");
			return;
		} else if (pathInfo.equals("/delete")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String paramQno = req.get("qno");
			int qno = Integer.parseInt(paramQno);
			service.removeQuestion(qno);
			multiReq.deleteAllImage(qno);
			response.sendRedirect(contextPath + "/qna");
			return;
		} else {
			System.out.println("잘못된 페이지입니다.");
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}
