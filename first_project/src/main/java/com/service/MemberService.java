package com.service;

import com.dao.MemberDAO;
import com.domain.MemberVO;
import com.domain.MemberVO.MemberGrade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberService {

	private MemberDAO memberDAO;
	
	// 회원가입
	public void joinUniverse(MemberVO vo) {
		memberDAO.joinMember(vo);
	}
	
	// 로그인 확인
	public boolean loginService(MemberVO vo) {
		return memberDAO.loginConfirm(vo);
	}
	
	// 회원등급 조회
	public MemberGrade getMemberGrade(String id) {
		return memberDAO.findMemberGradeById(id);
	}
}

