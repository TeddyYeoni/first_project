package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.MemberVO;
import com.domain.MemberVO.MemberGrade;

public class MemberDAO {

	private DataSource dataSource;

	public MemberDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 회원가입
	public void joinMember(MemberVO vo) {
		String query = "insert into universe_mem(mno,id,pwd,name,email) values(mno_seq.nextval,?,?,?,?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 로그인 확인
	public boolean loginConfirm(MemberVO vo) {
		boolean loginResult = false;
		String query = "select decode(count(*),1,'true','false') as loginResult from universe_mem where id=? and pwd=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					loginResult = Boolean.parseBoolean(rs.getString("loginResult"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginResult;
	}

	// 회원등급 조회
	public MemberGrade findMemberGradeById(String id) {
		MemberGrade grade = null;
		String query = "select grade from universe_mem where id=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, id);
			try(ResultSet rs = pstmt.executeQuery();){
				if (rs.next()) {
					grade = MemberGrade.valueOf(rs.getString("grade"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

}
