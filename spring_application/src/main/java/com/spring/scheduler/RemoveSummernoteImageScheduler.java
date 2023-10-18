package com.spring.scheduler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spring.dao.ScheduleDAO;

public class RemoveSummernoteImageScheduler {

	private ScheduleDAO scheduleDAO;
	public void setScheduleDAO(ScheduleDAO scheduleDAO) {
		this.scheduleDAO = scheduleDAO;
	}
	
	private String filePath;
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(RemoveSummernoteImageScheduler.class);
	
	public void fileRemove() throws Exception {
	      File dir = new File(filePath);
	      File[] files = dir.listFiles();
	      
	      if (files != null) {
	         for (File file : files) {
	            boolean existFile = false;
	            
	            existFile = (scheduleDAO.selectNoticeByImage(file.getName()) != null)
	                  || (scheduleDAO.selectBoardByImage(file.getName()) != null)
	                  || (scheduleDAO.selectPdsByImage(file.getName()) != null);
	            
	            if (existFile) {
	               logger.info(file.getName() + "은 사용하는 파일입니다.");
	            }
	            else {
	               logger.info(file.getName() + "은 사용하지 않습니다.");
	               if (file.exists()) file.delete();
	            }
	         }
	      }
	   }
}
