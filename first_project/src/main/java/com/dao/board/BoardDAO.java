package com.dao.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.Criteria;
import com.domain.board.BoardVO;

import oracle.jdbc.proxy.annotation.Pre;
import oracle.net.aso.d;
import oracle.net.aso.p;

public class BoardDAO {

	private DataSource dataSource;

	public BoardDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 글 목록
	public List<BoardVO> lookUpList(Criteria criteria) {
		String query = "SELECT ROWNUM, BNO, TITLE, CONTENT, WRITER, WRITEDATE";
		query += " FROM (SELECT /*+INDEX_DESC(mou_board PK_BOARD)*/";
		query += " ROWNUM AS RN, BNO, TITLE, CONTENT, WRITER, WRITEDATE";
		query += " FROM mou_board WHERE ROWNUM <= ?) WHERE RN > ?";
		List<BoardVO> boardList = new ArrayList<>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			int minRow = (criteria.getNowPageNum() - 1) * criteria.getPagingAmount();
			int maxRow = criteria.getNowPageNum() * criteria.getPagingAmount();
			pstmt.setInt(1, maxRow);
			pstmt.setInt(2, minRow);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					BoardVO vo = BoardVO.builder().bno(rs.getInt("bno")).title(rs.getString("title"))
							.content(rs.getString("content")).writer(rs.getString("writer"))
							.writeDate(rs.getDate("writeDate")).build();
					boardList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardList;
	}

	// 글 상세
	public BoardVO Contentdetail(int bno) {
		BoardVO vo = null;
		String query = "select * from mou_board where bno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, bno);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					vo = BoardVO.builder().bno(rs.getInt("bno")).title(rs.getString("title"))
							.content(rs.getString("content")).writer(rs.getString("writer"))
							.imageFileName(rs.getString("imageFileName")).writeDate(rs.getDate("writeDate")).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 글 번호 생성
	public int getNewBno() {
		int boardNo = 0;
		String query = "select max(bno) as boardNO from mou_board";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				boardNo = rs.getInt("boardNO") + 1;
				if (boardNo == 0) {
					boardNo = 1;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNo;
	}

	// 글 쓰기
	public int writeContent(BoardVO vo) {
		String query = "insert into mou_board(bno,title,content,writer,imageFileName) values(?,?,?,?,?)";
		int boardNo = getNewBno();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, boardNo);
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			pstmt.setString(5, vo.getImageFileName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardNo;
	}

	// 글 수정
	public void modifyContent(BoardVO vo) {
		String imageFileName = vo.getImageFileName();
		int bno = vo.getBno();

		String query = "update mou_board set title=?,content=?";

		if (imageFileName != null) {
			query += ",imageFileName=? where bno=?";
		} else {
			query += " where bno=?";
		}

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());

			if (imageFileName != null) {
				pstmt.setString(3, vo.getImageFileName());
				pstmt.setInt(4, bno);
			} else {
				pstmt.setInt(3, vo.getBno());
			}

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 글 삭제
	public void deleteContet(int bno) {
		String query = "delete from mou_board where bno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, bno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 총 게시물 수
	public int getTotalCount() {
		String query = "select count(bno) as count from mou_board";
		int totalCount = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				totalCount = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalCount;
	}

	// 최근 게시물
	public List<BoardVO> recentList() {
		String query = "SELECT ROWNUM, BNO, TITLE, CONTENT, WRITER, WRITEDATE";
		query += " FROM (SELECT /*+INDEX_DESC(mou_board PK_BOARD)*/";
		query += " ROWNUM AS RN, BNO, TITLE, CONTENT, WRITER, WRITEDATE";
		query += " FROM mou_board WHERE ROWNUM <= 5) WHERE RN > 0";
		List<BoardVO> recentList = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				BoardVO vo = BoardVO.builder().bno(rs.getInt("bno")).title(rs.getString("title"))
						.content(rs.getString("content")).writer(rs.getString("writer"))
						.writeDate(rs.getDate("writeDate")).build();
				recentList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recentList;
	}

}
