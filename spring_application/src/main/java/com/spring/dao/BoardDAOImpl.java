package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchListCommand;
import com.spring.dto.BoardVO;

public class BoardDAOImpl implements BoardDAO{
	
	private SqlSession session;
	public void setSqlSession(SqlSession session) {
		this.session = session;
	}
	
	@Override
	public List<BoardVO> selectSearchBoardList(SearchListCommand command) throws SQLException {
		
		int offset=command.getStartRowNum();
		int limit=command.getPerPageNum();		
		RowBounds rowBounds=new RowBounds(offset,limit);		
		
		List<BoardVO> boardList=
				session.selectList("Board-Mapper.selectSearchBoardList",command,rowBounds);
		
		return boardList;
	}

	@Override
	public int selectSearchBoardListCount(SearchListCommand command) throws SQLException {
		int count=session.selectOne("Board-Mapper.selectSearchBoardListCount",command);
		return count;
	}

	@Override
	public BoardVO selectBoardByBno(int bno) throws SQLException {
		BoardVO board=session.selectOne("Board-Mapper.selectBoardByBno",bno);
		return board;
	}

	@Override
	public void insertBoard(BoardVO board) throws SQLException {
		session.update("Board-Mapper.insertBoard",board);
	}

	@Override
	public void updateBoard(BoardVO board) throws SQLException {
		session.update("Board-Mapper.updateBoard",board);
	}

	@Override
	public void deleteBoard(int bno) throws SQLException {
		session.update("Board-Mapper.deleteBoard",bno);
	}

	@Override
	public void increaseViewCnt(int bno) throws SQLException {
		session.update("Board-Mapper.increaseViewCnt",bno);
	}

	@Override
	public int selectBoardSeqNext() throws SQLException {
		int seq_num=session.selectOne("Board-Mapper.selectBoardSeqNext");
		return seq_num;
	}

	@Override
	public BoardVO selectBoardByImage(String imageFile) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
