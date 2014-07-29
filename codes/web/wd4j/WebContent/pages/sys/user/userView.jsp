<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 用户详情页面
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
		<title>用户信息查看</title>
		<%@ include file="../../commons/frame.jsp"%>
		<script>$import("md5");</script>
	</head>
	<body>
		<div style="padding: 10px 20px 0 20px;">
						<table style="width: 100%;">
							<tr>
								<td colspan="4" class="ver01">
									<div class="box3">
										<div class="box3_topcenter">
											<div class="box3_topleft">
												<div class="box3_topright">
													<div class="box3_title">
														用户详细信息
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
						${user.userAccount}
					</td>
					<th>
						用户名：
					</th>
					<td>
						${user.userName}
					</td>
				</tr>
				<tr>
					<th>
						警号：
					</th>
					<td>
						${user.userIdentity}
					</td>
					<th>
						性别：
					</th>
					<td>
						${AppCacheDict.sex_dic[user.userSex].DIC_VALUE_NAME}
					</td>
				</tr>
				<tr>
					<th>
						身份证号：
					</th>
					<td>
						${user.userIdCard}
					</td>
					<th>
						联系电话：
					</th>
					<td>
						${user.userPhone}
					</td>
				</tr>
				<tr>
					<th>
						所属组织机构：
					</th>
					<td>
						${AppCacheDict.organizes_dic[user.orgCode].ORG_NAME}
					</td>
					<th>
						角色：
					</th>
					<td>
						<c:forEach items="${rolesList}" var="rolesList">
							<c:forEach items="${userRolesList}" var="userRolesList">
								<c:if test="${rolesList.id==userRolesList.roleId}">${rolesList.roleName}</c:if>
							</c:forEach>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<th>
						是否有效：
					</th>
					<td>
						${AppCacheDict.whether_or_not_dic[user.isEffective].DIC_VALUE_NAME}
					</td>
					<th>
						名称的汉字拼音：
					</th>
					<td colspan=3>
						${user.chSpell}
					</td>
				</tr>
				<tr>
					<th>
						住址：
					</th>
					<td colspan="3">
						${user.userAddress}
					</td>
				</tr>
				<tr>
					<th>
						创建时间：
					</th>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${user.createTime}" />
					</td>
					<th>
						最后修改时间：
					</th>
					<td>
						<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
							value="${user.modifyTime}" />
					</td>
				</tr>
			</table>
			<tr>
					<td colspan="4" align="center">
						<button type="button" class="buttonGray" onclick="top.Dialog.close()">
							关闭
						</button>
					</td>
				</tr>
				</table>
		</div>
	</body>
</html>
