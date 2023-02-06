package com.dao.album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.common.ConnectionUtil;
import com.domain.album.AlbumVO;

public class AlbumDAO {

	private DataSource dataSource;

	public AlbumDAO() {
		dataSource = ConnectionUtil.getDataSource();
	}

	// 사진 목록
	public List<AlbumVO> lookUpList() {
		String query = "select * from mou_album order by ano desc";
		List<AlbumVO> albumList = new ArrayList<AlbumVO>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			while (rs.next()) {
				AlbumVO vo = AlbumVO.builder().ano(rs.getInt("ano")).title(rs.getString("title"))
						.content(rs.getString("content")).uploadDate(rs.getDate("uploadDate"))
						.imageFileName(rs.getString("imageFileName")).build();
				albumList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return albumList;
	}

	// 앨범 상세
	public AlbumVO photoDetail(int ano) {
		AlbumVO vo = null;
		String query = "select * from mou_album where ano=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, ano);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					vo = AlbumVO.builder().ano(rs.getInt("ano")).title(rs.getString("title"))
							.content(rs.getString("content")).imageFileName(rs.getString("imageFileName"))
							.uploadDate(rs.getDate("uploadDate")).build();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 사진 번호 생성
	public int getNewAno() {
		int photoNo = 0;
		String query = "select max(ano) as photoNO from mou_album";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			if (rs.next()) {
				photoNo = rs.getInt("photoNO") + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoNo;
	}

	// 사진 업로드
	public int uploadPhoto(AlbumVO vo) {
		String query = "insert into mou_album(ano,title,content,imageFileName) values(?,?,?,?)";
		int albumNO = getNewAno();
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, albumNO);
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setString(4, vo.getImageFileName());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return albumNO;
	}

	// 내용 수정
	public void modifyAlbum(AlbumVO vo) {
		int ano = vo.getAno();
		String query = "update mou_album set title=?,content=?,imageFileName=? where ano=?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getImageFileName());
			pstmt.setInt(4, ano);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 사진 삭제
	public void deletePhoto(int ano) {
		String query = "delete from mou_album where ano=?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setInt(1, ano);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
