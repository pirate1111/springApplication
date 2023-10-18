<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>


<script>
if('${loginUser}'){
	location.href="index.do";
}else{
	location.href="common/loginForm.do";
}
	
</script>