package com.acc.sys.dictionary.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.acc.framework.constants.Constants;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseDicType;
import com.acc.sys.commons.pojo.BaseDicValue;
import com.acc.sys.dictionary.service.IDictionaryService;

/**
 * 
 * 注释标记示范类。
 * <p>说明如何注释类。</p> 
 * @author 王虎
 * @version v1.0.1
 * <p>最后更新 by 王虎 @ 2012-12-17 </p>
 * @since v1.0.0
 */

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/sys/dictionary")
public class DictionaryController {
	
	@Resource(name = "dictionaryService")
	private IDictionaryService dictionaryService;
	
	/**
	 * 查询字典类型信息 。 <p> </p>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/dicType_list")
	public ModelAndView queryDicTypeList(HttpServletRequest request, HttpServletResponse response, PageBean<BaseDicType> pageBean) {
		// 从request请求中获得参数集合
		Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
		pageBean = dictionaryService.queryDicType(pageBean, param);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("parameter", param);
		ModelAndView result = new ModelAndView("/pages/sys/dictionary/dicTypeList");
		return result;
	}
	
	/**
	 * 跳转添加页面。 <p> </p>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/toAddPage")
	public ModelAndView toAddPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView result = new ModelAndView("/pages/sys/dictionary/dicTypeAdd");
		return result;
	}
	
	/**
	 * 添加字典类型信息。 <p> </p>
	 * 
	 * @param baseDicType BaseDicType
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/insertDicTypeInfo")
	public ModelAndView insertDicTypeInfo(HttpServletRequest request,@ModelAttribute BaseDicType baseDicType) {
		dictionaryService.insertDicTypeInfo(baseDicType);
		// 以请求的方式跳转到列表页面
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,baseDicType.getId());
		ModelAndView result = new ModelAndView("redirect:dicType_list.do");
		return result;
	}
	
	/**
	 * 验证字典类型名称是否存在。 <p> </p>
	 * 
	 * @param request   HttpServletRequest
	 * @param response  HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/checkDicTypeName")
	public @ResponseBody String checkDicTypeName(String validateValue,String validateId,String validateError) throws Exception {
		String dicTypeName =  validateValue;
		boolean isExist = dictionaryService.checkDicTypeDicTypeName(dicTypeName);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}
	/**
	 * 验证字典类型编码是否存在。 <p> </p>
	 * 
	 * @param request   HttpServletRequest
	 * @param response  HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/checkDicTypeCode")
	public @ResponseBody String checkDicTypeCode(String validateValue,String validateId,String validateError) throws Exception {
		String dicTypeCode =  validateValue;
		boolean isExist = dictionaryService.checkDicTypeDicTypeCode(dicTypeCode);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}

	/**
	 * 删除字典类型 。 <p> </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/deleteDicTypeById")
	public ModelAndView deleteDicTypeById(HttpServletRequest request, HttpServletResponse response) {
		// 从request请求中获得参数userId
		String deleteDicTypeById = request.getParameter("deleteDicTypeById");
		dictionaryService.deleteDicTypeById(deleteDicTypeById);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,deleteDicTypeById);
		// 以请求的方式跳转到列表页面
		ModelAndView result = new ModelAndView("redirect:dicType_list.do");
		return result;
	}
	
	/**
	 * 跳转修改页面。 <p> </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/toModifyPage")
	public ModelAndView toModifyPage(HttpServletRequest request, HttpServletResponse response) {
		// 从request请求中获得参数userId
		String dicTypeById = request.getParameter("dicTypeById");
		BaseDicType baseDicType = dictionaryService.toModifyPage(dicTypeById);
		request.setAttribute("dicType", baseDicType);
		ModelAndView result = new ModelAndView("/pages/sys/dictionary/dicTypeModify");
		return result;
	}
	
	/**
	 * 修改字典类型信息。 <p> </p>
	 * 
	 * @param baseDicType BaseDicType
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/dicTypeModify")
	public ModelAndView modifyDicType(@ModelAttribute
			BaseDicType baseDicType,HttpServletRequest request) {
		dictionaryService.modifyDicTypeInfo(baseDicType);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,baseDicType.getId());
		// 以请求的方式跳转到列表页面
		ModelAndView result = new ModelAndView("redirect:dicType_list.do");
		return result;
	}
	
	/**
	 * 
	 * 跳转字典数据管理。
	 * <p> </p>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/toDicValuePage")
	public ModelAndView queryDicValue(HttpServletRequest request,
			HttpServletResponse response){
		//  树形结构
		Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
		String dicTypeId = (String)param.get("dicTypeId");
		if(param.get("parent_id")==null){
			param.put("parent_id","0");
		}
		List dicValueList=dictionaryService.queryDicValue(param);
		ModelAndView result = new ModelAndView("/pages/sys/dictionary/dicValueList"); 
		result.addObject("dicTypeId", dicTypeId);
		result.addObject("dicValueList",dicValueList);
		return result;
	}
	
	/**
	 * 
	 *异步加载字典数据树。
	 * <p> </p>
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/dicValueJson")
	public void queryDicValueJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		//  树形结构
		Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
		if(param.get("parent_id")==null){
			param.put("parent_id","0");
		}
		String strJson=dictionaryService.queryDicValueJson(param);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(strJson);
		out.close();
	}
	
	/**
	 * 
	 * 新增字典数据。
	 * <p> </p>
	 * @param baseDicValue
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/insertDicValueInfo")
	public void insertDicValueInfo(HttpServletRequest request,HttpServletResponse response,@ModelAttribute BaseDicValue baseDicValue) throws IOException {
		int i = dictionaryService.insertDicValueInfo(baseDicValue);
		// 以请求的方式跳转到列表页面
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,baseDicValue.getId());
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(i>0){
			out.write("true");
			out.close();
		}else{
			out.write("false");
			out.close();
		}
	}
	/**
	 * 
	 * 字典数据修改。
	 * <p> </p>
	 * @param baseDicValue
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/dicValueModify")
	public void modifyDicValue(HttpServletResponse response,@ModelAttribute
			BaseDicValue baseDicValue,HttpServletRequest request) throws IOException {
		SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			baseDicValue.setModifyTime(newFormat.parse(newFormat.format(new Date())));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = dictionaryService.modifyDicValueInfo(baseDicValue);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,baseDicValue.getId());
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(i>0){
			out.write("true");
			out.close();
		}else{
			out.write("false");
			out.close();
		}
	}
	/**
	 * 
	 * 删除字典数据。
	 * <p> </p>
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/deleteDicValueById")
	public void deleteDicValueById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 从request请求中获得参数userId
		String id = request.getParameter("id");
		int i= dictionaryService.deleteDicValueById(id);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID ,id);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(i>0){
			out.write("true");
			out.close();
		}else{
			out.write("false");
			out.close();
		}
	}
	
	/**
	 * 
	 * 核对字典值名称是否存在。
	 * <p> </p>
	 * 
	 * @return String
	 */
	@RequestMapping(value="/checkDicValueName",method=RequestMethod.POST)
	public @ResponseBody String checkDicValueName(String validateValue,String validateId,String validateError,String level){
		String dicName=validateValue;
		String[] param = level.split(",");
		boolean isExist=dictionaryService.checkDicValueName(dicName, param[0],param[1]);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}
	
