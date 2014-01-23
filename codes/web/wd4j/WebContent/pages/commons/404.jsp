<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../commons/include.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>'404.jsp'</title>    	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <style type="text/css" >
		.tip{ width:600px; height:400px;background-color:#F8FCFE;margin-top:50px;}
		.text {background:url(${ctx}/images/error_bg.jpg) no-repeat; width:592px; height:218px;}
		.fontclass{font-size:14px;font-weight:bold;}
	</style>
  </head>
  
  <%--
  <body>
    error.jsp
     <br/>
     ${model.exception}    
     <br/>
     <br/>
     <br/>
      sorry!请与系统管理员联系！
     <br/>
     <br/>
     <a onclick="history.back();">返回</a>
     --%>
  <body style="text-align:center;">
		<div class="tip">
		    <div class="center">
		       <div >	     				 
				 <table width='550' border='0' style='text-align:center;height:100px;'>
				     <tr >
					   <td width="60"></td>
					   <td width="60"><img src="${ctx}/images/error_icon.jpg" width="127" height="121" /></td>
					   <td style='padding-top:32px;' class="fontclass" align='left'>无法找到该页!</td>
					   <td width="60"></td>
					 </tr>
				 </table>
			   </div>
		    
		      <div class="size"><img src="${ctx}/images/error_hr.jpg" width="566" /></div>
			  <br/>
			  <div class="text">	    
				 <table width='550' border='0' style='text-align:center;line-height:25px;font-size:13px;'>
				    <tr>
					   <td class="fontclass">请尝试以下操作：&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					 <tr>
					   <td>1.刷新页面，重新访问</td>
					</tr>
					<tr>
					   <td>2.请与系统管理员联系</td>
					</tr>
					<tr>
					   <td style="line-height:45px;"><img onclick="history.back();"style="cursor:hand" src="${ctx}/images/fanhui.jpg"/>&nbsp;&nbsp;</td>
					</tr>
				 </table>
			  </div>
		</div>    
  </body>
</html>
