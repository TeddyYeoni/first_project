package com.dao.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.PStmtKey;

import com.common.ConnectionUtil;
import com.domain.board.BoardReplyVO;

public class BoardReplyDAO {

	private DataSource dataSource;

	public BoardReplyDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 댓글 목록
	public List<BoardReplyVO> list(int bno) {
		List<BoardReplyVO> list = new ArrayList<>();
		String query = "select * from board_reply where bno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, bno);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					BoardReplyVO vo = BoardReplyVO.builder().bno(rs.getInt("bno")).b_rno(rs.getInt("b_rno"))
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
	public void writeReply(BoardReplyVO vo) {
		String first_query = "insert into board_reply(b_rno, bno, reply, writer) values(seq_board_reply.nextval,?,?,?)";
		String second_query = "update mou_board set replycount=replycount+1 where bno=?";
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement first_pstmt = conn.prepareStatement(first_query);
					PreparedStatement second_pstmt = conn.prepareStatement(second_query);) {
				conn.setAutoCommit(false);
				first_pstmt.setInt(1, vo.getBno());
				first_pstmt.setString(2, vo.getReply());
				first_pstmt.setString(3, vo.getWriter());
				first_pstmt.executeUpdate();
				second_pstmt.setInt(1, vo.getBno());
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
	public BoardReplyVO detail(int b_rno) {
		BoardReplyVO boardReplyVO = null;
		String query = "select * from board_reply where b_rno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, b_rno);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					boardReplyVO = BoardReplyVO.builder().bno(rs.getInt("bno")).b_rno(rs.getInt("b_rno"))
							.reply(rs.getString("reply")).writer(rs.getString("writer")).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardReplyVO;
	}

	// 댓글 삭제
	public void delete(int b_rno, int bno) {
		String first_query = "delete from board_reply where b_rno=?";
		String second_query = "update mou_board set replycount=replycount-1 where bno=?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement first_pstmt = conn.prepareStatement(first_query);
				PreparedStatement second_pstmt = conn.prepareStatement(second_query);) {
			first_pstmt.setInt(1, b_rno);
			first_pstmt.executeUpdate();
			second_pstmt.setInt(1, bno);
			second_pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 댓글 수정
	public void modify(BoardReplyVO boardReplyVO) {
		String query = "update mou_board ret reply=?, modyfyDate=sysdate where b_rno=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, boardReplyVO.getReply());
			pstmt.setInt(2, boardReplyVO.getB_rno());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
