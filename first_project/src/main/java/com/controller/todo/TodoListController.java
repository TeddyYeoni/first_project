package com.controller.todo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/todo/*")
public class TodoListController extends HttpServlet {
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandler(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandler(request, response);
	}

	protected void doHandler(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String pathInfo = request.getPathInfo();
		String contextPath = request.getContextPath();
		final String PREFIX = "/WEB-INF/views/todo/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/todo")) {
			nextPage = "list";
		}
		// 체크리스트 작성 폼
		else if (pathInfo.equals("/addForm")) {

			nextPage = "addForm";
		}
		// 체크리스트 추가
		else if (pathInfo.equals("/newCheckList")) {

			response.sendRedirect(contextPath + "/todo");
		}
		// 체크리스트 확인
		else if (pathInfo.equals("/confirm")) {

		}
		// 체크리스트 수정
		else if (pathInfo.equals("/modify")) {

			response.sendRedirect(contextPath + "/todo");
		}

		else {
			System.out.println("잘못된 페이지입니다.");
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}