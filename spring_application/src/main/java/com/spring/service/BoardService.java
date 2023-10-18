package com.spring.service;

import java.sql.SQLException;
import java.util.Map;

import com.jsp.command.SearchListCommand;
import com.spring.dto.BoardVO;

public interface BoardService {
	
	
	// 목록조회	
	Map<String,Object> getBoardList(SearchListCommand command)throws SQLException;
	
	// 상세보기
	BoardVO read(int bno)throws SQLException;	
	BoardVO getBoard(int bno)throws SQLException;
		
	// 등록
	void regist(BoardVO board)throws SQLException;
		
	// 수정
	void modify(BoardVO board)throws SQLException;
	
	// 삭제
	void remove(int bno)throws SQLException;
}
