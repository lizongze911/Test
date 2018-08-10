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
		<button id="upload" type="button" onclick="doUpload()">upload</button>
	<script type="text/javascript"> 
	
		/* $(function() {
			$("#upload").click(function() */ 
				
		function doUpload() {
			var fileObj = document.getElementById("file").files[0];
			var formData = new FormData();
			//formData.append("file", fileObj);
			var tt={"file2":fileObj};
			$.ajax({
				url : 'http://localhost:8080/fileUpload',
				type : 'POST',
				cache : false,
				//data : formData,
				data:tt,
				processData : false,
				contentType : false
			}).done(function(res) {
				alert(res);
			}).fail(function(res) {
				alert("上传失败");
			});
		}
	</script>
</body>
</html>
