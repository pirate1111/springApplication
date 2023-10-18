<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<script>
	alert("로그인은 필수입니다.")
	location.href="<%=request.getContextPath()%>/common/loginForm.do?error=-1&retUrl=${retUrl}";

</script>