package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchListCommand;
import com.jsp.dao.NoticeDAO;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

public class NoticeServiceImpl implements NoticeService{

	private NoticeDAO noticeDAO;
	public void setNoticeDAO(NoticeDAO noticeDAO) {
		this.noticeDAO = noticeDAO;
	}
	
	@Override
	public Map<String, Object> getNoticeList() throws SQLException {
		List<NoticeVO> noticeList = noticeDAO.selectNoticeList();
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("noticeList", noticeList);
		return dataMap;
	}

	@Override
	public NoticeVO getNotice(int nno) throws SQLException {
		NoticeVO board = noticeDAO.selectNoticeByNno(nno);
		return board;
	}

	@Override
	public NoticeVO read(int nno) throws SQLException {
		NoticeVO board = noticeDAO.selectNoticeByNno( nno);
		noticeDAO.increaseViewCount(nno);
		return board;
	}

	@Override
	public void regist(NoticeVO notice) throws SQLException {
		int nno = noticeDAO.selectNoticeSequenceNextValue();
		notice.setNno(nno);
		noticeDAO.insertNotice(notice);
	}

	@Override
	public void modify(NoticeVO notice) throws SQLException {
		noticeDAO.updateNotice(notice);
	}

	@Override
	public void remove(int nno) throws SQLException {
		noticeDAO.deleteNotice(nno);
	}

	@Override
	public Map<String, Object> getNoticeList(SearchListCommand command) throws SQLException {
		List<NoticeVO> noticeList = noticeDAO.selectNoticeList(command);
		
		// 전체 board 개수
		int totalCount = noticeDAO.selectSearchNoticeListCount(command);

		// PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCommand(command);
		pageMaker.setTotalCount(totalCount);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("noticeList", noticeList);
		dataMap.put("pageMaker", pageMaker);
		
		return dataMap;
	}
	
}
