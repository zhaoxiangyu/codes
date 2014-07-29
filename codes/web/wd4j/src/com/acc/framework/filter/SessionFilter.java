package com.acc.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.acc.framework.constants.Constants;

/**
 * 
 * session过滤器。
 * <p>用于验证session有效性的filter类。</p>
 * 
 * @author 高雷
 * @version v1.0.0
 *          <p>最后更新 by 高雷 @ 2012-12-18 </p>
 * @since
 */

public class SessionFilter implements Filter {
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("session过滤器初始化~~~~~");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		/**判断session中是否存在用户账号信息，存在则认为session有效，否则无效（跳转到登录页面）*/
		if ((req.getSession().getAttribute(Constants.USER_ACCOUNT)) != null
			 && !(req.getSession().getAttribute(Constants.USER_ACCOUNT).toString()).equals("")) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute(Constants.ERROR_MSG, "提示：session超时或登录失效，请重新登录！");
			req.getRequestDispatcher("/pages/commons/error.jsp").forward(request, response);
		}
	}

	public void destroy() {
		System.out.println("session过滤器销毁~~~~~");
	}

}
