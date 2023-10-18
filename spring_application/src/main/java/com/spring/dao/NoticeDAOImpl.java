package com.spring.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchListCommand;
import com.jsp.dao.NoticeDAO;
import com.jsp.dto.NoticeVO;

public class NoticeDAOImpl implements NoticeDAO{
	
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	@Override
	public void deleteNotice(int nno) throws SQLException {
		sqlSession.delete("Notice-Mapper.deleteNotice",nno);
		
	}

	@Override
	public void increaseViewCount(int nno) throws SQLException {
		sqlSession.update("Notice-Mapper.increaseViewCount",nno);
		
	}

	@Override
	public void insertNotice(NoticeVO notice) throws SQLException {
		sqlSession.insert("Notice-Mapper.insertNotice",notice);
		
	}

	@Override
	public NoticeVO selectNoticeByNno(int nno) throws SQLException {
		NoticeVO notice = sqlSession.selectOne("Notice-Mapper.selectNoticeByNno",nno);
		return notice;
	}

	@Override
	public List<NoticeVO> selectNoticeList() throws SQLException {
		return null;
	}

	@Override
	public List<NoticeVO> selectNoticeList(SearchListCommand command) throws SQLException {
		int startRow = command.getStartRowNum();
		int endRow = startRow+command.getPerPageNum();
		
		Map<String, Object> dataParam = new HashMap<String, Object>();
		dataParam.put("startRow", startRow);
		dataParam.put("endRow", endRow);
		dataParam.put("searchType", command.getSearchType());
		dataParam.put("keyword", command.getKeyword());

		List<NoticeVO> noticeList 
			= sqlSession.selectList("Notice-Mapper.selectSearchNoticeList", dataParam);
		return noticeList;
	
	}

	@Override
	public int selectNoticeSequenceNextValue() throws SQLException {
		int nno = sqlSession.selectOne("Notice-Mapper.selectNoticeSequenceNextValue");
		return nno;
	}

	@Override
	public int selectSearchNoticeListCount(SearchListCommand command) throws SQLException {
		int count = sqlSession.selectOne("Notice-Mapper.selectSearchNoticeListCount",command);
		return count;
	}

	@Override
	public void updateNotice(NoticeVO notice) throws SQLException {
		sqlSession.update("Notice-Mapper.updateNotice",notice);
		
	}

}
