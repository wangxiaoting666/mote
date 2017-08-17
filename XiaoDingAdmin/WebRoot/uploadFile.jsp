<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'UploadFile.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"></link>
    <link href="css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script> 
    <script src="js/fileinput.min.js" type="text/javascript"></script>
    <script src="js/fileinput_locale_zh.js" type="text/javascript"></script>
  </head>
  
  <body>
    <form id="userForm2"  enctype="multipart/form-data" method="post">  
            文件名：<input type="text" id="nameText" class="form-control" name="name">
      <input id="file-0a" class="file-loading" name="file" type="file" multiple="true"/>
         
        
        <button type="button" class="btn btn-primary" id="addBrand_OK">上传</button>
          
          
    </form>
    <script>
    var picMap = new Map();
        $(function(){
        
			$("#addBrand_OK").click(function(){
			   //alert($("#userForm2").serialize());
			  // var formData = $("#userForm2").serialize();
			  // alert(getFromJson($("#userForm2")) + " ");
			   $("#file-0a").fileinput('refresh',{
				   uploadExtraData: getFromJson($("#userForm2"))
			   });
			   $("#file-0a").fileinput('upload');
			});
		});
        var getFromJson = function(form) {
	        	var o = {};
	        	var a = $(form).serializeArray();
	        	$.each(a, function () {
	        	if (o[this.name] !== undefined) 
	        	{
	        	if (!o[this.name].push)
	        	{
	        	  o[this.name] = [o[this.name]];
	        	}
	        	  o[this.name].push(this.value || '');
	        	} 
	        	else
	        	{
	        	  o[this.name] = this.value || '';
	        	}
        	});
        	return o;
        };
		 $("#file-0a").fileinput({
	       language:'zh',
	       allowedFileExtensions : ['jpg', 'png','gif','exe'],
	       maxFileCount: 5,
	       showCancel: false,
	       showClose: true,
	       showUpload:false,
	       uploadUrl:'fileUpload.action',
	       //uploadExtraData:{name:123456},
	       enctype: 'multipart/form-data'
	    });
		 
		 $("#file-0a").on('fileclear', function(event) {
			    alert("clear");
			});
		 $("#file-0a").on("fileuploaded", function (event, data, previewId, index) {
			 var jsonData = data.response; 
			 
			 picMap.set(index, jsonData.picName);
			//alert("jsonData: " + jsonData.picName);
		 });
		 $('#file-0a').on("filedeleted", function(event, key) {
			    alert('Key = ' + key);
			 
		 });
		 $("#file-0a").on("filesuccessremove", function(event, id) {
			 var i = id.lastIndexOf('-');
			 var mapIndex = id.substring(i+1);
			 alert("mapIndex is" + mapIndex + ", pic path: " + picMap.get(mapIndex));
			 return true;
		});
    </script>   
  </body>
</html>
