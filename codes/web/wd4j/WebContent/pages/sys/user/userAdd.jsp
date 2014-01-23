<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 用户添加页面
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
		<title>用户添加</title>
		<%@ include file="../../commons/frame.jsp"%>
		<%@ include file="../../commons/xdic.jsp"%>
		<script>$import("md5,validation");</script>
		<script type="text/javascript">
		function save(){
			var access=true;
			access=$('#userAdd').validationEngine({returnIsValid:true})
			if(access==true){
			document.getElementById("userPassword").value=hex_md5(document.getElementById("userPassword").value);
			document.forms["userAdd"].action="${ctx}/sys/user/insertUserInfo.do";
			document.forms["userAdd"].target="mainFrame";
			document.forms["userAdd"].submit();
			top.Dialog.close();
			}
		}
	</script>
	</head>
	<body style="line-height: 150%">
		<div class="box1" id="formContent" whiteBg="true">
			<form name="userAdd" id="userAdd"
				action="${ctx}/sys/user/insertUserInfo.do" method="post">
				<div style="padding: 10px 20px 0 20px;">
						<table style="width: 100%;">
							<tr>
								<td colspan="4" class="ver01">
									<div class="box3">
										<div class="box3_topcenter">
											<div class="box3_topleft">
												<div class="box3_topright">
													<div class="box3_title">
														用户新增
													</div>
												</div>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<table class="tableStyleInfo">
										<tr>
											<th>
												账号：
											</th>
											<td>
												<input type="text"
													class="textinput validate[required,ajax[ajaxUserAccount]]"  
												style="width: 175px" 
													name="userAccount" id="userAccount" maxlength="20" />
												&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<th>
												用户姓名：
											</th>
											<td>
												<input type="text" name="userName" id="userName" value=""
												style="width: 175px" 
													class="textinput validate[required]" maxlength="20" />
												&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<th>
												设置密码：
											</th>
											<td>
												<input type="password" name="userPassword" id="userPassword"
												style="width: 175px" 
													value="" class="textinput validate[required]" maxlength="32" />
												&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<th>
												确认密码：
											</th>
											<td>
												<input type="password" name="userPasswordCompare"
												style="width: 175px" 
													id="userPasswordCompare" value="" maxlength="32"
													class="textinput validate[required,confirm[userPassword]]" />
												&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<th>
												性别：
											</th>
											<td>
												<input type="radio" name="userSex" id="userSex1" value="1"
													checked />
												男
												<input type="radio" name="userSex" value="2" id="userSex2" />
												女
											</td>
										</tr>
										<tr>
											<th>
												警号：
											</th>
											<td>
												<input type="text" name="userIdentity"
												style="width: 175px" 
													class="textinput validate[required]" id="userIdentity" maxlength="50"
													value="" />
												&nbsp;&nbsp;
												<font color="red">*</font>
											</td>
										</tr>
										<tr>
											<th>
												身份证号：
											</th>
											<td>
												<input type="text" name="userIdCard" id="userIdCard"class="textinput"
												style="width: 175px" 
													maxlength="18" value="" />
											</td>
										</tr>
										<tr>
											<th>
												联系电话：
											</th>
											<td>
												<input type="text" name="userPhone" id="userPhone"class="textinput"
												style="width: 175px" 
													maxlength="25" value="" />
											</td>
										</tr>
										<tr>
											<th>
												住址：
											</th>
											<td>
												<input type="text" name="userAddress" id="userAddress"class="textinput"
												style="width: 175px" 
													maxlength="100" value="" />
											</td>
										</tr>
										<tr>
											<th>
												所属组织机构：
											</th>
											<td>
												<input type="text" kind="dic" code="" 
												style="width: 175px" 
													dic_src="dic_organizes" id="dicOrgId" name="dicOrgId"
													onpropertychange="doPropChange()" onfocus="doFocus()"
													onblur="doBlur()" onkeydown="doKeyPress()"
													class="textinput validate[required]" size="20" value="" />
												<input name="orgCode" type="hidden" value="" />
												&nbsp;&nbsp;
												<font color="red">*</font>

											</td>
										</tr>
										<tr>
											<th>
												角色：
											</th>
											<td>
												<c:forEach items="${rolesList}" var="rolesList">
													<input type="checkbox" name="rolesId"
														value="${rolesList.id}" style="width: 15px;" />${rolesList.roleName}
						</c:forEach>
											</td>
										</tr>
										<tr>
											<th>
												是否有效:
											</th>
											<td>
												<input type="radio" style="width: 20px;" name="isEffective"
													checked="checked" value="1" />
												是
												<input type="radio" style="width: 20px;" name="isEffective"
													value="0" />
												否
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
											<td colspan="2" align="center">
												<input type="button" class="buttonGray" onclick="save();" value="保存" />
												<input type="button" class="buttonGray" onclick="top.Dialog.close()" value="取消" />
											</td>
										</tr>
						</table>
					</form>
				</div>
	</body>
</html>