package com.spring.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;

import com.jsp.dto.NoticeVO;
import com.spring.dto.BoardVO;
import com.spring.dto.PdsVO;

public class ScheduleDAOImpl implements ScheduleDAO{

	private SqlSession session;
	public void setSqlSession(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public NoticeVO selectNoticeByImage(String imageFile) throws SQLException {
		NoticeVO notice = session.selectOne("Notice-Mapper.selectNoticeByImage",imageFile);
		return notice;
	}

	@Override
	public PdsVO selectPdsByImage(String imageFile) throws SQLException {
		PdsVO pds = session.selectOne("Pds-Mapper.selectPdsByImage",imageFile);
		return pds;
	}

	@Override
	public BoardVO selectBoardByImage(String imageFile) throws SQLException {
		BoardVO board = session.selectOne("Board-Mapper.selectBoardByImage",imageFile);
		return board;
	}

}
