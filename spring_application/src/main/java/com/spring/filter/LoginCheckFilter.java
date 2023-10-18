package com.spring.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.spring.dto.MemberVO;



public class LoginCheckFilter implements Filter {
	
	private List<String> exURLs = new ArrayList<String>();
	
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		// 제외할 url 확인
		String reqUrl = httpReq.getRequestURI()
				.substring(httpReq.getContextPath().length());
		
		if (excludeCheck(reqUrl)) {
			chain.doFilter(request, response);
			return;
		}
	
		// login check
		HttpSession session = httpReq.getSession();
		String loginUser = (String) session.getAttribute("loginUser");
		
		//login 확인
		if(loginUser!=null) {
			chain.doFilter(request, response);
			return;
		}
		
		//비로그인 loginForm으로 
		
		String retUrl = reqUrl;
		String queryString = httpReq.getQueryString();
		if(queryString!=null) {
			retUrl+="?"+queryString;
		}
		
		String url = httpReq.getContextPath()
					 +"/common/loginForm.do?error=-1&retUrl="+retUrl;
		httpResp.sendRedirect(url);
		
		
	}
	public void init(FilterConfig fConfig) throws ServletException {
		exURLs.add("/resources");
		exURLs.add("/common/login");
		exURLs.add("/common/loginForm");
		exURLs.add("/common/logout");
	}

	private boolean excludeCheck(String url) {
		boolean result = false;

		result = result || url.length() <= 1;

		for (String exURL : exURLs) {
			result = result || url.indexOf(exURL) == 0;
		}
		
		return result;
	}
}


