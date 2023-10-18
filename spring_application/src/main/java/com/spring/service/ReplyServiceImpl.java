package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchListCommand;
import com.spring.dao.ReplyDAO;
import com.spring.dto.ReplyVO;

public class ReplyServiceImpl implements ReplyService {

	private ReplyDAO replyDAO;
	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}

	@Override
	public Map<String, Object> getReplyList(int bno, SearchListCommand command) throws SQLException {

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<ReplyVO> replyList = replyDAO.selectReplyListPage(bno,command);
		
		
		int count = replyDAO.countReply(bno);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCommand(command);
		pageMaker.setTotalCount(count);

		dataMap.put("replyList", replyList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;

	}

	@Override
	public int getReplyListCount(int bno) throws SQLException {
		int count = replyDAO.countReply(bno);
		return count;
	}

	@Override
	public void registReply(ReplyVO reply) throws SQLException {
		int rno = replyDAO.selectReplySeqNextValue();
		reply.setRno(rno);
		replyDAO.insertReply(reply);

	}

	@Override
	public void modifyReply(ReplyVO reply) throws SQLException {

		replyDAO.updateReply(reply);
	}

	@Override
	public void removeReply(int rno) throws SQLException {

		replyDAO.deleteReply(rno);

	}
}
