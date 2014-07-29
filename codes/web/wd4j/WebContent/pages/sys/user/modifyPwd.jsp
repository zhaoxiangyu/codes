<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 修改密码
 * <p> </p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by </p>
 * @since 
 * @备注：
 */
-->
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../commons/include.jsp"%>
<html>
	<head>
		<title>密码修改</title>
		<%@ include file="../../commons/frame.jsp"%>
		<script>$import("md5, validation");</script>
		<script type="text/javascript">
		function modify(){
			var access=true;
			access=$('#userModify').validationEngine({returnIsValid:true})
			if(access==true&&confirm("你确定要修改密码？")){
				form = document.forms['userModify'];
				userModify.action="${ctx}/sys/user/modifyUserpwd.do";
				document.userModify.target="frmrigh";
				document.userModify.submit();
				top.Dialog.close();
				window.setTimeout(function (){top.window.open('','_parent','');
				top.window.close();},200)
			}
		}
		
	</script>
	</head>
	<body style="line-height: 150%">
		<form name="userModify" method="post" id="userModify">
			<input type="hidden" name="id" value="${user.id }" />
			<div class="box1" whiteBg="true">
				<table class="tableStyleInfo" formMode="transparent">
					<tr>
						<th>
							账号：
						</th>
						<td>
							<input type="text" readonly="readonly" name="userAccount"class="textinput" style="width: 175px"
								id="userAccount" value="${user.userAccount}" />
						</td>
					</tr>
					<tr>
						<th>
							原密码：
						</th>
						<td>
							<input type="password" name="ordPwd" id="ordPwd" style="width: 175px"
								class="textinput validate[required,ajax[ajaxPassword]]" maxlength="32"
								value="" />
							&nbsp;&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<th>
							新密码：
						</th>
						<td>
							<input type="password" name="userNewPassword" style="width: 175px"
								id="userNewPassword" class="textinput validate[required]" maxlength="32"
								value="" />
							&nbsp;&nbsp;
							<font color="red">*</font>
						</td>
					</tr>
					<tr>
						<th>
							确认新密码：
						</th>
						<td>
							<input type="password" name="userPassword" id="userPassword" style="width: 175px"
								class="textinput validate[required,confirm[userNewPassword]]"
								maxlength="32" value="" />
							&nbsp;&nbsp;
							<font color="red">*</font>
						</td>
					</tr>

				</table>
				<br/>
				<div align="center">
					<button type="button" class="buttonGray" onclick="modify();">
						保存
					</button>
					&nbsp;&nbsp;&nbsp;
					<button type="button" class="buttonGray"
						onclick="top.Dialog.close()">
						取消
					</button>
				</div>
			</div>
		</form>
	</body>
</html>
