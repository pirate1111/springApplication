package com.spring.interceptore;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.spring.dto.MemberVO;

public class LoginUserLogInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler)
			throws Exception {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		boolean flag=true;
		
		flag  = id != null
				&& !id.isEmpty()
				&& pwd != null
				&& !pwd.isEmpty();
		
		
		if(!flag) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디 혹은 패스워드를 입력하세요.');");
			out.println("history.go(-1);");
			out.println("</script>");
		}
		
		return flag;
	}

	private String loginUserLogSavePath;
	public void setLoginUserLogSavePath(String path) {
		this.loginUserLogSavePath = path;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		Map<String,Object> model = modelAndView.getModel();
		MemberVO loginUser =(MemberVO)model.get("loginUser");
		
		if(loginUser==null) return;
		
		String saveFileName = "login_user_log.csv";
		//로그인 정보를 스트링으로 저장.
		String tag ="[login:user]";
		String log =tag
					+loginUser.getId()+","					
					+loginUser.getPhone()+","
					+loginUser.getEmail()+","
					+request.getRemoteAddr()+","
					+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		
		File file=new File(loginUserLogSavePath);
		file.mkdirs();
		
		String logFilePath=loginUserLogSavePath+File.separator+saveFileName;	
		BufferedWriter out=new BufferedWriter(new FileWriter(logFilePath,true));


		//로그를 기록
		out.write(log);
		out.newLine();
		
		out.close();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, 
								HttpServletResponse response,
								Object handler, 
								Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	
}
