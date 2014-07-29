package com.acc.login.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.acc.framework.constants.Constants;
import com.acc.login.service.ILoginService;

/**
 * 
 * 登陆的控制类。
 * <p> </p> 
 * @author 张琦
 * @version v1.0.0
 * <p>最后更新 by 张琦 @ 2012-12-12 </p>
 */
@Controller
@SuppressWarnings("unchecked")
public class LoginController {
	
	@Autowired
	private ILoginService iLoginService;

	/**
	 * 用户登录
	 * <p></p>
	 * @param request
	 * @param response
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws UnknownHostException {
		ModelAndView mav = new ModelAndView();
		//license验证
		/*
		String licenseInfo = (String)request.getSession().getServletContext().getAttribute("licenseInfo");
		if(!LicenseConstant.LICENSE_SUCCESS.equals(licenseInfo)){
			mav.addObject("licenseError", licenseInfo);
			mav.setViewName("/pages/login/login");
			return mav;
		}
		*/
		//得到session
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName")==null?"":request.getParameter("userName").toString();
		String password = request.getParameter("password")==null?"":request.getParameter("password").toString();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userName",userName);
		param.put("password",password);
		Map rsMap = iLoginService.login(param,session,request);
		
		String loginResult = (String)rsMap.get(Constants.LOGIN_INFO);
		if(loginResult.equals(Constants.LOGIN_SUCCESS)){
				mav.setViewName("/pages/main");
		}
		else{ 
			if(loginResult.equals(Constants.USER_NOT_EXIST)){
				mav.addObject(Constants.ERROR_MSG, "该用户未注册！");
			}else if(loginResult.equals(Constants.PASS_ERROR)){
				mav.addObject(Constants.ERROR_MSG, "密码错误！");
			}else if(loginResult.equals(Constants.IP_LIMITED)){
				mav.addObject(Constants.ERROR_MSG, "您的IP没有权限访问！");
			}else if(loginResult.equals(Constants.USER_INEFFECTIVE)){
				mav.addObject(Constants.ERROR_MSG, "您输入的用户名已经失效，请联系管理员！");
			}else if(loginResult.equals(Constants.ORG_INEFFECTIVE)){
				mav.addObject(Constants.ERROR_MSG, "您输入用户的组织机构已经失效，请联系管理员！");
			}
			mav.setViewName("/pages/login/login");
		}
		return mav;
	}
	
	
	/**
	 * 用户退出系统
	 * <p></p>
	 * @param request
	 * @param response
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws UnknownHostException {
		ModelAndView mav = new ModelAndView();
		//得到session
		HttpSession session = request.getSession();
		iLoginService.logout(session);
		mav.setViewName("/pages/login/login");
		
		return mav;
	}
	
}
