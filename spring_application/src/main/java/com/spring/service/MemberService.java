package com.spring.service;

import java.sql.SQLException;
import java.util.Map;

import com.jsp.command.SearchListCommand;
import com.spring.dto.MemberVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIdException;

public interface MemberService {
	
	public void login(String id, String pwd) throws NotFoundIdException, 
													InvalidPasswordException,
													SQLException;
	// 회원리스트
	public Map<String,Object> getMemberList(SearchListCommand command)
													throws Exception;
	
	// 회원상세
	public MemberVO getMember(String id) throws Exception;

	// 회원등록
	public void regist(MemberVO member) throws Exception;

	// 회원수정
	public void modify(MemberVO member) throws Exception;

	// 회원탈퇴
	public void remove(String id) throws Exception;
}
