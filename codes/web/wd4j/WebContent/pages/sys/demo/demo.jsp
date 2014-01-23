<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="../../commons/include.jsp"%>
<html>
	<head>
		<title>My JSP 'demo.jsp' starting page</title>
		<%@ include file="../../commons/frame.jsp"%>
	</head>
	<body style="line-height: 150%">
		<div id="scrollContent">
			<div class="box1" panelTitle="" roller="false">
				<table>
					<tr>
						<td width="33%">
							<fieldset>
								<legend>
									普通下拉框字典实例
								</legend>
								<acc:dic entry_key="ORG_CODE" selectName="orgCode"
									defaultValue="" dicName="organizes" model="select"
									selectId="orgCode1" entry_value="ORG_NAME"
									multiple="false"/>
							</fieldset>
						</td>
						<td width="33%">
							<fieldset>
								<legend>
									多选下拉框字典实例
								</legend>
								<acc:dic entry_key="ORG_CODE" selectName="orgCode"
									defaultValue="" dicName="organizes" model="select"
									selectId="orgCode2" entry_value="ORG_NAME"
									multiple="true" />
							</fieldset>
						</td>
						<td width="33%">
							<fieldset>
								<legend>
									单选树形下拉框字典实例
								</legend>
								<table>
									<tr>
										<td>
											<acc:dic entry_key="ORG_CODE" selectName="orgCode"
												defaultValue="" dicName="organizes" model="selectTree"
												selectId="selectTree1" entry_value="ORG_NAME"
												multiple="false" />
										</td>
										<td>
											<button type="button" onclick="getSelectValue()">
												获取值
											</button>
											<button type="button" onclick="setSelectValue()">
												选中值
											</button>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
					<tr>
						<td width="33%">
							<fieldset>
								<legend>
									多选树形下拉框字典实例
								</legend>
								<table>
									<tr>
										<td>
											<acc:dic entry_key="ORG_CODE" selectName="orgCode"
												defaultValue="" dicName="organizes" model="selectTree"
												selectId="selectTree2" entry_value="ORG_NAME"
												multiple="true" />
										</td>
										<td>
											<button type="button" onclick="getMultSelectValue()">
												获取值
											</button>
											<button type="button" onclick="setMultiSelectValue()">
												选中值
											</button>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
						<td width="33%">
							<fieldset>
								<legend>
									弹出下拉框字典实例
								</legend>
								<acc:dic entry_key="ORG_CODE" selectName="orgCode"
									defaultValue="" dicName="organizes" model="popup"
									selectId="orgCode3" entry_value="ORG_NAME"
									multiple="false"  />
							</fieldset>
						</td>
						<td>
						</td>
						<td></td>
					</tr>
				</table>
				<br />
			</div>
		</div>
	</body>
</html>
<script>
			function setSelectValue(){
			//$("#selectTree1").addItem({id:100, parentId:2, name:"测试数据"});
	   		$("#selectTree1").setValue("2"); 
			}
			function setMultiSelectValue(){
			//$("#selectTree2").addItem({id:100, parentId:2, name:"测试数据"});
	   		$("#selectTree2").setValue("3,4"); 
			}
			function getSelectValue(){
				var val=$("#selectTree1").attr("relValue");
				var text=$("#selectTree1").attr("relText");
				var relCode=$("#selectTree1").attr("relCode");
				alert("选中项:"+text+"\n选中值:"+val+"\ncode:"+relCode);
			}
			function getMultSelectValue(){
				var val=$("#selectTree2").attr("relValue");
				var text=$("#selectTree2").attr("relText");
				var relCode=$("#selectTree2").attr("relCode");
				alert("选中项:"+text+"\n选中值:"+val+"\ncode:"+relCode);
			}
			function getDicSelectValue(){
				var val=$("#selectTree3").attr("relValue");
				var text=$("#selectTree3").attr("relText");
				var relCode=$("#selectTree3").attr("relCode");
				alert("选中项:"+text+"\n选中值:"+val+"\ncode:"+relCode);
			}
			function setDicSelectValue(){
				//$("#selectTree2").addItem({id:100, parentId:2, name:"测试数据"});
		   		$("#selectTree3").setValue("2"); 
			}
</script>