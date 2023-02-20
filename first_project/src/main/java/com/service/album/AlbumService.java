package com.service.album;

import java.util.List;

import com.dao.album.AlbumDAO;
import com.domain.Criteria;
import com.domain.album.AlbumVO;

public class AlbumService {

	private AlbumDAO albumDAO;

	public AlbumService(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
	}

	public List<AlbumVO> photoList(Criteria criteria) {
		return albumDAO.lookUpList(criteria);
	}
	
	public AlbumVO photoDetail(int ano) {
		return albumDAO.photoDetail(ano);
	}
	
	public int addPhoto(AlbumVO vo) {
		return albumDAO.uploadPhoto(vo);
	}
	
	public void removePhoto(int ano) {
		albumDAO.deletePhoto(ano);
	}

	public int totalCount() {
		return albumDAO.getTotalCount();
	}
	
}
