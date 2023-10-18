package com.spring.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.spring.dao.LoginUserLogDAO;

public class LoginUserLogTaskScheduler {

	private LoginUserLogDAO loginUserLogDAO;
	public void setLoginUserLogDAO(LoginUserLogDAO loginUserLogDAO) {
		this.loginUserLogDAO = loginUserLogDAO;
	}
	
	private String loginUserlogPath;
	public void setLoginUserLogPath(String path) {
		this.loginUserlogPath = path;
	}
	
	private String fileName="login_user_log.csv";
	public void setFileName(String fileName) {
		this.fileName=fileName;
	}
	
	public void loginUserLogToDataBase() throws Exception{
		
		FileReader reader = new FileReader(loginUserlogPath+File.separator+fileName);		
		BufferedReader in=new BufferedReader(reader);
		
		String textLine=null;
		
		loginUserLogDAO.deleteLoginUserLog();
		try {
			while((textLine=in.readLine())!=null) {
				String[] logData =textLine.replace("[login:user]","").split(",");
				
				Map<String,Object> logVO=new HashMap<String,Object>();
				
				logVO.put("id",logData[0]);
				logVO.put("phone",logData[1]);
				logVO.put("email",logData[2]);
				logVO.put("ipAddress",logData[3]);
				logVO.put("indate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
													.parse(logData[4]));
				loginUserLogDAO.insertLoginUserLog(logVO);		
			}
		}finally {
			if(reader!=null) reader.close();
			if(in!=null) in.close();
		}
	}
}






