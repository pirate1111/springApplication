package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchListCommand;
import com.spring.dto.MemberVO;

public interface MemberDAO {

	// 검색결과일반 리스트
	List<MemberVO> selectSearchMemberList(SearchListCommand command) throws Exception;

	// 검색 결과의 전체 리스트 개수
	int selectSearchMemberListCount(SearchListCommand command) throws SQLException;

	// 회원정보 조회
	MemberVO selectMemberById(String id) throws SQLException;
	MemberVO selectMemberByPicture(String picture) throws SQLException;
	
	// 회원 추가
	public void insertMember(MemberVO member) throws SQLException;

	// 회원 수정
	public void updateMember(MemberVO member) throws SQLException;

	// 회원정보 삭제
	void deleteMember(String id) throws SQLException;
}
