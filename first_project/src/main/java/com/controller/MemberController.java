package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.MemberDAO;
import com.domain.AuthVO;
import com.domain.MemberVO;
import com.domain.MemberVO.MemberGrade;
import com.service.MemberService;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {

	private MemberService service;

	@Override
	public void init() throws ServletException {
		service = new MemberService(new MemberDAO());
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
		final String PREFIX = "/WEB-INF/views/member/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		// 회원가입 페이지
		if (pathInfo.equals("/joinForm")) {
			nextPage = "joinForm";
		}
		// 회원가입 처리
		else if (pathInfo.equals("/join")) {
			String id = request.getParameter("id");
			String pwd = (String) request.getAttribute("pwd");
			System.out.println(pwd);
			String name = request.getParameter("name");
			String email = request.getParameter("email");

			MemberVO vo = MemberVO.builder().id(id).pwd(pwd).name(name).email(email).build();
			service.joinUniverse(vo);
			response.sendRedirect(contextPath + "/main");
			return;
		}
		// 로그인 페이지
		else if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/loginForm")) {
			nextPage = "loginForm";
		}
		// 로그인 처리
		else if (pathInfo.equals("/login")) {
			String id = request.getParameter("id");
			String pwd = (String) request.getAttribute("pwd");
			MemberVO vo = MemberVO.builder().id(id).pwd(pwd).build();

			if (service.loginService(vo)) {
				HttpSession session = request.getSession();

				MemberGrade grade = service.getMemberGrade(vo.getId());
				AuthVO authVO = new AuthVO();
				authVO.setId(vo.getId());
				authVO.setGrade(grade);
				session.setAttribute("auth", authVO);

				String userUri = (String) session.getAttribute("userUri");
				if (userUri != null) {
					session.removeAttribute(userUri);
					response.sendRedirect(userUri);
					return;
				}
				response.sendRedirect(contextPath + "/main");
				return;
			} else {
				System.out.println("MemberController.login : 아이디 또는 비밀번호가 잘못되었습니다.");
				return;
			}
		}
		// 로그아웃 처리
		else if (pathInfo.equals("/logout")) {
			HttpSession session = request.getSession(false);
			session.removeAttribute("auth");
			response.sendRedirect(contextPath + "/member/loginForm");
			return;

		} else {
			System.out.println("페이지를 찾을 수 없습니다.");
			return;
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}