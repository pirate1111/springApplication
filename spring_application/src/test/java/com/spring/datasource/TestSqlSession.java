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
public class TestSqlSession {

	@Autowired
	private SqlSession session;
	
	
	
	@Test
	public void testSqlSession()throws SQLException{
		Collection<String> mapNames=session.getConfiguration()
								   .getMappedStatementNames();
		
		System.out.println(session);
		
		Assert.assertTrue(mapNames.contains("Notice-Mapper.selectNoticeList"));
	}
	
	@Test
	public void testGetNotice()throws SQLException{
		NoticeVO notice = 
				session.selectOne("Notice-Mapper.selectNoticeByNno",27);
		
		System.out.println(session);
		
		Assert.assertNotNull(notice);
		Assert.assertEquals("mama", notice.getWriter());
	}
	
}



