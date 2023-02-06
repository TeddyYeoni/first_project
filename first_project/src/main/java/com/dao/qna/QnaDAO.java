package com.dao.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.Criteria;
import com.domain.qna.QnaVO;

public class QnaDAO {

	private DataSource dataSource;

	public QnaDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 글 목록
	public List<QnaVO> questionList(Criteria criteria) {
		String query = "select rownum, qno, title, content, writer, writeDate";
		query += " from (select /*+index_desc(mou_qna pk_qna)*/";
		query += " rownum as rn, qno, title, content, writer, writeDate";
		query += " from mou_qna where rownum <= ?) where rn > ?";
		List<QnaVO> qnaList = new ArrayList<QnaVO>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			int minRow = (criteria.getNowPageNum() - 1) * criteria.getPagingAmount();
			int maxRow = criteria.getNowPageNum() * criteria.getPagingAmount();
			pstmt.setInt(1, maxRow);
			pstmt.setInt(2, minRow);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					QnaVO vo = QnaVO.builder().qno(rs.getInt("qno")).title(rs.getString("title"))
							.content(rs.getString("content")).writer(rs.getString("writer"))
							.writeDate(rs.getDate("writeDate")).build();
					qnaList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qnaList;
	}

	// 글 상세
	public QnaVO questionDetail(int qno) {
		QnaVO vo = null;
		String query = "select * from mou_qna where qno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, qno);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					vo = QnaVO.builder().qno(rs.getInt("qno")).title(rs.getString("title"))
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
	public int getNewQno() {
		int qnaNo = 0;
		String query = "select max(qno) as qnaNo from mou_qna";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				qnaNo = rs.getInt("qnaNo") + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return qnaNo;
	}

	// 글 쓰기
	public int writeQuestion(QnaVO vo) {
		String query = "insert into mou_qna(qno,title,content,writer,imageFileName) values(?,?,?,?,?)";
		int qnaNo = getNewQno();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, qnaNo);
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getWriter());
			pstmt.setString(5, vo.getImageFileName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qnaNo;
	}

	// 글 삭제
	public void deleteQuestion(int qno) {
		String query = "delete from mou_qna where qno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, qno);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTotalCount() {
		String query = "select count(qno) as count from mou_qna";
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
	public List<QnaVO> recentList() {
		String query = "select rownum, qno, title, content, writer, writeDate";
		query += " from (select /*+index_desc(mou_qna pk_qna)*/";
		query += " rownum as rn, qno, title, content, writer, writeDate";
		query += " from mou_qna where rownum <= 5) where rn > 0";
		List<QnaVO> recenQnaList = new ArrayList<QnaVO>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				QnaVO vo = QnaVO.builder().qno(rs.getInt("qno")).title(rs.getString("title"))
						.content(rs.getString("content")).writer(rs.getString("writer"))
						.writeDate(rs.getDate("writeDate")).build();
				recenQnaList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recenQnaList;
	}

}