package com.dao.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.diary.DiaryVO;

public class DiaryDAO {

	private DataSource dataSource;

	public DiaryDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	public List<DiaryVO> lookupList() {
		String query = "select * from mou_diary order by dno desc";
		List<DiaryVO> diaryList = new ArrayList<DiaryVO>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				DiaryVO vo = DiaryVO.builder().dno(rs.getInt("dno")).content(rs.getString("content"))
						.writeDate(rs.getDate("writeDate")).weather(rs.getString("weather"))
						.emotion(rs.getString("emotion")).praise(rs.getString("praise"))
						.thanks(rs.getString("thanks")).build();
				diaryList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diaryList;
	}

	public int writeDiary(DiaryVO vo) {
		String query = "insert into mou_diary(dno,content,weather,emotion,praise,thanks) values(?,?,?,?,?,?)";
		int diaryNO = getNewDno();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, diaryNO);
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getWeather());
			pstmt.setString(4, vo.getEmotion());
			pstmt.setString(5, vo.getPraise());
			pstmt.setString(6, vo.getThanks());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diaryNO;
	}

	public DiaryVO diaryDetail(int dno) {

		return null;
	}

	public int getNewDno() {
		int diaryNO = 0;
		String query = "select max(dno) as diaryNO from mou_diary";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				diaryNO = rs.getInt("diaryNO") + 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return diaryNO;
	}

	public void deleteDiary(int dno) {

	}

}