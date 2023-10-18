<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="pageMaker" value="${dataMap.pageMaker }" />
<c:set var="command" value="${pageMaker.command }" />
<c:set var="noticeList" value="${dataMap.noticeList }" />
<body>
 <!-- Main content -->
	<section class="content-header">
	  	<div class="container-fluid">
	  		<div class="row md-2">
	  			<div class="col-sm-6">
	  				<h1>공지목록</h1>  				
	  			</div>
	  			<div class="col-sm-6">
	  				<ol class="breadcrumb float-sm-right">
			        <li class="breadcrumb-item">
			        	<a href="list.do">
				        	<i class="fa fa-dashboard"></i>공지사항
				        </a>
			        </li>
			        <li class="breadcrumb-item active">
			        	목록
			        </li>		        
	    	  </ol>
	  			</div>
	  		</div>
	  	</div>
	</section>

	<section class="content">
		<div class="card">
			<div class="card-header with-border">
				<button type="button" class="btn btn-primary" id="registBtn" onclick="OpenWindow('registForm.do','공지등록',700,800);">공지등록</button>				
				
				
				<div id="keyword" class="card-tools" >
					<div class="input-group row">
						<select class="form-control col-md-3" name="perPageNum" id="perPageNum"
					  		onchange="searchList_go(1);">
					  		<option value="10" >정렬개수</option>
					  		<option value="20" ${command.perPageNum == 20 ? 'selected':''}>20개씩</option>
					  		<option value="50" ${command.perPageNum == 50 ? 'selected':''}>50개씩</option>
					  		<option value="100" ${command.perPageNum == 100 ? 'selected':''}>100개씩</option>
					  		
					  	</select>						
						<select class="form-control col-md-4" name="searchType" id="searchType">
							<option value="tcw" ${command.searchType eq 'tcw' ? 'selected':'' }>전 체</option>
							<option value="t" ${command.searchType eq 't' ? 'selected':'' }>제 목</option>
							<option value="w" ${command.searchType eq 'w' ? 'selected':'' }>작성자</option>
							<option value="c" ${command.searchType eq 'c' ? 'selected':'' }>내 용</option>
							<option value="tc" ${command.searchType eq 'tc' ? 'selected':'' }>제목+내용</option>
							<option value="cw" ${command.searchType eq 'cw' ? 'selected':'' }>작성자+내용</option>							
							<option value="tcw" ${command.searchType eq 'tcw' ? 'selected':'' }>작성자+제목+내용</option>
						</select>					
						<input  class="form-control" type="text" name="keyword" placeholder="검색어를 입력하세요." value="${command.keyword }"/>
						<span class="input-group-append">
							<button class="btn btn-primary" type="button" onclick="searchList_go(1);" data-card-widget="search">
								<i class="fa fa-fw fa-search"></i>
							</button>
						</span>
					</div>
				</div>			
			</div>
			<div class="card-body">
				<table class="table table-bordered table-striped" id="noticeTable" >	
				   <thead>				
					<tr style="font-size:0.95em;">
						<th style="width:10%;">번 호</th>
						<th style="width:50%;">제 목</th>
						<th style="width:15%;">작성자</th>
						<th>등록일</th>
						<th style="width:10%;">조회수</th>
					</tr>
				   </thead>	
				   <tbody>
					<c:if test="${empty noticeList }" >
						<tr>
							<td colspan="5">
								<strong>해당 내용이 없습니다.</strong>
							</td>
						</tr>
					</c:if>			
					<c:forEach items="${noticeList }" var="notice">
						<tr style='font-size:0.85em;cursor:pointer;' 
							onclick="OpenWindow('detail.do?nno=${notice.nno }&from=list','상세보기',700,800);">
							<td>${notice.nno }</td>
							<td style="text-align:left;
									   max-width:100px;
							           overflow: hidden; 
									   white-space: nowrap; 
									   text-overflow: ellipsis;">${notice.title }</td>
							<td>${notice.writer }</td>
							<td>
								<fmt:formatDate value="${notice.regDate }"
								                pattern="yyyy-MM-dd"/>
							</td>
							<td>${notice.viewcnt }</td>
						</tr>
					</c:forEach>
				   </tbody>			
				</table>
			</div>
			<div class="card-footer">
				<%@ include file="/WEB-INF/views/module/pagination.jsp" %>
			</div>
		</div>
	</section>


<%@ include file="/WEB-INF/views/module/footer_js.jsp" %>

<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-responsive/js/dataTables.responsive.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-buttons/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-buttons/js/buttons.bootstrap4.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/jszip/jszip.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/pdfmake/pdfmake.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/pdfmake/vfs_fonts.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-buttons/js/buttons.html5.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-buttons/js/buttons.print.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/bootstrap/plugins/datatables-buttons/js/buttons.colVis.min.js"></script>


<script>
window.onload=function(){
	$("#noticeTable").DataTable({
		"paging": false,
	    "searching": false,
	    "ordering": true,
	    "info": false,
    	"responsive": true, 
    	"lengthChange": true, 
    	"autoWidth": false,
    	"buttons": ["copy", {
    		extend: 'csv',
            charset: 'utf-8',
            bom: true
    	}, "excel", "pdf", "print"]
  	}).buttons().container().appendTo('#noticeTable_wrapper .col-md-6:eq(0)');
}
</script>
</body>








