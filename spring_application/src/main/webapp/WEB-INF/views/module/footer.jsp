<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>


  <!-- Control Sidebar -->
    <!-- Control sidebar content goes here -->
  <!-- /.control-sidebar -->

	<!-- Main Footer -->
		<!-- To the right -->
		<!-- Default to the left -->
    <!-- To the right -->
    <!-- Default to the left -->
</div>
<!-- ./wrapper -->



<!-- jQuery -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/resources/bootstrap/dist/js/adminlte.min.js"></script>

<!-- common.js  -->
<script src="<%=request.getContextPath() %>/resources/js/common.js"></script>
<script>
function pageName(menuName){
	$('.pageNames').text(menuName);
}
</script>
$('ul').Treeview(trigger)
<script>
function goPage(url,menuCode){
	$('iframe[name="ifr"]').attr("src",url);
	
	//alert(mCode);
	 var renewURL = location.href;
    //현재 주소 중 .do 뒤 부분이 있다면 날려버린다.
    renewURL = renewURL.substring(0, renewURL.indexOf(".do")+3);
    
    if (menuCode != 'M000000') {
        renewURL += "?menuCode=" + menuCode;
    }
    //페이지를 리로드하지 않고 페이지 주소만 변경할 때 사용
    history.pushState(menuCode, null, renewURL);
}

goPage('<%=request.getContextPath()%>${menu.menuUrl}','${menu.menuCode}');
</script>

</body>
</html>


