<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新增用户</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"></link>
		<link href="css/foodstyle.css" rel="stylesheet" type="text/css"></link>
	    <link rel="stylesheet" type="text/css" href="css/default.css">
		
		<script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> 
	</head>
	<body style="padding-top: 20px;">
		<div class="row">
	      <label  class="col-xs-offset-1 col-xs-2">用户名:</label>
	      <div class="col-xs-8">
	         <input type="text" id="userName" class="form-control" name="userName" placeholder="请输入用户名" required="required" autofocus="autofocus"/>
	      </div>
	   </div>
	   <div class="row">
	      <label class="col-xs-offset-1 col-xs-2">密码:</label>
	      <div class="col-xs-8">
	          <input type="password" id="password" class="form-control" name="password" placeholder="请输入密码" required="required"/>
	      </div>
	   </div>
        <div class="row">
	      <div class="col-xs-offset-3" style="margin-top: 10px;">
	         <button type="submit" class="btn btn-primary" id="addUser_OK">确认</button>
	         <button type="button" class="btn btn-default" id="addUser_Cancel">取消</button>
	      </div>
	   </div>
	 			
    <script>
    var index = parent.layer.getFrameIndex(window.name); //获取index
    //按钮事件处理
	$('#addUser_OK').on('click', function(){
		
		$.post("userAdd.action",{userName:$('#userName').val(),password:$('#password').val()},
				  function(data){
				    //$('#msg').html("please enter the email!");
				   /*var userInfo = "<tr>";
				   userInfo += "<td>" + $('#userName').val() + "</td>";
				   userInfo += "<td>" + $('#password').val() + "</td>";
				   userInfo += "<td>管理员</td></tr>";
				   parent. $('#adminTable').append(userInfo);*/
				   parent.layer.msg(data.msg);
				   parent.layer.close(index);
				    //$('#msg').html(data);
				  },
				  "json");//这里返回的类型有：json,html,xml,text
	});
	
	$('#addUser_Cancel').on('click', function(){
	     parent.layer.close(index);
	});
    </script>
	</body>
</html>
