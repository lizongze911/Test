<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>文件下载</h2>
	<%=request.getContextPath()%>
	<form action="<%=request.getContextPath()%>/fileDownload" method="post"
		enctype="multipart/form-data">
		<p>
			选择文件: <input type="file" name="fileName" />
		</p>
		<p>
			<input type="submit" value="下载" />
		</p>
	</form>
	<a href="D:\\test\\">打开</a>
</body>
</html>