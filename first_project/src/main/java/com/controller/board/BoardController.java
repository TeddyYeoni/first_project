package com.controller.board;

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
import com.dao.board.BoardDAO;
import com.domain.Criteria;
import com.domain.Pagination;
import com.domain.board.BoardVO;
import com.service.board.BoardService;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {

	private BoardService service;
	private FileUpload multiReq;

	@Override
	public void init() throws ServletException {
		BoardDAO dao = new BoardDAO();
		service = new BoardService(dao);
		multiReq = new FileUpload("board/");
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
		final String PREFIX = "/WEB-INF/views/board/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			Criteria criteria = new Criteria();
			String paramPageNum = request.getParameter("nowPageNum");
			if (paramPageNum != null) {
				criteria.setNowPageNum(Integer.parseInt(paramPageNum));
			}
			List<BoardVO> boardList = service.boardList(criteria);
			int totalCount = service.totalCount();
			
			Pagination pagination = new Pagination(criteria, totalCount);
			
			request.setAttribute("board_list", boardList);
			request.setAttribute("page", pagination);
			
			nextPage = "list";
		} else if (pathInfo.equals("/detail")) {
			String paramBno = request.getParameter("bno");
			int bno = Integer.parseInt(paramBno);
			BoardVO board = service.boardDetail(bno);
			request.setAttribute("board", board);
			nextPage = "detail";
		} else if (pathInfo.equals("/writeForm")) {
			nextPage = "writeForm";
		} else if (pathInfo.equals("/newPost")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String imageFileName = req.get("imageFileName");
			BoardVO vo = BoardVO.builder().title(req.get("title")).content(req.get("content")).writer(req.get("writer"))
					.imageFileName(req.get("imageFileName")).build();
			int boardNo = service.addContent(vo);

			if (imageFileName != null && imageFileName.length() > 0) {
				multiReq.imageUpload(boardNo, imageFileName);
			}
			response.sendRedirect(contextPath + "/board");
			return;
		} else if (pathInfo.equals("/modify")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String paramBno = req.get("bno");
			int bno = Integer.parseInt(paramBno);
			String title = req.get("title");
			String content = req.get("content");
			String imageFileName = req.get("imageFileName");

			BoardVO vo = BoardVO.builder().bno(bno).title(title).content(content).imageFileName(imageFileName).build();
			service.updateContent(vo);
			
			if(imageFileName != null) {
				String originFileName = req.get("originFileName");
				multiReq.imageUpload(bno, imageFileName);
				if(originFileName != null) {
					multiReq.originImageDelete(bno, originFileName);
				}
			}
			response.sendRedirect(contextPath + "/board");
			return;
		}

		else if (pathInfo.equals("/delete")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String paramBno = req.get("bno");
			int bno = Integer.parseInt(paramBno);
			service.removeContent(bno);
			multiReq.deleteAllImage(bno);
			response.sendRedirect(contextPath + "/board");
			return;
		} else {
			System.out.println("잘못된 페이지입니다.");
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}
