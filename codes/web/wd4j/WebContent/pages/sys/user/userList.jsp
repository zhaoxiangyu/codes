<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 用户列表页面
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>用户管理</title>
		<%@ include file="../../commons/frame.jsp"%>
		<script type="text/javascript">
		$import("ztree,selectTree");
		$(function (){$(".selectTree").selectTreeRender()})
		//删除
		function deleteById(userId){
			if(top.Dialog.confirm('确定删除用户吗？',function(){
				location.href="${ctx}/sys/user/deleteById.do?userId="+userId;
			})){
			}else{
				return;
			}		
		}
		//修改
		function updateById(userId){
			top.Dialog.open({URL:"${ctx}/sys/user/toModifyPage.do?userId="+userId,Title:"修改",Width:500,Height:450});
		}
		//查看
		function queryById(userId){
			top.Dialog.open({URL:"${ctx}/sys/user/selectUserView.do?userId="+userId,Title:"查看",Width:650,Height:350});
		}
		//新增
		function doAdd(){
			top.Dialog.open({URL:"${ctx}/sys/user/toAddPage.do",Title:"新增",Width:550,Height:500});
		}
		//清空
		function doClear(){
		document.forms['userListForm'].reset();
		document.getElementById('userAccount').value="";
		document.getElementById('userName').value="";
		document.getElementById('userIdentity').value="";
		document.getElementById('orgCode').value="";
		$('#code').attr("relCode","");
		document.getElementById("roleId").value="";
		}
		function resetPassword(userId){
			if(confirm('确定重置密码？')){
				$.get("${ctx}/sys/user/resetPassword.do",{userId:userId},function(json){
					if(json!=""){
						alert(json);
		     		}
				});
			}else{
				return;
			}
		}
		function queryForm(){
		var code=$("#code").attr("relCode");
			$("#orgCode").val(code);
			document.forms[0].submit();
		}
		</script>
	</head>
	<body style="line-height: 150%">
		<form action="${ctx }/sys/user/userList.do" method="post"
			name="userListForm" id="userListForm">
			<div style="padding: 10px 20px 0 20px;">
				<div class="box3">
					<div class="box3_topcenter">
						<div class="box3_topleft">
							<div class="box3_topright">
								<div class="box3_title">
									查询条件
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					<div class="box3_middlecenter">
						<div class="box3_middleleft">
							<div class="box3_middleright">
								<div class="box3_content4">
									<table class="tableStyle3" style="width: 100%">
										<tr>
											<th>
												用户账户：
											</th>
											<td>
												<input type="text" name="userAccount" id="userAccount"
													style="width: 175px;" class="textinput"
													value="${parameter.userAccount}" />
											</td>
											<th>
												用户姓名：
											</th>
											<td>
												<input type="text" name="userName" id="userName"
													style="width: 175px;" class="textinput"
													value="${parameter.userName}" />
											</td>
											<th>
												警 &nbsp; 号：
											</th>
											<td>
												<input type="text" name="userIdentity" id="userIdentity"
													style="width: 175px;" class="textinput"
													value="${parameter.userIdentity}" />
											</td>
										</tr>
										<tr>
											<th>
												组织机构：
											</th>
											<td>
												<acc:dic entry_key="ORG_CODE" selectName="code"
													selWidth="175" defaultValue="${parameter.orgCode}"
													dicName="organizes" selectId="code" entry_value="ORG_NAME"
													model="selectTree" />
												<input type="hidden" name="orgCode" id="orgCode" />
											</td>
											<th>
												角 &nbsp; 色：
											</th>
											<td colspan="3">
												<select name="roleId" id="roleId" selWidth="170">
													<option value=""
														<c:if test="${parameter.roleId == '' || parameter.roleId == null}">selected="selected"</c:if>>
														请选择
													</option>
													<c:if test="${not empty rolesList}">
														<c:forEach items="${rolesList}" var="rolesList">
															<option value="${rolesList.id}"
																<c:if test="${parameter.roleId == rolesList.id}">selected</c:if>>
																${rolesList.roleName}
															</option>
														</c:forEach>
													</c:if>
												</select>
											</td>
										</tr>
										<tr>
											<td height="29" colspan="6" align="center">
												<button type="button" class="buttonGray"
													onclick="queryForm();">
													<span class="icon_find">查询</span>
												</button>
												&nbsp;
												<button type="button" class="buttonGray" onclick="doClear()">
													<span class="icon_reload">清空</span>
												</button>
												&nbsp;
												<c:if
													test='<%=setUserResources.contains("/sys/user/toAddPage.do")%>'>
													<button type="button" class="buttonGray" onclick="doAdd()">
														<span class="icon_add">新增</span>
													</button>
												</c:if>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="box3_bottomcenter">
						<div class="box3_bottomleft">
							<div class="box3_bottomright"></div>
						</div>
					</div>
				</div>
				<div style="height: 10px;"></div>
				<div class="box3">
					<div class="box3_middlecenter">
						<div class="box3_middleleft">
							<div class="box3_middleright">
								<div>
									<table class="tableStyle4">
										<tr>
											<th width="25">
												序号
											</th>
											<th>
												<span>用户账户</span>
											</th>
											<th>
												<span>用户名</span>
											</th>
											<th>
												<span>警号</span>
											</th>
											<th>
												<span>角色</span>
											</th>
											<th>
												<span>所属组织机构</span>
											</th>
											<th>
												<span>是否有效</span>
											</th>
											<th>
												操作
											</th>
										</tr>
										<c:forEach items="${pageBean.resultList}" var="item"
											varStatus="status">
											<tr class="${status.count % 2==0 ?'odd':'' }">
												<td>
													${status.count }
												</td>
												<td>
													${item.userAccount}
												</td>
												<td>
													${item.userName}
												</td>
												<td>
													${item.userIdentity}
												</td>
												<td>
													<c:forEach items="${rolesList}" var="rolesList">
														<c:forEach items="${userRolesList}" var="userRolesList">
															<c:if
																test="${rolesList.id==userRolesList.roleId && userRolesList.userId==item.id}">${rolesList.roleName}</c:if>
														</c:forEach>
													</c:forEach>
												</td>
												<td>
													${AppCacheDict.organizes_dic[item.orgCode].ORG_NAME}
												</td>
												<td>
													${AppCacheDict.whether_or_not_dic[item.isEffective].DIC_VALUE_NAME}
												</td>
												<td>
													<c:if
														test='<%=setUserResources.contains("/sys/user/selectUserView.do")%>'>
														<a onclick="queryById('${item.id}')"><font
															color="#0066ae">查看</font> </a>
													</c:if>
													<c:if
														test='<%=setUserResources.contains("/sys/user/toModifyPage.do")%>'>
														<a onclick="updateById('${item.id}')"><font
															color="#0066ae">修改</font> </a>
													</c:if>
													<c:if
														test='<%=setUserResources.contains("/sys/user/deleteById.do")%>'>
														<a onclick="deleteById('${item.id}')"><font
															color="#0066ae">删除</font> </a>
													</c:if>
													<c:if
														test='<%=setUserResources.contains("/sys/user/user_resetPassword.do")%>'>
														<a onclick="resetPassword('${item.id}')"><font
															color="#0066ae">密码重置</font> </a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</table>
									<page:pageno pageBean="${pageBean}" pageId="pageNo"
										framePath="${ctx}/js/frame" formId="userListForm" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
