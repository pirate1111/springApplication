package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchListCommand;
import com.spring.dao.BoardDAO;
import com.spring.dao.ReplyDAO;
import com.spring.dto.BoardVO;

public class BoardServiceImpl implements BoardService {
	
	private BoardDAO boardDAO;
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	
	private ReplyDAO replyDAO;
	public void setReplyDAO(ReplyDAO replyDAO) {
		this.replyDAO = replyDAO;
	}
	
	
	@Override
	public BoardVO read(int bno) throws SQLException {
		BoardVO board = boardDAO.selectBoardByBno(bno);
		boardDAO.increaseViewCnt(bno);
		return board;
	}

	@Override
	public BoardVO getBoard(int bno) throws SQLException {
		BoardVO board = boardDAO.selectBoardByBno(bno);
		return board;
	}

	@Override
	public Map<String, Object> getBoardList(SearchListCommand commnad) throws SQLException {

		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<BoardVO> boardList = boardDAO.selectSearchBoardList(commnad);
		// reply count 입력
		for (BoardVO board : boardList) {
			int replycnt = replyDAO.countReply(board.getBno());
			board.setReplycnt(replycnt);
		}
		
		// 전체 board 개수
		int totalCount = boardDAO.selectSearchBoardListCount(commnad);

		// PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCommand(commnad);
		pageMaker.setTotalCount(totalCount);

		dataMap.put("boardList", boardList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public void regist(BoardVO board) throws SQLException {

		int bno = boardDAO.selectBoardSeqNext();
		board.setBno(bno);
		boardDAO.insertBoard(board);
	}

	@Override
	public void modify(BoardVO board) throws SQLException {

		boardDAO.updateBoard(board);
	}

	@Override
	public void remove(int bno) throws SQLException {

		boardDAO.deleteBoard(bno);
	}
}