	/**
	 * 
	 * 核对字典值编码是否存在。
	 * <p> </p>
	 * 
	 * @return String
	 */
	@RequestMapping(value="/checkDicValueCode",method=RequestMethod.POST)
	public @ResponseBody String checkDicValueCode(String validateValue,String validateId,String validateError,String level){
		String dicCode=validateValue;
		String[] param = level.split(",");
		boolean isExist=dictionaryService.checkDicValueCode(dicCode, param[0],param[1]);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}
	
	@RequestMapping("/showDicValueTree")
	public ModelAndView showDicValueTree(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> param = WebUtils.getParametersStartingWith(request, "");
		ModelAndView result = new ModelAndView();
		List  dicValueList = dictionaryService.selectDicValueList(param);
		List dicIdByParent = dictionaryService.selectDicIdByParent(request.getParameter("dicId").toString());
		result.addObject("dicValueList",dicValueList);
		result.addObject("pId",param.get("pId"));
		request.setAttribute("dicIdByParent", dicIdByParent);
		request.setAttribute("dicId",request.getParameter("dicId"));
		result.setViewName("/pages/sys/dictionary/dicValueTree");
		return result;
	}
	
	/**
	 * 修改显示顺序
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/changeOrder")
	public void changeOrder(HttpServletRequest  request,HttpServletResponse response) throws IOException{
		Map<String,Object> param = WebUtils.getParametersStartingWith(request, "");
		int i = dictionaryService.changeOrder(param);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		if(i>0){
			out.write("true");
			out.close();
		}else{
			out.write("false");
			out.close();
		}
	}
	/**
	 * 
	 * 异步加载行政区划树形列表。
	 * <p> </p>
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/xzqhJson")
	public void queryXzqhJson(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String,Object> param = WebUtils.getParametersStartingWith(request, "");
		String strJson = dictionaryService.selectXzqhJson(param);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.write(strJson);
		out.close();
	}
	
}
