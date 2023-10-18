package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchListCommand;
import com.spring.dto.BoardVO;

public interface BoardDAO {
	
	List<BoardVO> selectSearchBoardList( SearchListCommand command) throws SQLException;
	
	int selectSearchBoardListCount(SearchListCommand command) throws SQLException;
	
	
	BoardVO selectBoardByBno ( int bno) throws SQLException;
	
	BoardVO selectBoardByImage ( String imageFile) throws SQLException;
	
	void insertBoard( BoardVO board) throws SQLException;
	
	void updateBoard( BoardVO board) throws SQLException;
	
	void deleteBoard( int bno) throws SQLException;

	
	void increaseViewCnt( int bno) throws SQLException;
	
	int selectBoardSeqNext() throws SQLException;
}
