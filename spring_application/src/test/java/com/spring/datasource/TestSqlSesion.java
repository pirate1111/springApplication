package com.spring.datasource;

import java.sql.SQLException;
import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsp.dto.NoticeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/spring/context/dataSource-context.xml")
public class TestSqlSesion {
	
	@Autowired
	private SqlSession session;
	
	@Test
	public void testSqlSession() throws SQLException{
		// session을 가져다주므로 open할 필요X
		Collection<String> mapNames = session.getConfiguration().getMappedStatementNames();
		System.out.println(session);
		Assert.assertTrue(mapNames.contains("Notice-Mapper.selectNoticeList"));
	}
	
	@Test
	public void testGetNotice() throws SQLException{
		NoticeVO notice = session.selectOne("Notice-Mapper.selectNoticeByNno", 27);
		// 먼저 진행한 test의 session과 같은 session이 출력됨
		// test마다 session이 달라져야함 -> session close하면 오류 발생
		// scope를 prototype으로 지정 -> 이제 close를 신경 쓸 필요 없음
		System.out.println(session);
		Assert.assertNotNull(notice);
		Assert.assertEquals("mama", notice.getWriter());
	}
}
