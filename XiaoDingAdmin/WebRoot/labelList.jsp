<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>标签列表</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"></link>
		<link href="css/foodstyle.css" rel="stylesheet" type="text/css"></link>
	    <link rel="stylesheet" type="text/css" href="css/default.css">
        <link href="pagination/css/qunit-1.11.0.css" rel="stylesheet"></link>
		
		<script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>  
	</head>
	<body style="padding: 20px 30px 0 30px;">
	<div class="row">
		<div class="col-lg-12">
	 	  	 <div class="input-group custom-search-form">
	            <input id="searchText" type="text" class="form-control" placeholder="请输入标签名称">
	            <span class="input-group-btn">
	                <button class="btn btn-default" type="button" id="searchBtn">
	                    <i class="glyphicon glyphicon-search"></i>
	                </button>
	            </span>
	         </div>
 	  </div>
 	  <div class="col-lg-12">
 	  	<form role="form" class="form-horizontal">
	 	  	<ul class="list-group" style="margin-top:10px" id="labelCheckBoxes">
			   
			</ul>
			<div align="center">
             	<ul id="labelPager"></ul>
            </div>
			<div class="form-group">
		      <div class="col-xs-offset-4">
		         <button type="button" class="btn btn-success" id="labelCB_OK">添加</button>
		         <button type="button" class="btn btn-default" id="labelCB_Cancel">完成</button>
		      </div>
		   </div>
		</form>
 	  </div>
	</div>
	<script src="pagination/js/bootstrap-paginator.js"></script>
    <script src="pagination/js/qunit-1.11.0.js"></script>			
    <script>
    var chk_value = {}; 
    $(document).ready(function(){
    	//alert("hello");
    	getFirstPageOfLabel();
	});
  //  var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
  
   $('input:checkbox').change(function () { 
  		   alert("hello");
  	   }); 
   
    //给父页面传值
	$('#labelCB_OK').on('click', function(){
		
	   // parent.layer.msg("选择成功！");
	   // parent.layer.close(index);
		$('input[name="optionsCheckbox"]:checked').each(function(){ 
			var labelId = $(this).prop("id");
			var labelVal = $(this).val();
			chk_value[labelId] = labelVal;
		}); 
		//$('#labelCheckBoxes').data('checks', chk_value);
	});
	
	$('#labelCB_Cancel').on('click', function(){
	   // parent.layer.close(index);
		for(var prop in chk_value){
		    if(chk_value.hasOwnProperty(prop)){
		        alert('key is ' + prop +' and value is' + chk_value[prop]);
		    }
		}
	});
	
	$('#searchBtn').on('click', function(){
	    searchLabel();
   });
   $('#searchText').keyup(function(){
        if(event.keyCode == 13){
            //这里写你要执行的事件;
        	 searchLabel();
        }
    });
   
 
	
    var getFirstPageOfLabel = function(){
		
		$.ajax({
		      url: "listLabels.action",
		      datatype: 'json',
		      type: "GET",
		      data: { "pageNo": 1,"pageSize":5 },
		      dataType: "json",
		      success: function (data) {
		    	  var curPage = 0;
				  var tPages = 10;
				  var pgSize = 5;
		          if (data != null) 
		          {
		        	   tPages =  data.totalPages;
		        	   curPage = data.currentPage;
		        	   pgSize = data.sizeOfPage;
		               var tableShow = "";
			           $.each(data.labelLists, function(i, item){
			        	 //  alert(item.name + " ," + item.picPath);
			        	 tableShow += "<li class='list-group-item'>";
			        	 tableShow += "<label class='checkbox-inline'>";
			        	 tableShow += "<input type='checkbox' name='optionsCheckbox' id=\"" + item.id + "\" " +  
				           " value=\"" + item.name + "\">" + item.name + "</label></li>";
			           });//end each
			           $('#labelCheckBoxes').append(tableShow);
			           
			           var element = $('#labelPager');
			            var options = {
			                bootstrapMajorVersion:3,
			                currentPage: curPage,
			                numberOfPages: pgSize,
			                totalPages: tPages,
			                itemTexts: function (type, page, current) {
			                switch (type) {
				                case "first":
				                  return "首页";
				                case "prev":
				                  return "上一页";
				                case "next":
				                  return "下一页";
				                case "last":
				                  return "末页";
				                case "page":
				                  return page;
			                  }
			              },
			              //点击事件，用于通过Ajax来刷新整个list列表
				            onPageClicked: function (event, originalEvent, type, page) {
				            	
				            	getLabel(page);
				            }//onpageClicked
			            };//end of options

			            element.bootstrapPaginator(options);
			          
		          }//end data != null
		     }//end success
		    
		});//end $.ajax
	};
	
	var getLabel = function(pageNo){
		 $('#labelCheckBoxes').html("");
		$.ajax({
		      url: "listLabels.action",
		      datatype: 'json',
		      type: "GET",
		      data: { "pageNo": pageNo, "pageSize":5 },
		      dataType: "json",
		      success: function (data) {
		    	 
		          if (data != null) 
		          {
		        	   tPages =  data.totalPages;
		        	   curPage = data.currentPage;
		        	   pgSize = data.sizeOfPage;
		               var tableShow = "";
			           $.each(data.labelLists, function(i, item){
			        	 // alert(item.name + " ," + item.picPath);
			        	   tableShow += "<li class='list-group-item'>";
				           tableShow += "<label class='checkbox-inline'>";
				           tableShow += "<input type='checkbox' name='optionsCheckbox' id=\"" + item.id + "\" " +  
					           " value=\"" + item.name + "\">" + item.name + "</label></li>";
			           });//end each
			           $('#labelCheckBoxes').append(tableShow); 
		          }
		     }
		    
		});//end $.ajax
	};//end getLabel
	
	var searchLabel = function(){
		   var labelName = $('#searchText').val();
		   if(labelName.length > 0)
		   {
			   $('#labelCheckBoxes').html("");
			   $('#labelPager').hide();
			   $.ajax({
				      url: "searchLabel.action",
				      datatype: 'json',
				      type: "GET",
				      data: { "labelName": labelName},
				      dataType: "json",
				      success: function (data) {
				    	 
				          if (data != null) 
				          {
				               var tableShow = "";
					           $.each(data.labelLists, function(i, item){
					        	 // alert(item.name + " ," + item.picPath);
					        	   tableShow += "<li class='list-group-item'>";
						           tableShow += "<label class='checkbox-inline'>";
						           tableShow += "<input type='checkbox' name='optionsCheckbox' id=\"" + item.id + "\" " +  
							           " value=\"" + item.name + "\">" + item.name + "</label></li>";
					           });//end each
					           $('#labelCheckBoxes').append(tableShow); 
				          }
				     }
				    
				});//end $.ajax
		   }else{
			   $('#labelCheckBoxes').html("");
			   $('#labelPager').show();
			   getFirstPageOfLabel();
		   }
		};
    </script>
	</body>
</html>
