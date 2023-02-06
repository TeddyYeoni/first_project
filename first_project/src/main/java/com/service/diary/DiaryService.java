package com.service.diary;

import java.util.List;

import com.dao.diary.DiaryDAO;
import com.domain.diary.DiaryVO;

public class DiaryService {

	private DiaryDAO diaryDAO;

	public DiaryService(DiaryDAO diaryDAO) {
		this.diaryDAO = diaryDAO;
	}

	public List<DiaryVO> diaryList() {
		return diaryDAO.lookupList();
	}

	public int newDiary(DiaryVO diaryVO) {
		return diaryDAO.writeDiary(diaryVO);
	}

	public DiaryVO diaryDetail(int dno) {
		return diaryDAO.diaryDetail(dno);
	}

	public void removeDiary(int dno) {
		diaryDAO.deleteDiary(dno);
	}

}
