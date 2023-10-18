package com.spring.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchListCommand;
import com.spring.dao.MemberDAO;
import com.spring.dto.MemberVO;
import com.spring.exception.InvalidPasswordException;
import com.spring.exception.NotFoundIdException;

public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDAO;

	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public void login(String id, String pwd) throws NotFoundIdException, InvalidPasswordException, SQLException {
		MemberVO member = memberDAO.selectMemberById(id);
		if (member == null)
			throw new NotFoundIdException();
		if (!pwd.equals(member.getPwd()))
			throw new InvalidPasswordException();

	}

	@Override
	public Map<String, Object> getMemberList(SearchListCommand command) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		List<MemberVO> memberList = memberDAO.selectSearchMemberList(command);
		dataMap.put("memberList", memberList);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCommand(command);
		pageMaker.setTotalCount(memberDAO.selectSearchMemberListCount(command));
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public MemberVO getMember(String id) throws Exception {

		MemberVO member = memberDAO.selectMemberById(id);
		return member;

	}

	@Override
	public void regist(MemberVO member) throws Exception {

		memberDAO.insertMember(member);

	}

	@Override
	public void modify(MemberVO member) throws Exception {

		memberDAO.updateMember(member);

	}

	@Override
	public void remove(String id) throws Exception {

		memberDAO.deleteMember(id);

	}

}
