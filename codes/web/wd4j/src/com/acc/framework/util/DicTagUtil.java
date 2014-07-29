package com.acc.framework.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 字典标签的实现类。
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-18 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public class DicTagUtil extends TagSupport {

	private static final long serialVersionUID = -1479595932677075771L;
	private static final String DIC_ENTRY_KEY = "DIC_VALUE_CODE";
	private static final String DIC_ENTRY_VALUE = "DIC_VALUE_NAME";

	private static final String ORG_ENTRY_KEY = "ORG_CODE";
	private static final String ORG_ENTRY_VALUE = "ORG_NAME";
	
	private static final String ORG_DIC_NAME = "organizes";

	private String dicName;

	private String defaultValue = "";

	private String selectName;

	private String selectId;

	private String entry_key;

	private String entry_value;

	private boolean multiple = false;
	
	private String valueName="";
	
	private String model;

	private String returnResult="";
	
	private String whereClause="";
	
	private String selWidth="";
	
	private String openTree="";
	
	private String event="";
	
	public String getSelWidth() {
		return selWidth;
	}

	public void setSelWidth(String selWidth) {
		this.selWidth = selWidth;
	}

	public String getWhereClause() {
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public String getSelectName() {
		return selectName;
	}

	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		if(dicName != null && dicName.equals(ORG_DIC_NAME)){
			entry_key = ORG_ENTRY_KEY;
			entry_value = ORG_ENTRY_VALUE;
		}else{
			entry_key = DIC_ENTRY_KEY;
			entry_value = DIC_ENTRY_VALUE;
		}
		if(defaultValue == null){
			defaultValue = "";
		}
		if(selectName == null){
			selectName = selectId;
		}
		returnResult = "";
		
		try {
			JspWriter out = this.pageContext.getOut(); 
			ServletContext servletContext = pageContext.getServletContext();
			//取得字典缓存对象
			Map<String, Map<String,Map<String, Object>>> appCacheDict = (Map<String, Map<String,Map<String, Object>>>) servletContext.getAttribute("AppCacheDict");
			Object dicObj=null;
			if(appCacheDict!=null&&appCacheDict.get(dicName+DicCacheUtil.DIC_KEY_END)!=null){
				dicObj=appCacheDict.get(dicName+DicCacheUtil.DIC_KEY_END);
			}else{
				dicObj=pageContext.getRequest().getAttribute(dicName);
			}
			
			String outString="";
			Map<String,Map<String, Object>> dicMap=(Map<String,Map<String, Object>>)dicObj;
			if(model.toUpperCase().equals("POPUP")){
				outString+=getPopupElement();
			}else if(model.toUpperCase().equals("SELECT")){
				List<Map<String, Object>> dicList = DicCacheUtil.convertMapToList(dicMap);
				outString+=getSelectElement(dicList,null);
			}else if(model.toUpperCase().equals("SELECTTREE")){
				List<Map<String, Object>> dicList = DicCacheUtil.convertMapToList(dicMap);
				outString+=getSelectTreeElement(dicList,dicMap);
			}
			out.print(outString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY; 
	}
	
	@Override
	public int doEndTag() throws JspException {
		reset();
		return super.doEndTag();
	}

	private void reset(){
		defaultValue = null;
		entry_key = null;
		entry_value = null;
		selectId = null;
		selectName = null;
		multiple = false;
	}
	/**
	 * 
	 * 生成弹出窗口形式的选择框。
	 * <p></p>
	 * @return 弹出窗口形式的选择框的HTML
	 */
	private String getPopupElement(){
		returnResult="<input type=\"text\" id=\"dic_"+dicName+"\" class=\"textinput\" kind=\"dic\" code=\"\" style=\"width:" + selWidth + 
					"px\" dic_src=\"dic_"+dicName+"\" name=\"dic_"+dicName+"\" onpropertychange=\"doPropChange()\" " +
					"onfocus=\"doFocus()\" onblur=\"doBlur()\" "+event+" onkeydown=\"doKeyPress()\" initCodes=\""+whereClause+"\"" +
					" value=\""+valueName+"\"";
		returnResult+="/><br/>" +
				"<input name=\""+selectName+"\" id=\""+selectId+"\" type=\"hidden\" value=\""+defaultValue+"\" />";
		return returnResult;
	}
	
	/**
	 * 
	 * 生成下拉列表形式的选择框。
	 * <p></p>
	 * @param dicList 字典项的列表 
	 * @param dicMap
	 * @return 下拉框形式的选择框的HTML
	 */
	private String getSelectElement(List<Map<String, Object>> dicList,Map<String,Map<String, Object>> dicMap){
		if(isMultiple()){
			returnResult = "";
		}
		String []codes= new String[]{};
		if (!whereClause.equals("")) {
			codes=whereClause.split(",");
		}
		if (multiple) {
		String idName="ID";
		String codeName="DIC_VALUE_CODE";
		if(dicName != null && dicName.equals(ORG_DIC_NAME)){
			idName="ORG_ID";
			codeName="ORG_CODE";
		}
		
		String data="[";
		for (int i = 0; i < dicList.size(); i++) {
			Map map = (Map<String, Object>) dicList.get(i);
			if(codes.length>0){
				for (int j = 0; j < codes.length; j++) {
					if (codes[j].indexOf("*")!=-1) {
						if (codes[j].substring(codes[j].length()-1).equals("*")) {
							codes[j]+=" *";
						}
						String [] codeStr=codes[j].split("\\*");
						String code=map.get(entry_key).toString();
						int type=0;
						for (int k = 0; k < codeStr.length; k++) {
							codeStr[k]=codeStr[k].replace(" ","");
							if (k==0&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])==0) {
								code=code.replace(codeStr[k], " ");
								type=1;
							}else
							if(k!=0&&k!=(codeStr.length-1)&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])!=-1){
								String [] strs=code.split(codeStr[k]);
								code=strs[strs.length-1];
							//code=code.replace(codeStr[k], " ");
							type=1;
							}else if(k==(codeStr.length-1)&&!codeStr[k].equals("")&&code.substring((code.length()-codeStr[k].length())).equals(codeStr[k])){
								code=code.replace(codeStr[k], " ");
								type=1;
							}else {
								if(!codeStr[k].equals("")) {type=0;
								break;
								}
							}
						}
						if(type==1){
							data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\"0\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \"true\"},";
						}
					}else{
					if (map.get(codeName).equals(codes[j])) {
						data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\"0\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \"true\"},";
					}
					}
				}
			}else {
			data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\"0\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \"true\"},";
			}
		}
		data=data.substring(0,data.length()-1);
		if (!data.equals("")) {
			data+="]";
		}else {
			data="[]";
		}
		
		
		returnResult+="<div selWidth=\"200\" name=\""+selectName+"\" id=\""+selectId+"\" noGroup=\"true\" class=\"selectTree\" " ;
		
			returnResult+="multiMode=\"true\" ";
		
		returnResult+=	" data='{\"treeNodes\": "+data+"}'></div>";
		String defString="";
		if (defaultValue!=null&&!defaultValue.equals("")) {
			defString=dicMap.get(defaultValue).get(idName)+"";
		}
		returnResult+=" <script>$(function(){if(\""+defaultValue+"\"!=\"\"&& \""+defaultValue+"\"!=null){$(\"#"+selectId+"\").setValue('"+defString+"');$(\"#"+selectId+"\").attr(\"relCode\",\""+defaultValue+"\")}});</script>";
		}else {
			returnResult+="<select selWidth=\""+selWidth+"\"  "+event+" name=\""+selectName+"\" type=\"select-one\" id=\""+selectId+"\" ";
			returnResult += "><option value=\"\">请选择</option>";
			Map<String, Object> map =null;
			if(!StringUtil.isEmpty(entry_key)&&StringUtil.isEmpty(entry_value)){
				entry_key="DIC_VALUE_CODE";
				entry_value="DIC_VALUE_NAME";
			}
			if(dicList!=null){
				for(int i=0;i<dicList.size();i++){
					map = (Map<String, Object>) dicList.get(i);
					String selected="";
					if(defaultValue.equals(map.get(entry_key).toString())){
						selected="selected";
					}
					if (codes.length>0) {
						for (int j = 0; j < codes.length; j++) {
							if (codes[j].indexOf("*")!=-1) {
								if (codes[j].substring(codes[j].length()-1).equals("*")) {
									codes[j]+=" *";
								}
								String [] codeStr=codes[j].split("\\*");
								String code=map.get(entry_key).toString();
								int type=0;
								for (int k = 0; k < codeStr.length; k++) {
									codeStr[k]=codeStr[k].replace(" ","");
									if (k==0&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])==0) {
										code=code.replace(codeStr[k], " ");
										type=1;
									}else
									if(k!=0&&k!=(codeStr.length-1)&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])!=-1){
										String [] strs=code.split(codeStr[k]);
										code=strs[strs.length-1];
										//code=code.replace(codeStr[k], " ");
									type=1;
									}else if(k==(codeStr.length-1)&&!codeStr[k].equals("")&&code.substring((code.length()-codeStr[k].length())).equals(codeStr[k])){
										code=code.replace(codeStr[k], " ");
										type=1;
									}else {
										if(!codeStr[k].equals("")) {type=0;
										break;
										}
									}
								}
								if(type==1){
									returnResult=returnResult+"<option value=\""+map.get(entry_key).toString()+"\" "+selected+">"+map.get(entry_value).toString()+"</option>";
								}
							}else{
							if (map.get(entry_key).equals(codes[j])) {
								returnResult=returnResult+"<option value=\""+map.get(entry_key).toString()+"\" "+selected+">"+map.get(entry_value).toString()+"</option>";
							}
							}
							}
					}else {
						returnResult=returnResult+"<option value=\""+map.get(entry_key).toString()+"\" "+selected+">"+map.get(entry_value).toString()+"</option>";
					}
					
				}
			}
			if(dicMap!=null){
				for(Map.Entry<String,Map<String, Object>> row_map : dicMap.entrySet()){
					map=row_map.getValue();
					String selected="";
					if(defaultValue.equals(map.get(entry_key).toString())){
						selected="selected";
					}
					returnResult+="<option value=\""+map.get(entry_key).toString()+"\" "+selected+">"+map.get(entry_value).toString()+"</option>";
				}
			}
			returnResult=returnResult+"</select>";
		}
		return returnResult;
	}
	
	private String getSelectTreeElement(List<Map<String, Object>> dicList,Map<String,Map<String, Object>> dicMap){
		String idName="ID";
		if(dicName != null && dicName.equals(ORG_DIC_NAME)){
			idName="ORG_ID";
		}
		String []codes= new String[]{};
		if (!whereClause.equals("")) {
			codes=whereClause.split(",");
		}
		if(!StringUtil.isEmpty(entry_key)&&StringUtil.isEmpty(entry_value)){
			entry_key="DIC_VALUE_CODE";
			entry_value="DIC_VALUE_NAME";
		}
		if(openTree==null || openTree.equals("")){
			openTree="true";
		}
		String data="[";
		for (int i = 0; i < dicList.size(); i++) {
			Map map = (Map<String, Object>) dicList.get(i);
			if (codes.length>0) {
				for (int j = 0; j < codes.length; j++) {
					if (codes[j].indexOf("*")!=-1) {
						if (codes[j].substring(codes[j].length()-1).equals("*")) {
							codes[j]+=" *";
						}
						String [] codeStr=codes[j].split("\\*");
						String code=map.get(entry_key).toString();
						int type=0;
						for (int k = 0; k < codeStr.length; k++) {
							codeStr[k]=codeStr[k].replace(" ","");
							if (k==0&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])==0) {
								code=code.replace(codeStr[k], " ");
								type=1;
							}else
							if(k!=0&&k!=(codeStr.length-1)&&!codeStr[k].equals("")&&code.indexOf(codeStr[k])!=-1){
								String [] strs=code.split(codeStr[k]);
								code=strs[strs.length-1];
								//code=code.replace(codeStr[k], " ");
							type=1;
							}else if(k==(codeStr.length-1)&&!codeStr[k].equals("")&&code.substring((code.length()-codeStr[k].length())).equals(codeStr[k])){
								code=code.replace(codeStr[k], " ");
								type=1;
							}else {
								if(!codeStr[k].equals("")) {type=0;
								break;
								}
							}
						}
						if(type==1){
							data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\"0\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \""+openTree+"\"},";
						}
					}else{
					if (map.get(entry_key).equals(codes[j])) {
						data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\""+map.get("PARENT_ID")+"\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \""+openTree+"\"},";
					}
					}
				}
			}else {
				data+="{\"id\":\""+map.get(idName)+"\", \"pId\":\""+map.get("PARENT_ID")+"\", \"name\":\""+map.get(entry_value)+"\",\"code\":\""+map.get(entry_key)+"\", \"open\": \""+openTree+"\"},";
			}
		}
		data=data.substring(0,data.length()-1);
		if (!data.equals("")) {
			data+="]";
		}else {
			data="[]";
		}
		
		returnResult+="<div selWidth=\"" + selWidth + "\" name=\""+selectName+"\" id=\""+selectId+"\" class=\"selectTree\" " ;
		if (multiple) {
			returnResult+="multiMode=\"true\" ";
		}
		returnResult+=	" data='{\"treeNodes\": "+data+"}'></div>";
		String defString="";
		if (defaultValue!=null&&!defaultValue.equals("")) {
			if (defaultValue.indexOf(",")!=-1) {
				String[] dicVal=defaultValue.split(",");
				for (int i = 0; i < dicVal.length; i++) {
					if(dicVal[i]!=null&&!dicVal[i].equals("")){
					defString+=dicMap.get(dicVal[i]).get(idName)+"";
					if (i<(dicVal.length-1)) {
						defString+=",";
					}
				}
				}
			}else{
				if (dicMap.get(defaultValue)!=null && !dicMap.get(defaultValue).equals("")) {
					defString=dicMap.get(defaultValue).get(idName)+"";
				}
			}
			
		}
		returnResult+=" <script>$(function(){if(\""+defaultValue+"\"!=\"\"&& \""+defaultValue+"\"!=null){$(\"#"+selectId+"\").attr(\"selectedValue\",\""+defString+"\");$(\"#"+selectId+"\").attr(\"relCode\",\""+defaultValue+"\");$(\".selectTree\").selectTreeRender()}});</script>";
		return returnResult;
	}
	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public String getEntry_key() {
		return entry_key;
	}

	public void setEntry_key(String entry_key) {
		this.entry_key = entry_key;
	}

	public String getEntry_value() {
		return entry_value;
	}

	public void setEntry_value(String entry_value) {
		this.entry_value = entry_value;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getOpenTree() {
		return openTree;
	}

	public void setOpenTree(String openTree) {
		this.openTree = openTree;
	}
	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
}
