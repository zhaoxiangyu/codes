package com.acc.framework.util.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.acc.framework.constants.Constants;

/**
 * 
 * 分页标签类。
 * <p>页面标签封装。</p> 
 * @author 何晓超
 * @version v1.0.0
 * <p>最后更新 by 何晓超 @ 2012年12月12日 </p>
 * @since 
 */
@SuppressWarnings("unchecked")
public class PageTld extends TagSupport {
	
	/**
	 * 属性serialVersionUID的声明
	 */
	private static final long serialVersionUID = 7465200343331515019L;
	/** 分页标签ID **/
	private String pageId="";
	/** 分页基础信息 **/
	private PageBean<Object> pageBean;
	/** 分页对应JS文件夹 **/
	private String framePath="";
	/** 分页所在formID **/
	private String formId="";
	
	@Override
	public int doStartTag() throws JspException {
		 try {
			 JspWriter out =this.pageContext.getOut();
			 String outStr ="";
			 if (pageBean==null) {
				 pageBean=new PageBean<Object>();
			 }
			 int countA=1;
			 int temp=(pageBean.getPageNumber()-1)*pageBean.getPageSize();
			 if (temp>0) {
				 countA=temp;
			 }
			 int countB=(pageBean.getPageNumber())*pageBean.getPageSize();
			 if (countB>pageBean.getTotalResult()) {
				 countB=pageBean.getTotalResult();
			}
			outStr+="<script type=\"text/javascript\" src=\""+framePath+"/js/pageNumber.js\"></script>" +
					"<script type=\"text/javascript\">$(function(){$(\".pageNumber\").pageNumberRender();});$(function(){$(\"#"+pageId+"\").bind(\"pageChange\"," +
							"function(e,index){document.getElementById(\""+Constants.HTTP_PARAMETER_PAGE_NUM+"\").value = index;" +
							"document.getElementById('"+formId+"').submit();});"+
					"$(\"#"+pageId+"\").bind(\"sizeChange\"," +
							"function(e,num){document.getElementById(\"pageSize\").value = num;" +
							"document.getElementById('"+formId+"').submit();})}) </script>"+
					"<div id='div1' style='height: 35px;'>"+
					"<input type=\"hidden\" name=\""+Constants.HTTP_PARAMETER_PAGE_NUM+"\" id=\""+Constants.HTTP_PARAMETER_PAGE_NUM+"\" " +
					"value=\""+pageBean.getPageNumber()+"\" />"+
					" <input type=\"hidden\" name=\"pageSize\" id=\"pageSize\" " +
					"value=\""+pageBean.getPageSize()+"\" />"+
					"<div class='float_left' style=\"padding:5px 5px 5px 0;\">"+
					"找到"+pageBean.getTotalResult()+"条记录，显示第"+countA+"-"+countB+" 条。"+
					"</div>"+
					"<div class='float_right' style=\"padding:5px 5px 5px 0;\">"+
					"<div class='pageNumber' total='"+pageBean.getTotalResult()+"'"+
					"pageSize='"+pageBean.getPageSize()+"' page='"+pageBean.getPageNumber()+"'"+
					"showSelect='true' showInput='true' id='"+pageId+"'></div>"+
					"</div>"+
					"<div class='clear'></div>"+
					"</div>";

			out.write(outStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY; 
	}
	public PageBean<Object> getPageBean() {
		return pageBean;
	}
	public void setPageBean(PageBean<Object> pageBean) {
		this.pageBean = pageBean;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getFramePath() {
		return framePath;
	}
	public void setFramePath(String framePath) {
		this.framePath = framePath;
	}
}
