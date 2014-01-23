<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 用户修改页面
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
		<title>用户修改</title>
		<%@ include file="../../commons/frame.jsp"%>
		<%@ include file="../../commons/xdic.jsp"%>
		<script>$import("md5, validation");</script>
		<script type="text/javascript">
		function modify(){
			var access=true;
			access=$('#userModify').validationEngine({returnIsValid:true})
			if(access==true){
				userModify.action="${ctx}/sys/user/modifyUserInfo.do";
				document.userModify.target="mainFrame";
				document.userModify.submit();
				top.Dialog.close();
			}
		}
	</script>
	</head>
	<body style="line-height: 150%">
		<form name="userModify" id="userModify"
			action="${ctx}/sys/user/user_modify.do" method="post">
			<input type="hidden" value="${user.id }" name="id" id="id" />
			<input type="hidden" value="${user.createTime }" name="createTime"
				id="createTime" />
			<input type="hidden" value="${user.userPassword }"
				name="userPassword" id="userPassword" />
			<div style="padding: 10px 20px 0 20px;">
				<table style="width: 100%;">
					<tr>
						<td colspan="4" class="ver01">
							<div class="box3">
								<div class="box3_topcenter">
									<div class="box3_topleft">
										<div class="box3_topright">
											<div class="box3_title">
												修改用户
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
										<input type="text" name="userAccount" id="userAccount"
											readonly="readonly" class="validate[required]" maxlength="20"
											value="${user.userAccount}" style="width:250px" />
										&nbsp;&nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<th>
										用户名：
									</th>
									<td>
										<input type="text" name="userName" id="userName"
											maxlength="20" class="validate[required]"
											value="${user.userName}" style="width:250px"/>
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
											${user.userSex== "1"?"checked":"" } />
										男
										<input type="radio" name="userSex" value="2" ${user.userSex==
											"2"?"checked":"" }
						id="userSex2" />
										女
									</td>
								</tr>
								<tr>
									<th>
										警号：
									</th>
									<td>
										<input type="text" name="userIdentity" id="userIdentity"
											class="validate[required]" value="${user.userIdentity}"
											maxlength="50" style="width:250px"/>
										&nbsp;&nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<th>
										身份证号：
									</th>
									<td>
										<input type="text" name="userIdCard" id="userIdCard"
											value="${user.userIdCard}" maxlength="18" style="width:250px"/>
									</td>
								</tr>
								<tr>
									<th>
										联系电话：
									</th>
									<td>
										<input type="text" name="userPhone" id="userPhone"
											value="${user.userPhone}" maxlength="25" style="width:250px"/>
									</td>
								</tr>
								<tr>
									<th>
										住址：
									</th>
									<td>
										<input type="text" name="userAddress" id="userAddress"
											value="${user.userAddress }" maxlength="100" style="width:250px"/>
									</td>
								</tr>
								<tr>
									<th>
										所属组织机构：
									</th>
									<td>
										<input type="text" kind="dic" code="" dic_src="dic_organizes"
											class="validate[required]" name="dicOrgId"
											onpropertychange="doPropChange()" onfocus="doFocus()"
											onblur="doBlur()" onkeydown="doKeyPress()" size="20"
											value="${AppCacheDict.organizes_dic[user.orgCode].ORG_NAME }" style="width:250px"/>
										<input name="orgCode" type="hidden" value="${user.orgCode}" />
										&nbsp;&nbsp;
										<font color="red">*</font>
									</td>
								</tr>
								<tr>
									<th>
										角色：
									</th>
									<td colspan="3">
										<c:forEach items="${rolesList}" var="rolesList">
											<input type="checkbox" name="rolesId" value="${rolesList.id}"
												<c:forEach items="${userRolesList}" var="userRolesList">
													<c:if test="${rolesList.id==userRolesList.roleId}">checked</c:if> 
												</c:forEach>style="width: 15px;" />${rolesList.roleName}
										</c:forEach>
									</td>
								</tr>
								<tr>
									<th>
										是否有效：
									</th>
									<td colspan=3>
										<input type="radio" style="width: 20px;" name="isEffective"
											${user.isEffective==null ? "checked" : user.isEffective==
											"1"?"checked":"" }
						value="1" />
										是
										<input type="radio" style="width: 20px;" name="isEffective"
											${user.isEffective== "0" ? "checked":"" }
						value="0" />
										否
									</td>
								</tr>

							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<button type="button" class="buttonGray" onclick="modify();">
								保存
							</button>
							&nbsp;
							<button type="button" class="buttonGray" onclick="top.Dialog.close()">
								取消
							</button>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
