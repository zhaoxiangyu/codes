<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- 
 * 系统日志列表页面
 * <p> </p> 
 * @author 何龙
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
		<title>日志查询</title>
		<%@ include file="../../commons/frame.jsp"%>
		<script>
	function clearForm(){
		document.forms['logQueryForm'].reset();
		$('#operateUserAccount').val("");
		$('#userName').val("");
		$('#beginTime').val("");
		$('#endTime').val("");
		$('#operateModule').val("");
		$('#operateType').val("");
	}
</script>
	</head>
	<body style="line-height: 150%">
		<form id="logQueryForm" action="${ctx }/sys/syslog/logList.do"
			method="post">
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
												操作人账号：
											</th>
											<td>
												<input type="text" name="operateUserAccount" style="width: 175px;" class="textinput"
													id="operateUserAccount" maxlength="50"
													value="${parameter.operateUserAccount}" />
											</td>
											<th>
												操作模块：
											</th>
											<td>
												<acc:dic selectId="operateModule" dicName="operate_module" selWidth="170"
													defaultValue="${parameter.operateModule}" model="SELECT" />
											</td>
											<th>
												操作类型：
											</th>
											<td>
												<acc:dic selectId="operateType" dicName="operate_type" selWidth="170"
													defaultValue="${parameter.operateType}" model="select" />
											</td>
										</tr>
										<tr>
											<th>
												用户名：
											</th>
											<td>
												<input type="text" name="userName" id="userName" style="width: 175px;" class="textinput"
													maxlength="50" value="${parameter.userName }" />
											</td>
											<th>
												开始时间：
											</th>
											<td>
												<input type="text" name="beginTime" id="beginTime"
													value="${parameter.beginTime }" class="dateIcon" style="width: 175px;"
													onclick="WdatePicker({ skin:'blue',dateFmt:'yyyy-MM-dd HH:mm:ss' });" />
												<div id="begintime"></div>
											</td>
											<th>
												结束时间：
											</th>
											<td>
												<input type="text" name="endTime" id="endTime"
													value="${parameter.endTime }" class="dateIcon" style="width: 175px;"
													onclick="WdatePicker({ skin:'blue',dateFmt:'yyyy-MM-dd HH:mm:ss' });" />
												<div id="begintime"></div>
											</td>
										</tr>
										<tr>
											<td height="29" colspan="6" align="center">
												<button type="button" class="buttonGray"
													onclick="document.forms['logQueryForm'].submit();">
													<span class="icon_find">查询</span>
												</button>
												&nbsp;
												<button type="button" class="buttonGray" onclick="clearForm();">
													<span class="icon_reload">清空</span>
												</button>
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
													<span>操作人账号</span>
												</th>
												<th>
													<span>用户名</span>
												</th>
												<th>
													<span>操作模块</span>
												</th>
												<th>
													<span>操作类型</span>
												</th>
												<th>
													<span>IP</span>
												</th>
												<th>
													<span>操作时间</span>
												</th>
											</tr>
											<c:forEach items="${page.resultList}" var="item"
												varStatus="Status">
												<tr>
													<td>
														${Status.count }
													</td>
													<td>
														${item.operateUserAccount}
													</td>
													<td>
														${item.operateUserName}
													</td>
													<td>
														${AppCacheDict.operate_module_dic[item.operateModule].DIC_VALUE_NAME}
													</td>
													<td>
														${AppCacheDict.operate_type_dic[item.operateType].DIC_VALUE_NAME}
													</td>
													<td>
														${item.ip}
													</td>
													<td>
														<fmt:formatDate value="${item.operateTime}"
															pattern="yyyy-MM-dd HH:mm:ss" />
													</td>
												</tr>
											</c:forEach>
										</table>
										<page:pageno pageId="pageNo" framePath="${ctx}/js/frame"
												formId="logQueryForm" pageBean="${pageBean}"></page:pageno>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>

