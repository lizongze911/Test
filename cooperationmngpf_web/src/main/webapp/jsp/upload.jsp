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
	</script>
</body>
</html>
