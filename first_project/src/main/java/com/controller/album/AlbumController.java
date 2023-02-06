package com.controller.album;

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
import com.dao.album.AlbumDAO;
import com.domain.album.AlbumVO;
import com.service.album.AlbumService;

@WebServlet("/album/*")
public class AlbumController extends HttpServlet {

	private AlbumService service;
	private FileUpload multiReq;

	@Override
	public void init() throws ServletException {
		AlbumDAO dao = new AlbumDAO();
		service = new AlbumService(dao);
		multiReq = new FileUpload("album/");
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
		final String PREFIX = "/WEB-INF/views/album/";
		final String SUFFIX = ".jsp";

		RequestDispatcher rd = null;
		String nextPage = null;

		// 앨범 리스트
		if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
			List<AlbumVO> photoList = service.photoList();
			request.setAttribute("album_list", photoList);
			nextPage = "list";
		}
		// 앨범 상세
		else if (pathInfo.equals("/detail")) {
			String paramAno = request.getParameter("ano");
			int ano = Integer.parseInt(paramAno);
			AlbumVO album = service.photoDetail(ano);
			request.setAttribute("album", album);
			nextPage = "detail";
		}
		// 앨범 업로드 폼
		else if (pathInfo.equals("/uploadForm")) {
			nextPage = "uploadForm";
		}
		// 사진 업로드
		else if (pathInfo.equals("/upload")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String imageFileName = req.get("imageFileName");
			AlbumVO vo = AlbumVO.builder().title(req.get("title")).content(req.get("content"))
					.imageFileName(req.get("imageFileName")).build();
			int photoNO = service.addPhoto(vo);
			if (imageFileName != null && imageFileName.length() > 0) {
				multiReq.imageUpload(photoNO, imageFileName);
			}
			response.sendRedirect(contextPath + "/album");
			return;
		}
		// 앨범 수정
		else if (pathInfo.equals("/modify")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String paramAno = req.get("ano");
			int ano = Integer.parseInt(paramAno);
			String title = req.get("title");
			String content = req.get("content");
			String imageFileName = req.get("imageFileName");

			AlbumVO vo = AlbumVO.builder().ano(ano).title(title).content(content).imageFileName(imageFileName).build();
			service.addPhoto(vo);

			if (imageFileName != null) {
				String originFileName = req.get("originFileName");
				multiReq.imageUpload(ano, imageFileName);
				if (originFileName != null) {
					multiReq.originImageDelete(ano, originFileName);
				}
			}
			response.sendRedirect(contextPath + "/album");
			return;
		}
		// 사진 삭제
		else if (pathInfo.equals("/delete")) {
			Map<String, String> req = multiReq.getMutipartRequest(request);
			String paramAno = req.get("ano");
			int ano = Integer.parseInt(paramAno);
			service.removePhoto(ano);
			multiReq.deleteAllImage(ano);
			response.sendRedirect(contextPath + "/album");
			return;
		} else {
			System.out.println("잘못된 페이지입니다.");
		}

		rd = request.getRequestDispatcher(PREFIX + nextPage + SUFFIX);
		rd.forward(request, response);
	}

}