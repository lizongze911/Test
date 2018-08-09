<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'welcome.jsp' starting page</title>
    <script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
	<script>
		$(document).ready(function(){
			$("#button1").click(function(){
				var nowPage = encodeURI($("#username").val());//get请求必须明确转码，post请求被web.xml配置的filter拦截转码了，不需要再转码了
				var pageSize = encodeURI($("#usercode").val());
				var dd = {'nowPage':nowPage,'pageSize':pageSize};
				$.ajax({
					url:"<%=request.getContextPath()%>/getPage",
					type:"get",
					data:dd,
					success:function(a){
						 if(a && a.length>0)
							 {
							     var s = "";
							     for(var i=0;i<a.length;i++){
							    	 s=s+a[i].USER_ID+"   "+a[i].USER_CODE+"   "+a[i].USER_NAME+"<br>";
							     }
							     document.getElementById("mydiv").innerHTML=s;
							 }
						},
					error:function(rq,a,b){
					 alert(a+"\n"+b);
					}
				});
			});
			
			
			
		});
	</script>
	
       
  </head>
  <body>
        <%=request.getContextPath()%>
		<form action="<%=request.getContextPath()%>/du.do" method="POST">
			当前页号码<input type="text" name="username" id="username" />
			每页显示记录数<input type="text" name="usercode" id="usercode"/>
			<input type="button"  value="button1" id="button1" />
		</form>
		
		<div id="mydiv">
		
		
		</div>
  </body>
</html>
