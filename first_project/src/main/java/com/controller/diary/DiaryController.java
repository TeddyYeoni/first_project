package com.controller.diary;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.diary.DiaryDAO;
import com.domain.diary.DiaryVO;
import com.service.diary.DiaryService;

/**
 * Servlet implementation class DiaryController
 */
@WebServlet("/diary/*")
public class DiaryController extends HttpServlet {

	private DiaryService service;

	@Override
	public void init() throws ServletException {
		DiaryDAO dao = new DiaryDAO();
		service = new DiaryService(dao);
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
		final String PREFIX = "/WEB-INF/views/diary/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		// 다이어리 목록
		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			List<DiaryVO> diaryList = service.diaryList();
			request.setAttribute("diary_list", diaryList);
			nextPage = "list";
		}
		// 다이어리 작성
		else if (pathInfo.equals("/writeForm")) {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd EEEEE");
			String todayDate = dateFormat.format(date);
			request.setAttribute("todayDate", todayDate);
			nextPage = "writeForm";
		}
		// 다이어리 추가
		else if (pathInfo.equals("/newDiary")) {
			DiaryVO vo = DiaryVO.builder().content(request.getParameter("content"))
					.weather(request.getParameter("weather"))
					.emotion(request.getParameter("emotion")).praise(request.getParameter("praise"))
					.thanks(request.getParameter("thanks")).build();
			service.newDiary(vo);
			response.sendRedirect(contextPath + "/diary");
			return;
		}
		// 다이어리 상세
		else if (pathInfo.equals("/detail")) {
			int paramDno = Integer.parseInt(request.getParameter("dno"));
			DiaryVO diary = service.diaryDetail(paramDno);
			request.setAttribute("diary", diary);
			nextPage = "detail";
		} else {
			System.out.println("잘못된 페이지입니다.");
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}