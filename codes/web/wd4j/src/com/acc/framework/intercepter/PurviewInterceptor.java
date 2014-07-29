package com.acc.framework.intercepter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.acc.framework.constants.Constants;
import com.acc.framework.util.Logger;
import com.acc.framework.util.UUIDGeneratorUtil;
import com.acc.sys.commons.dao.BaseSysLogDao;
import com.acc.sys.commons.pojo.BaseSysLog;


/**
 * 
 * 用于权限的拦截器，拦截SpringMVC请求。
 * <p></p> 
 * @author 王虎
 * @version v1.0.1
 * <p>最后更新 by 王虎 @ 2013-1-17 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public class PurviewInterceptor extends HandlerInterceptorAdapter {	
	private Logger logger = new Logger(PurviewInterceptor.class);
	@Autowired
	private BaseSysLogDao sysLogDao;
	public static final String PREF_LOGOUT = "logout";
	
	/**
	 * 
	 * 构造函数
	 */
	public PurviewInterceptor(){
		logger.debug("new PurviewInterceptor");
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,Object handler) throws ServletException, IOException{
		HttpServletRequest req = (HttpServletRequest) request;
		boolean isExist = true;
		//过滤静态页面请求
		if(handler instanceof HandlerMethod ){
			logger.debug("request's url:"+request.getRequestURI());
			if(request.getSession().getAttribute(Constants.USER_RESOURCES) !=null){
				//获取url
				String url = request.getRequestURI();
				//去掉工程名与数据库资源名格式统一，例如：/framework/	
				String urlNew = url.substring(request.getContextPath().length() + 1);	 
				//获取该用户被禁用的资源
				Set<String> forbiddenResources = (HashSet<String>) request.getSession().getAttribute(Constants.USER_RESOURCES_FORBIDDEN);
				//判断该资源是否被禁用
				isExist = !forbiddenResources.contains(urlNew);
				if(!isExist){
					request.setAttribute(Constants.ERROR_MSG, "提示：你没有该功能权限，请向管理员申请！");
					req.getRequestDispatcher("/pages/commons/error.jsp").forward(request, response);
				}
			}
			
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			logger.debug("request's url:"+request.getRequestURI());
			logger.debug("handler's type:"+handler.getClass());

			/** WebInterceptor已经将有用信息记录到日志表中，在此删除一下内容 2013-5-14
			BaseSysLog sysLog = new BaseSysLog();
			String classStr = handlerMethod.getBeanType().getName();
		
			String id = UUIDGeneratorUtil.getUUID();
			sysLog.setId(id);

			String classStrs[] = classStr.split("\\.");
			StringBuffer operateModule = new StringBuffer();
			for(int i=2;i < classStrs.length - 2; i++){
				if(i!=2){
					operateModule.append(".");
				}
				operateModule.append(classStrs[i]);
			}
			sysLog.setOperateModule(operateModule.toString());

			String methodStr = handlerMethod.getMethod().getName();
			if(null != methodStr && !"".equals(methodStr)){
				if(methodStr.startsWith(PREF_LOGOUT)){
					sysLog.setOperateType(PREF_LOGOUT);
				}
			}
			
			Object[] jp = handlerMethod.getMethodParameters();
			List<Object> paramList = new ArrayList<Object>();
			if(jp != null ){			
				for (int i = 0; i < jp.length; i++) {
					MethodParameter mp = (MethodParameter)jp[i];
					logger.debug(""+mp.getParameterType());
					paramList.add(jp[i]);
				}
				String tempStr = paramList.toString();
				if(tempStr.length() > 1000){
					tempStr = tempStr.substring(0,1000);
				}
				sysLog.setParameter(tempStr);
			}
			
			String ip = "";
			if(!request.getRemoteAddr().equals("") && request.getRemoteAddr() != null){
				ip = request.getRemoteAddr();
			}
			sysLog.setIp(ip);

			String operateUserId = "";
			if(request.getSession().getAttribute(Constants.USER_ID) != null){
				operateUserId = request.getSession().getAttribute(Constants.USER_ID).toString();
			}
			sysLog.setOperateUserId(operateUserId);
			
			String operateUserAccount = "";
			if(request.getSession().getAttribute(Constants.USER_ACCOUNT) != null){
				operateUserAccount= request.getSession().getAttribute(Constants.USER_ACCOUNT).toString();
			}
			sysLog.setOperateUserAccount(operateUserAccount);
			
			sysLog.setOperateTime(new Date());
			
			insertSysLog(sysLog);
**/
		}
		return isExist;
	}
	
	/**
	 * 
	 * 向日志表加新记录。
	 * <p></p>
	 * @param sysLog 新日志
	 * @return
	 */
	public int insertSysLog(BaseSysLog sysLog) {
		return sysLogDao.insert(sysLog);
	}

	public BaseSysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(BaseSysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}
	
}
