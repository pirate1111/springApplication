package com.spring.dao;

import java.sql.SQLException;

import com.jsp.dto.NoticeVO;
import com.spring.dto.BoardVO;
import com.spring.dto.PdsVO;

public interface ScheduleDAO {

	NoticeVO selectNoticeByImage(String imageFile) throws SQLException;
	
	PdsVO selectPdsByImage(String imageFile) throws SQLException;
	
	BoardVO selectBoardByImage(String imageFile) throws SQLException;
}
