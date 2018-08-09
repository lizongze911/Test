<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.4.4.js"></script>
	<script>
	function fileChange(base) {
		$("#fileTypeError").html('');
		var fileName=$('#file_upload').val();
		var fileType=fileName.substr(fileName.length-4,fileName.length);
		if(fileType=='.xls' || fileType=='.doc' || fileType=='.pdf' || fileType=='.txt'){
			$ajax({
				url: base+'/actionsupport/upload/uploadFile', 
	            /* url:'D://test//', */
				type: 'POST',
	            cache: false,
	            data: new FormData($('#uploadForm')[0]),　　　　　　　　　　　　　
	            processData: false,
	            contentType: false,
	            success:function(data){
	                if(data=='fileTypeError'){
	                    $("#fileTypeError").html('*上传文件类型错误,支持类型: .xsl .doc .pdf');　　
	                }
	                $("input[name='enclosureCode']").attr('value',data);
	            }
			});
		}else {
			$("#fileTypeError").html('*上传文件类型错误,支持类型: .xls .doc .pdf .txt');
		}
	}
	</script>
</head>
<body>
	<%-- <h2>文件上传</h2>
	<%=request.getContextPath()%>
	<form action="<%=request.getContextPath()%>/fileUpload" method="post"
		enctype="multipart/form-data">
		<p>
			选择文件: <input type="file" name="fileName" />
		</p>
		<p>
			<input type="submit" value="提交" />
		</p>
	</form>
	<%=request.getContextPath()%>
	<a href="<%=request.getContextPath()%>/fileDownload">下载</a> --%>

	<h2>文件上传2</h2>
	<%=request.getContextPath()%>
	<form action="<%=request.getContextPath()%>/uploadForm" enctype="multipart/form-data">
		<input id="file_upload"  type="file"  name="pic"  onchange="fileChange(''${base} }')"/> 
		<br/> 
		<span	stype="color :red"  id="fileTypeError"/>
	</form>
</body>
</html>