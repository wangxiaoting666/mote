<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>添加品牌</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"></link>
		<link href="css/foodstyle.css" rel="stylesheet" type="text/css"></link>
	    <link rel="stylesheet" type="text/css" href="css/default.css">
        <link href="css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />	
		
		<script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 
        <script src="js/fileinput.min.js" type="text/javascript"></script>
        <script src="js/fileinput_locale_zh.js" type="text/javascript"></script>
	</head>
	<body style="padding-top: 20px;">
	 <form id="brandForm" role="form" class="form-horizontal" enctype="multipart/form-data">
		<div class="form-group">
	      <label  class="col-xs-offset-1 col-xs-2">品牌名称:</label>
	      <div class="col-xs-8">
	         <input type="text" name="name" class="form-control" placeholder="请输入品牌名称"/>
	      </div>
	   </div>
	   <div class="form-group">
	      <label class="col-xs-offset-1 col-xs-2">品牌图片:</label>
	      <div class="col-xs-8">
	         <input id="brandFile" class="file" type="file" name="file"/>
	      </div>
	   </div>
        <div class="form-group">
	      <div class="col-xs-offset-3">
	         <button type="button" class="btn btn-primary" id="addBrand_OK">确定</button>
	         <button type="button" class="btn btn-default" id="addBrand_Cancel">取消</button>
	      </div>
	   </div>
    </form>
	 			
    <script>
    var index = parent.layer.getFrameIndex(window.name); //è·åçªå£ç´¢å¼
    //ç»ç¶é¡µé¢ä¼ å¼
	$('#addBrand_OK').on('click', function(){
		  //alert("形如");
		  $("#brandFile").fileinput('refresh',{
			   uploadUrl:'addBrand.action',
			   uploadExtraData: getFromJson($("#brandForm"))
		   });
		  $("#brandFile").fileinput('upload');
	});
	 
	
	$('#addBrand_Cancel').on('click', function(){
	    parent.layer.close(index);
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
	 $("#brandFile").fileinput({
	    language:'zh',
	    allowedFileExtensions : ['jpg', 'png','gif','bmp'],
	    showUpload: false,
	    maxFileCount: 1,
	    showCancel: true,
	    showClose: true,
	    showUpload:false,
	    //uploadExtraData:{name:123456},
	    enctype: 'multipart/form-data'
      });
	 $("#brandFile").on("fileuploaded", function (event, data, previewId, index) {
		var jsonData = data.response; 
		
		closeWin(jsonData.msg);
	  });
	 var closeWin = function(data){
		 parent.layer.msg(data);
		 parent.layer.close(index);
	 };
    </script>
	</body>
</html>
