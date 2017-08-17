<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>品牌列表</title>
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
	            <input id="searchText" type="text" class="form-control" placeholder="请输入品牌名称">
	            <span class="input-group-btn">
	                <button class="btn btn-default" type="button" id="searchBtn">
	                    <i class="glyphicon glyphicon-search"></i>
	                </button>
	            </span>
	         </div>
 	  </div>
 	  <div class="col-lg-12">
 	  	<form role="form" class="form-horizontal">
	 	  	<ul class="list-group" style="margin-top:10px" id="brandRadios">  
			</ul>
             <div align="center">
             	<ul id="brandPager"></ul>
             </div>
			<div class="form-group">
		      <div class="col-xs-offset-4">
		         <button type="submit" class="btn btn-primary" id="brandRadio_OK">确定</button>
		         <button type="button" class="btn btn-default" id="brandRadio_Cancel">取消</button>
		      </div>
		   </div>
		</form>	
 	  </div>
	</div>
	 	
	<script src="pagination/js/bootstrap-paginator.js"></script>
    <script src="pagination/js/qunit-1.11.0.js"></script>
    <script>
    $(document).ready(function(){
    	//alert("hello");
    	getFirstPageOfBrand();
	});
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    //给父页面传值
	$('#brandRadio_OK').on('click', function(){
		var ele=$('input:radio[name="optionsRadiosinline"]:checked');
		var val = $(ele).val();
		var brandId = $(ele).attr("id");
        if(val==null){
        	parent.layer.msg("请选择一个品牌!");
            return false;
        }
        else{
        	//alert("id=" + id + ", val=" + val);
        	//parent.layer.msg("选中品牌[" + val + "]");
     	    var str = "<label id=\"" + brandId +  "\" >" + val + "</lable>";
     	    parent.$('#brands').html(str);
        }
	  
	    parent.layer.close(index);
	});
	
	$('#brandRadio_Cancel').on('click', function(){
	    parent.layer.close(index);
	});
	
	$('#searchBtn').on('click', function(){
	    searchBrand();
   });
   $('#searchText').keyup(function(){
        if(event.keyCode == 13){
            //这里写你要执行的事件;
        	 searchBrand();
        }
    });
	
	var getFirstPageOfBrand = function(){
		
		$.ajax({
		      url: "listBrands.action",
		      datatype: 'json',
		      type: "GET",
		      data: { "pageNo": 1,"pageSize":5 },
		      dataType: "json",
		      success: function (data) {
		    	  var curPage = 0;
				  var tPages = 10;
				  var pgSize = 10;
		          if (data != null) 
		          {
		        	   tPages =  data.totalPages;
		        	   curPage = data.currentPage;
		        	   pgSize = data.sizeOfPage;
		               var tableShow = "";
			           $.each(data.brandLists, function(i, item){
			        	 //  alert(item.name + " ," + item.picPath);
			        	 tableShow += "<li class='list-group-item'>";
			        	 tableShow += "<label class='radio-inline'>";
			        	 tableShow += "<input type='radio' name='optionsRadiosinline' id=\"" + item.id + "\" " +  
				           " value=\"" + item.name + "\">" + item.name + "</label></li>";
			           });//end each
			           $('#brandRadios').append(tableShow);
			           
			           var element = $('#brandPager');
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
				            	
				            	getBrand(page);
				            }//onpageClicked
			            };//end of options

			            element.bootstrapPaginator(options);
			          
		          }//end data != null
		     }//end success
		    
		});//end $.ajax
	};
	
	var getBrand = function(pageNo){
		 $('#brandRadios').html("");
		$.ajax({
		      url: "listBrands.action",
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
			           $.each(data.brandLists, function(i, item){
			        	 // alert(item.name + " ," + item.picPath);
			        	   tableShow += "<li class='list-group-item'>";
				           tableShow += "<label class='radio-inline'>";
				           tableShow += "<input type='radio' name='optionsRadiosinline' id=\"" + item.id + "\" " +  
					           " value=\"" + item.name + "\">" + item.name + "</label></li>";
			           });//end each
			           $('#brandRadios').append(tableShow); 
		          }
		     }
		    
		});//end $.ajax
	};//end getBrand
	
	var searchBrand = function(){
		   var brandName = $('#searchText').val();
		   if(brandName.length > 0)
		   {
			   $('#brandRadios').html("");
			   $('#brandPager').hide();
			   $.ajax({
				      url: "searchBrand.action",
				      datatype: 'json',
				      type: "POST",
				      data: { "brandName": brandName},
				      dataType: "json",
				      success: function (data) {
				    	 
				          if (data != null) 
				          {
				               var tableShow = "";
					           $.each(data.brandLists, function(i, item){
					        	 // alert(item.name + " ," + item.picPath);
					        	   tableShow += "<li class='list-group-item'>";
						           tableShow += "<label class='radio-inline'>";
						           tableShow += "<input type='radio' name='optionsRadiosinline' id=\"" + item.id + "\" " +  
							           " value=\"" + item.name + "\">" + item.name + "</label></li>";
					           });//end each
					           $('#brandRadios').append(tableShow); 
				          }
				     }
				    
				});//end $.ajax
		   }else{
			   $('#brandRadios').html("");
			   $('#brandPager').show();
			   getFirstPageOfBrand();
		   }
		};
   
    </script>
	</body>
</html>
