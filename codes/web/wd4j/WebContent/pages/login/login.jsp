<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>样例系统</title>
		<script type="text/javascript">
	var contextPath = "${ctx}";//contextPath在boot.js中用到
</script>
		<script type="text/javascript" src="${ctx}/js/boot.js"></script>
		<script>$import("jquery,login.style,md5,dialog");</script>
<script type="text/javascript">
	$(function(){
		 if("${errorMsg}"!=""){
		 	top.Dialog.alert("${errorMsg}");
		 }
	})
	function login(){
		var mes='';
		if($("#userName").val()==''){
			mes="请输入用户名！";
		}
		if($("#password").val()==''){
			mes+="请输入密码！";
		}
		if(mes!=''){
			top.Dialog.alert(mes);
			return false;
		}else{
			$("#password").attr("value",hex_md5($("#password").val()));
			return true;
		}
	}
</script>
</head>
	<body >
	<div class="login_main">
		<div class="login_top">
			<div class="login_title"></div>
		</div>
		<div class="login_middle">
			<div class="login_middleleft"></div>
			<div class="login_middlecenter">
					<form action="${ctx }/login" class="login_form" onsubmit="return login();" method="post">
					<div class="login_user"><input type="text" id="userName" name="userName" value="${param.userName }"/></div>
					<div class="login_pass"><input type="password" id="password" name="password" value="${param.password }"/></div>
					<div class="clear"></div>
					<div class="login_button">
						<div class="login_button_left"><input type="submit" value="" onfocus="this.blur()"/></div>
						<div class="login_button_right"><input type="reset" value="" onfocus="this.blur()"/></div>
						<div class="clear"></div>
					</div>
					</form>
					<div class="login_info"></div>
			</div>
			<div class="login_middleright"></div>
			<div class="clear"></div>
		</div>
		<div class="login_bottom">
			<div class="login_copyright">Copyright © 2013 sharpx.org</div>
		</div>
	</div>
	</body>
</html>
