<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@ include file="include.jsp"%>
<%
	String errorMsg = (String) request.getAttribute("errorMsg");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>错误页面</title>
<%@ include file="frame.jsp"%>
	<style type="text/css">
	.tip {
		width: 600px;
		height: 400px;
		background-color: #F8FCFE;
		margin-top: 50px;
	}
	
	.text {
		background: url(${ctx}/images/error_bg.jpg) no-repeat;
		width: 592px;
		height: 218px;
	}
	
	.fontclass {
		font-size: 14px;
		font-weight: bold;
	}
	</style>
	<script language="javascript">
		$(function (){
			if("<%=errorMsg%>"!="null"){
				alert("<%=errorMsg%>");
				logout();
			}
		});
		function logout(){
			var curWin = window;
			var endStr;
			var tempStr;
			for(var i=0;i<15;i++){
				tempStr = curWin.location.href;
				endStr = tempStr.substring(tempStr.length-6,tempStr.length);
				if(endStr !="/login" ){
					curWin = curWin.parent;
				}
				else{
					break;
				}
			}
			if("<%=errorMsg%>"!="null"){
			curWin.location="${ctx}/logout.do";
			}else{
			curWin.history.back();
			}
		}
	</script>
</head>
<body style="text-align: center;">
	<div class="tip">
		<div class="center">
			<div>
				<table width='550' border='0'
					style='text-align: center; height: 100px;'>
					<tr>
						<td width="60"></td>
						<td width="60"><img src="${ctx}/images/error_icon.jpg"
							width="127" height="121" /></td>
						<td style='padding-top: 32px;' align='left'>
							<table border='0'>
								<tr>
									<td class="fontclass">系统运行发生错误! </td>
								</tr>
								<tr>
									<td>错误类型：<%=request.getAttribute("javax.servlet.error.status_code")%></td>
								</tr>
							</table>
						</td>
						<td width="60"></td>
					</tr>
				</table>
			</div>
			<div class="size">
				<img src="${ctx}/images/error_hr.jpg" width="566" />
			</div>
			<br />
			<div class="text">
			<c:if test="${errorMsg==null}">
				<table width='550' border='0'
					style='text-align: center; line-height: 25px; font-size: 13px;'>
					<tr>
						<td class="fontclass">请尝试以下操作：&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td>1.刷新页面，重新访问。</td>
					</tr>
					<tr>
						<td>2.请与系统管理员联系。</td>
					</tr>
					<tr>
						<td style="line-height: 45px;"><img onclick="logout();"
							style="cursor: hand" src="${ctx}/images/fanhui.jpg" />&nbsp;&nbsp;</td>
					</tr>
				</table>
			</c:if>
			</div>
		</div>
	</div>
</body>
</html>
