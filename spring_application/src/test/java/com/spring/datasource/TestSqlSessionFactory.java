package com.spring.datasource;

import java.sql.SQLException;
import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jsp.dto.NoticeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/spring/context/dataSource-context.xml")
@Transactional
public class TestSqlSessionFactory {

	@Autowired
	private SqlSessionFactory factory;
	
	private SqlSession session;
	
	@Before
	public void init()throws Exception{
		session = factory.openSession();
	}

	@After
	public void close()throws Exception{
		 if(session !=null) session.close();
	}
	
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
	
	@Test
	@Rollback
	public void testInsertNotice()throws SQLException{
		NoticeVO notice = new NoticeVO();
		notice.setContent("content");
		notice.setWriter("mimi");
		notice.setTitle("title");
		notice.setNno(52859);
		
		session.insert("Notice-Mapper.insertNotice",notice);
		
		
	}
}







