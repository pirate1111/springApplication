package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchListCommand;
import com.spring.dto.PdsVO;

public interface PdsDAO {
	

	List<PdsVO> selectSearchPdsList(SearchListCommand command) throws SQLException;
	int selectSearchPdsListCount(SearchListCommand command) throws SQLException;
	
	PdsVO selectPdsByPno(int pno)throws SQLException;
	
	
	void insertPds(PdsVO pds)throws SQLException;
	void updatePds(PdsVO pds)throws SQLException;
	void deletePds(int pno)throws SQLException;
	
	
	//pds_seq.nextval 가져오기
	int selectSeqNextValue() throws SQLException;
	//viewcnt  증가
	void increaseViewCnt(int pno)throws SQLException;
}
