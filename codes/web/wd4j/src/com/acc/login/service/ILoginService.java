package com.acc.login.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

/**
 * 登陆接口。
 * <p> </p>
 * 
 * @author 张琦
 * @version v1.0.0
 * <p>最后更新 by 张琦 @ 2012-12-12</p>
 */
@Component
@SuppressWarnings("unchecked")
public interface ILoginService {

	/**
	 * 
	 * 登陆方法。
	 * <p> </p>
	 * @param param 登陆参数
	 * @param session
	 * @param request
	 * @return
	 */
	public Map login(Map param, HttpSession session, HttpServletRequest request);
	
	/**
	 * 用户退出。
	 * <p>清空session，保证页面跳转到登录页面时session失效。</p>
	 * @param session
	 * @return
	 */
	public boolean logout( HttpSession session);
	
}
