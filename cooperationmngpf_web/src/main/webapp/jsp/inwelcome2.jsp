<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'welcome.jsp' starting page</title>
    <script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
	<script>
		$(document).ready(function(){
			$("#button1").click(function(){
				var name = encodeURI($("#username").val());//get请求必须明确转码，post请求被web.xml配置的filter拦截转码了，不需要再转码了
				var code = encodeURI($("#usercode").val());
				var dd = {'username':name,'usercode':code};
				$.ajax({
					url:"<%=request.getContextPath()%>/hello2",
					type:"get",
					data:dd,
					success:function(a){
						alert(a);
						$("#username").val(a.duwenyi)
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
			<input type="text" name="username" id="username" />
			<input type="text" name="usercode" id="usercode"/>
			<input type="button"  value="button1" id="button1" />
		</form>
  </body>
</html>
