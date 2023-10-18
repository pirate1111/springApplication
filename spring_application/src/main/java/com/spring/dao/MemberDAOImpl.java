package com.spring.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.jsp.command.SearchListCommand;
import com.spring.dto.MemberVO;

public class MemberDAOImpl implements MemberDAO{

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public List<MemberVO> selectSearchMemberList(SearchListCommand command) throws Exception {
		int offset = command.getStartRowNum();
		int limit = command.getPerPageNum();
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		List<MemberVO> memberList 
			= sqlSession.selectList("Member-Mapper.selectSearchMemberList",command,rowBounds);
		return memberList;
	}

	@Override
	public int selectSearchMemberListCount(SearchListCommand command) throws SQLException {
		int count=0;		
		count=sqlSession.selectOne("Member-Mapper.selectSearchMemberListCount",command);
		return count;
	}

	@Override
	public MemberVO selectMemberById(String id) throws SQLException {
		MemberVO member = sqlSession.selectOne("Member-Mapper.selectMemberById",id);
		return member;
	}
	
	@Override
	public MemberVO selectMemberByPicture(String picture) throws SQLException {
		MemberVO member=null;		
		member = sqlSession.selectOne("Member-Mapper.selectMemberByPicture",picture);		
		return member;
	}

	@Override
	public void insertMember(MemberVO member) throws SQLException {
		sqlSession.update("Member-Mapper.insertMember",member);
	}

	@Override
	public void updateMember(MemberVO member) throws SQLException {
		sqlSession.update("Member-Mapper.updateMember",member);
		
	}

	@Override
	public void deleteMember(String id) throws SQLException {
		sqlSession.update("Member-Mapper.deleteMember",id);	
		
	}

}
