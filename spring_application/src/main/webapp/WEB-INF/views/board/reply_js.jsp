<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/4.7.7/handlebars.min.js"></script>

<script type="text/x-handlebars-template"  id="reply-list-template" >
{{#each .}}  
<div class="replyLi" >
   <div class="user-block">
      <img src="<%=request.getContextPath()%>/member/getPicture.do?id={{replyer}}" class="img-circle img-bordered-sm"/>
    </div>   
   <div class="timeline-item" >
        <span class="time">
          <i class="fa fa-clock"></i>{{prettifyDate regdate}}
          <a class="btn btn-primary btn-xs {{rno}}-a" id="modifyReplyBtn" data-rno={{rno}} onclick="replyModifyModal_go('{{rno}}');"            
            style="display:{{VisibleByLoginCheck replyer}};"
             data-replyer={{replyer}} data-toggle="modal" data-target="#modifyModal">Modify</a>
        </span>
   
        <h3 class="timeline-header"><strong style="display:none;">{{rno}}</strong>{{replyer}}</h3>
        <div class="timeline-body" id="{{rno}}-replytext">{{replytext}} </div>
   </div>
</div>

{{/each}}
</script>

<script type="text/x-handlebars-template"  id="reply-pagination-template" >
   <li class="page-item">
      <a class="page-link" href="{{goPage 1}}">
         <i class="fas fa-angle-double-left"></i>
      </a>
   </li>
   <li class="page-item">
      <a class="page-link" href="{{#if prev}}{{goPage prevPageNum}}{{else}}{{goPage 1}}{{/if}}">
         <i class="fas fa-angle-left"></i>
      </a>                  
   </li>
   {{#each pageNum}}
      <li class="paginate_button page-item {{signActive this}} ">
         <a href="{{goPage this}}" aria-controls="example2" data-dt-idx="1" tabindex="0" class="page-link">{{this}}</a>
      </li>
   {{/each}}
   <li class="page-item">
      <a class="page-link" href="{{#if next}}{{goPage nextPageNum}}{{else}}{{goPage command.page}}{{/if}}">
         <i class="fas fa-angle-right"></i>
      </a>                  
   </li>
   <li class="page-item">
      <a class="page-link" href="{{goPage realEndPage}}">
         <i class="fas fa-angle-double-right"></i>
      </a>
   </li>
</script>

<script>
   Handlebars.registerHelper({
      "prettifyDate":function(timeValue){ //Handlbars에 날짜출력함수 등록
         var dateObj=new Date(timeValue);
         var year=dateObj.getFullYear();
         var month=dateObj.getMonth()+1;
         var date=dateObj.getDate();
         return year+"/"+month+"/"+date;
      },
      "VisibleByLoginCheck":function(replyer){
         var result="none";      
         if(replyer == "${loginUser}") result="visible";      
         return result;                    
      },
      "goPage":function(pageNum){
         return 'javascript:getPage("<%=request.getContextPath()%>/reply/list.do?bno=${board.bno}&page='+pageNum+'",'+pageNum+');';
      },
      "signActive":function(pageNum){
         if(pageNum == currentPage) return 'active';
      }
   });
   
   window.onload=function(){
      getPage("<%=request.getContextPath()%>/reply/list?bno=${board.bno}&page="+currentPage);
   }

</script>

<script>

function getPage(pageInfo,page){
   var reply_list_func = Handlebars.compile($("#reply-list-template").html());
   var pagination_func = Handlebars.compile($("#reply-pagination-template").html());
   
   $.getJSON(pageInfo,function(data){
      var reply_html = reply_list_func(data.replyList);
      $('.replyLi').remove();
      $('#repliesDiv').after(reply_html);
      
      if(page) currentPage = page;
      
      let pageMaker = data.pageMaker;
      
      var pageNumArray = new Array(pageMaker.endPage-pageMaker.startPage+1);
      for(var i=pageMaker.startPage;i<pageMaker.endPage+1;i++){
         pageNumArray[i-pageMaker.startPage]=i;
      }   
      
      pageMaker.pageNum=pageNumArray;  
      pageMaker.prevPageNum=pageMaker.startPage-1;
      pageMaker.nextPageNum=pageMaker.endPage+1;
      
      var pagination_html = pagination_func(data.pageMaker);
      $("#pagination").html(pagination_html);
   });
}
</script>

<script>
var currentPage = 1;
function replyRegist_go(){
   //alert("click reply btn");
   var replytext=$('#newReplyText').val();   
   //alert(replytext);
   var data={
         "bno":"${board.bno}",
         "replyer":"${loginUser}",
         "replytext":replytext   
   }
   
   $.ajax({
      url:"<%=request.getContextPath()%>/reply/regist.do",
      type:"post",
      data:JSON.stringify(data),      
      contentType:'application/json',
      success:function(data){
         alert('댓글이 등록되었습니다.\n마지막페이지로 이동합니다.');
         currentPage=data; //페이지이동
         getPage("<%=request.getContextPath()%>/reply/list.do?bno="+${board.bno}+"&page="+data); //리스트 출력
         $('#newReplyText').val("");   
         
      },
      error:function(error){
         AjaxErrorSecurityRedirectHandler(error.status);
      }
   });
}

function replyModifyModal_go(rno){
   //alert(rno);
   //alert($("div#"+rno+"-replytext").text());
   $('div#modifyModal div.modal-body #replytext').val($('div#'+rno+'-replytext').text());
   $('div#modifyModal div.modal-header h4.modal-title').text(rno);
}

//댓글 수정.
function replyModify_go(){
   //alert("click modify btn");
   var rno=$('div#modifyModal h4.modal-title').text();
   var replytext=$('div#modifyModal #replytext').val();
   
   var sendData={
         "rno":rno,
         "replytext":replytext
   }
   
   $.ajax({
      url:"<%=request.getContextPath()%>/reply/modify.do",
      method:"PUT",
      data:JSON.stringify(sendData),
      contentType:"application/json",
      headers:{         
         "X-HTTP-Method-Override":"PUT"
      },
      success:function(result){
         alert("수정되었습니다.");         
         getPage("<%=request.getContextPath()%>/reply/list.do?bno=${board.bno}&page="+currentPage);
      },
      error:function(error){
         AjaxErrorSecurityRedirectHandler(error.status);
      },
      complete:function(){
         $('#modifyModal').modal('hide');
      }
   });
   
}

function replyRemove_go(){
   //alert("click remove btn");
   var rno=$('h4.modal-title').text();
   
   $.ajax({
      url:"<%=request.getContextPath()%>/reply/remove.do?rno="+rno+"&page="+currentPage+"&bno=${board.bno}",
      type:"get",
      success:function(page){
         alert("삭제되었습니다.");
         getPage("<%=request.getContextPath()%>/reply/list.do?bno=${board.bno}&page="+page);
         currentPage=page;
      },
      error:function(error){
         AjaxErrorSecurityRedirectHandler(error.status);
      },
      complete:function(){
         $('#modifyModal').modal('hide');
      }
   });
}
</script>




