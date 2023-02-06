package com.dao.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.PStmtKey;

import com.common.ConnectionUtil;
import com.domain.board.BoardReplyVO;
import com.domain.qna.QnaReplyVO;

public class QnaReplyDAO {

	private DataSource dataSource;

	public QnaReplyDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 댓글 목록
	public List<QnaReplyVO> list(int bno) {
		List<QnaReplyVO> list = new ArrayList<>();
		String query = "select * from qna_reply where qno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, bno);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					QnaReplyVO vo = QnaReplyVO.builder().qno(rs.getInt("qno")).q_rno(rs.getInt("q_rno"))
							.reply(rs.getString("reply")).writer(rs.getString("writer"))
							.replyDate(rs.getDate("replyDate")).modifyDate(rs.getDate("modifyDate")).build();
					list.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 댓글 쓰기
	public void writeReply(QnaReplyVO qnaReplyVO) {
		String first_query = "insert into qna_reply(q_rno, bno, reply, writer) values(qna_reply_seq.nextval,?,?,?)";
		String second_query = "update mou_qna set replycount=replycount+1 where qno=?";
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement first_pstmt = conn.prepareStatement(first_query);
					PreparedStatement second_pstmt = conn.prepareStatement(second_query);) {
				conn.setAutoCommit(false);
				first_pstmt.setInt(1, qnaReplyVO.getQno());
				first_pstmt.setString(2, qnaReplyVO.getReply());
				first_pstmt.setString(3, qnaReplyVO.getWriter());
				first_pstmt.executeUpdate();
				second_pstmt.setInt(1, qnaReplyVO.getQno());
				second_pstmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 댓글 상세
	public QnaReplyVO detail(int q_rno) {
		QnaReplyVO boardReplyVO = null;
		String query = "select * from qna_reply where q_rno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, q_rno);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					boardReplyVO = QnaReplyVO.builder().qno(rs.getInt("qno")).q_rno(rs.getInt("q_rno"))
							.reply(rs.getString("reply")).writer(rs.getString("writer")).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardReplyVO;
	}

	// 댓글 삭제
	public void delete(int q_rno, int qno) {
		String first_query = "delete from qna_reply where q_rno=?";
		String second_query = "update mou_qna set replycount=replycount-1 where qno=?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement first_pstmt = conn.prepareStatement(first_query);
				PreparedStatement second_pstmt = conn.prepareStatement(second_query);) {
			first_pstmt.setInt(1, q_rno);
			first_pstmt.executeUpdate();
			second_pstmt.setInt(1, qno);
			second_pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 댓글 수정
	public void modify(QnaReplyVO boardReplyVO) {
		String query = "update mou_qna ret reply=?, modyfyDate=sysdate where q_rno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, boardReplyVO.getReply());
			pstmt.setInt(2, boardReplyVO.getQ_rno());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
