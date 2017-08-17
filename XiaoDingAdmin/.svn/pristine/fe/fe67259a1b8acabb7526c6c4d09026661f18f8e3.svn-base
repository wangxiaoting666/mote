<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>修改品牌</title>
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
	         <input id="brandName" type="text" name="name" value="${param.name}" class="form-control"/>
	      </div>
	   </div>
	   <div class="form-group">
	      <label class="col-xs-offset-1 col-xs-2">品牌图片:</label>
	      
	      <div class="col-xs-8">
	         <input id="brandId" type="hidden" value="${param.id }">
	         <input id="brandPicPath" type="hidden" value="${param.picPath }">
	         <input id="brandFile" class="file-uploading" type="file" name="file"/>
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
		 var brandId = $("#brandId").val();
	     var brandPicPath = $("#brandPicPath").val();
	     var brandName = $("#brandName").val();
	     var files = $('#brandFile').fileinput('getFileStack');
	     
	    // alert(brandPicPath);
	    // alert(files.length);
	     if(files.length == 0)//数组长度为0 说明没有选中文件
	     {
	    	 $.post("updateBrandName.action",{id:brandId, name:brandName, picPath: brandPicPath},
					  function(data){
	    		           changeBrandName(brandPicPath);
						   parent.layer.msg(data.msg);
						   parent.layer.close(index);
					  },
					  "json");//这里返回的类型有：json,html,xml,text
	     }
	     else if(files.length > 0)//选中上传的文件
	     {
	    	//alert("形如");
			  $("#brandFile").fileinput('refresh',{
				   uploadUrl:'updateBrand.action',
				   uploadExtraData:{id:brandId, name:brandName, picPath: brandPicPath}
			  });
			  $("#brandFile").fileinput('upload');
	     }
		  
	});
	 
	
	$('#addBrand_Cancel').on('click', function(){
	    parent.layer.close(index);
	});
	
	 $(document).ready(function(){

	    //alert(brandId + "  " + brandName + " " + brandPicPath);
		 $("#brandFile").fileinput({
			    language:'zh',
			    allowedFileExtensions : ['jpg', 'png','gif','bmp'],
			    maxFileCount: 5,
			    showCancel: true,
			    showClose: true,
			    showUpload:false,
			    showPreview:true,
			    enctype: 'multipart/form-data',
			    initialPreview:["<img src=\"" +  $("#brandPicPath").val() + "\" class='file-preview-image'>",]
		 });
		
	 });
	
	 $("#brandFile").on("fileuploaded", function (event, data, previewId, index) {
		var jsonData = data.response; 
		
		closeWin(jsonData);
	  });
	 var closeWin = function(data){
		// alert(data.msg + " ," + data.newPicPath);
		 if(data.msg === "1")
		 {
			 changeBrandName(data.newPicPath);
			 parent.layer.msg("成功修改！");
		 }
		 else
		 {
			 parent.layer.msg("修改失败！！！");
		 }
		 parent.layer.close(index);
	 };
	 //父窗口改变brandName和brandPicPath的值
	 var changeBrandName = function(picPath){
		 
		     var brandId = $("#brandId").val();
		     var brandName = $("#brandName").val();
		     var thumbNailEle = parent.$('#'+brandId).children("div");
		     //修改名字
		     var brandNameNode = $(thumbNailEle).children("div").children("p");
		     $(brandNameNode).text(brandName);
		    //修改图片路径
		    var picNode =  $(thumbNailEle).children(":first");
		    $(picNode).attr("src", picPath);
	 };
    </script>
	</body>
</html>
