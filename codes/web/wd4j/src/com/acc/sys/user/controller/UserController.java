package com.acc.sys.user.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.acc.framework.constants.Constants;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseRole;
import com.acc.sys.commons.pojo.BaseUser;
import com.acc.sys.user.service.IUserService;

/**
 * 用户信息控制层,Controller层。 <p> </p>
 * 
 * @author 何晓超
 * @version v1.0.0 <p> 最后更新 by 何晓超 @ 2012年12月4日14:45:09 </p>
 * @since
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/sys/user")
public class UserController {

	/** 用户信息Service对象声明 */
	@Autowired
	private IUserService iUserService;

	/**
	 * 查询用户信息 。 <p> </p>
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/userList")
	public ModelAndView queryUserList(HttpServletRequest request, HttpServletResponse response, PageBean<BaseUser> pageBean) {
		// 从request请求中获得参数集合
		Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
		pageBean = iUserService.queryUsers(pageBean, param);
		ModelAndView result = new ModelAndView("/pages/sys/user/userList");
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("parameter", param);
		
		List rolesList =iUserService.selectAllRole();
		List roleUserList =iUserService.selectAllRoleUser("");
		request.setAttribute("userRolesList", roleUserList);
		request.setAttribute("rolesList", rolesList);
		
		List roleList=iUserService.selectAllRole();
		request.setAttribute("rolesList", roleList);
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
		List<BaseRole> roleList =iUserService.selectAllRole();
		request.setAttribute("rolesList", roleList);
		ModelAndView result = new ModelAndView("/pages/sys/user/userAdd");
		return result;
	}

	/**
	 * 添加用户信息。 <p> </p>
	 * 
	 * @param user User
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/insertUserInfo")
	public ModelAndView insertUserInfo(HttpServletRequest request,@ModelAttribute BaseUser user) {
		iUserService.insertUserInfo(user);
		// 新加记录的ID放到HttpServletRequest，写日志的拦截器取此ID写数据库日志
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID, user.getId());
		// 以请求的方式跳转到列表页面
		ModelAndView result = new ModelAndView("redirect:userList.do");
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
		String userId = request.getParameter("userId");
		BaseUser userInfo = iUserService.toModifyPage(userId);
		request.setAttribute("user", userInfo);
		//获取所有角色列表
		List<BaseRole> roleList =iUserService.selectAllRole();
		request.setAttribute("rolesList", roleList);
		//获取该用户角色列表
		List roleUserList =iUserService.selectAllRoleUser(userId);
		request.setAttribute("userRolesList", roleUserList);
		ModelAndView result = new ModelAndView("/pages/sys/user/userModify");
		return result;
	}

	/**
	 * 修改用户信息。 <p> </p>
	 * 
	 * @param user User
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/modifyUserInfo")
	public ModelAndView modifyUserInfo(@ModelAttribute
	BaseUser user,HttpServletRequest request) {
		iUserService.modifyUserInfo(user);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID, user.getId());
		// 以请求的方式跳转到列表页面
		ModelAndView result = new ModelAndView("redirect:userList.do");
		return result;
	}

	/**
	 * 删除用户信息 。 <p> </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/deleteById")
	public ModelAndView deleteById(HttpServletRequest request, HttpServletResponse response) {
		// 从request请求中获得参数userId
		String userId = request.getParameter("userId");
		iUserService.deleteById(userId);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID, userId);
		// 以请求的方式跳转到列表页面
		ModelAndView result = new ModelAndView("redirect:userList.do");
		return result;
	}

	/**
	 * 查看用户详情页面。 <p> </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/selectUserView")
	public ModelAndView viewSelectUser(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		BaseUser user = iUserService.selectUserView(userId);
		List rolesList =iUserService.selectAllRole();
		List roleUserList =iUserService.selectAllRoleUser(userId);
		request.setAttribute("userRolesList", roleUserList);
		request.setAttribute("rolesList", rolesList);
		request.setAttribute(Constants.HTTP_REQUEST_PARAMETER_ID, userId);
		ModelAndView result = new ModelAndView("/pages/sys/user/userView");
		request.setAttribute("user", user);
		return result;
	}

	/**
	 * 验证用户是否存在。 <p> </p>
	 * 
	 * @param validateValue   String   文本框值
	 * @param validateId  	  String   文本框ID
	 * @param validateError   String   
	 * @return String
	 */
	@RequestMapping("/checkUserAccount")
	public @ResponseBody String checkUserAccount(String validateValue,String validateId,String validateError)  {
		String account = validateValue;
		boolean isExist = iUserService.checkUserAccount(account);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}
	/**
	 * 
	 * 密码重置。
	 * <p>把密码重置成111111。</p>
	 * @param request    HttpServletRequest
	 * @param response   HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/resetPassword")
	public void resetPassword(HttpServletRequest request,HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		String userId=request.getParameter("userId");
		iUserService.resetPassword(userId);
		out.print("该用户密码成功重置为111111！");
		out.flush();
		out.close();
	}
	/**
	 * 
	 * 跳转密码修改页面。
	 * <p>方法详述（简单方法可不必详述）。</p>
	 * @param request
	 * @return
	 */
	@RequestMapping("/toUserPwd")
	public ModelAndView toUserPwd(HttpServletRequest request){
		String userId=request.getSession().getAttribute(Constants.USER_ID).toString();
		BaseUser user =iUserService.selectUserView(userId);
		request.setAttribute("user", user);
		ModelAndView result=new ModelAndView("/pages/sys/user/modifyPwd");
		return result;
	}
	/**
	 * 
	 * 验证旧密码是否正确。
	 * <p>正确返回“true”。</p>
	 * @param validateValue String
	 * @param validateId    String
	 * @param validateError String
	 * @param request       HttpServletRequest
	 * @return String
	 */
	@RequestMapping("/checkPassword")
	public @ResponseBody String checkPassword(String validateValue,String validateId,String validateError,HttpServletRequest request){
		String userId=request.getSession().getAttribute(Constants.USER_ID).toString();
		boolean isExist=iUserService.checkPassword(userId,validateValue);
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(String.valueOf(isExist));
		return jsonArray.toString();
	}
	/**
	 * 
	 * 修改密码。
	 * <p></p>
	 * @param request HttpServletRequest
	 * @param user    BaseUser
	 * @return ModelAndView
	 */
	@RequestMapping("/modifyUserpwd")
	public ModelAndView changepasswordUserpwd(HttpServletRequest request,BaseUser user){
		iUserService.modifyUserpwd(user);
		ModelAndView result=new ModelAndView("/pages/login/login");
		return result;
	} 
}
