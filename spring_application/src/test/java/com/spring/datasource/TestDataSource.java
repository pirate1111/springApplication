package com.spring.datasource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsp.dto.NoticeVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/spring/context/dataSource-context.xml")
public class TestDataSource {

	@Autowired
	//@Resource(name = "dataSource")
	private DataSource dataSource;
	
	private Connection conn;
	
	@Before
	public void init()throws SQLException {
		conn=dataSource.getConnection();
	}
	
	@After
	public void destroy()throws SQLException{
		conn.close();
	}
	
	@Test
	public void testSQL()throws Exception {
		Assert.assertNotNull(conn);
		
		Statement stmt=conn.createStatement();
		
		String sql="select * from notice where nno=27";
		
		ResultSet rs=stmt.executeQuery(sql);
		
		NoticeVO notice = null;
		if(rs.next()) {
			notice = new NoticeVO();
			
			notice.setNno(rs.getInt("nno"));
			notice.setWriter(rs.getString("writer"));
		}
		
		Assert.assertNotNull(notice);
		
		Assert.assertEquals("mama", notice.getWriter());
	}
}







