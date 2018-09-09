<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My JSP 'welcome.jsp' starting page</title>

<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
 </head>
<body>
	<form id="uploadForm" enctype="multipart/form-data">
		<input id="file" type="file" name="file" />
	</form>
	<button id="upload" type="button" onclick="doUpload()">上传</button>
	<br>
	<a href="<%=request.getContextPath()%>/fileDownload2">下载</a>
	<p>
	
	<form id="ReadForm" enctype="multipart/form-data">
		<input id="file2" type="file" name="file2">
	</form>
	<button id="fileRead" type=“button” onclick="doRead()">读取文件</button>
	<button id="fileWrite" type=“button” onclick="doWrite()">写入文件</button>
	<script type="text/javascript"> 	
		function doUpload() {
			/* var fileObj = document.getElementById("file").files[0];
			var formData = new FormData();
			formData.append("file", fileObj); */
			var formData=new FormData();
			formData.append("file", $('#file')[0].files[0])
			$.ajax({
				url : "/fileUpload",
				type : 'POST',
				cache : false,
				data : formData,
				processData : false,
				contentType : false,
				saync:false
			}).done(function(res) {
				alert("上传成功");
			}).fail(function(res) {
				alert("上传失败");
			});
		}
		function doRead() {
			var formData=new FormData();
			formData.append("file2", $('#file2')[0].files[0])
			$.ajax({
				url : "/fileRead3",
				type : 'POST',
				cache : false,
				data : formData,
				processData : false,
				contentType : false,
				saync:false
			}).done(function(res) {
				alert("读取成功");
			}).fail(function(res) {
				alert("读取失败");
			});
		}
		function doWrite() {
			var formData=new FormData();
			formData.append("file2", $('#file2')[0].files[0])
			$.ajax({
				url : "/fileWrite2",
				type : 'POST',
				cache : false,
				data : formData,
				processData : false,
				contentType : false,
				saync:false
			}).done(function(res) {
				alert("写入成功");
			}).fail(function(res) {
				alert("写入失败");
			});
		}
	</script>
</body>
</html>
