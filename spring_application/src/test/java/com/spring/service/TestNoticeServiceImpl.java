package com.spring.service;

import java.sql.SQLException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchListCommand;
import com.jsp.service.NoticeService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:com/spring/context/root-context.xml")
public class TestNoticeServiceImpl {

	
	@Autowired
	private NoticeService noticeService;
	
	
	@Test
	public void testSelectNoticeList()throws SQLException{
		
		SearchListCommand command = new SearchListCommand();
		command.setKeyword("mama");
		command.setSearchType("w");
		
		Map<String,Object> dataMap = noticeService.getNoticeList(command);
		
		PageMaker pageMaker = (PageMaker)dataMap.get("pageMaker");
		
		int totalCount = pageMaker.getTotalCount();
		
		Assert.assertEquals(10240, totalCount);
	}
	
}









