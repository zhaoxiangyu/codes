package com.acc.framework.intercepter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.acc.framework.constants.Constants;
import com.acc.framework.util.Logger;
import com.acc.framework.util.UUIDGeneratorUtil;
import com.acc.sys.commons.dao.BaseSysLogDao;
import com.acc.sys.commons.pojo.BaseSysLog;


/**
 * 
 * 用于记录数据库日志的拦截器，拦截SpringMVC请求。
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-19 </p>
 * @since v1.0.0
 */
public class WebInterceptor extends HandlerInterceptorAdapter {	
	
	@Autowired
	private BaseSysLogDao sysLogDao;
	public static final String PREF_INSERT = "insert"; 
	public static final String PREF_DELETE = "delete"; 
	public static final String PREF_MODIFY = "modify";
	public static final String PREF_VIEW = "view";
	
	public static final String PREF_QUERY = "query";
	public static final String PREF_STATIS = "statis";
	public static final String PREF_LOGIN = "login";
	public static final String PREF_LOGOUT = "logout";
	public static final String PREF_CHANGEPASSWORD = "changepassword";
	
	private Logger logger = new Logger(WebInterceptor.class);
	
	/**
	 * 
	 * 构造函数
	 */
	public WebInterceptor(){
		logger.debug("new WebInterceptor");
	}

	@Override
	public void postHandle(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView){
		if(!(handler instanceof HandlerMethod)){
			return;
		}
		
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		logger.debug("request's url:"+request.getRequestURI());
		logger.debug("handler's type:"+handler.getClass());
		
		String pageNumber = request.getParameter("pageNumber");
		if( pageNumber!= null)
			return;

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
			if(methodStr.startsWith(PREF_INSERT)){
				sysLog.setOperateType(PREF_INSERT);
			}else if(methodStr.startsWith(PREF_DELETE)){
				sysLog.setOperateType(PREF_DELETE);
			}else if(methodStr.startsWith(PREF_MODIFY)){
				sysLog.setOperateType(PREF_MODIFY);
			}else if(methodStr.startsWith(PREF_QUERY)){
				sysLog.setOperateType(PREF_QUERY);
			}else if(methodStr.startsWith(PREF_LOGIN)){
				sysLog.setOperateType(PREF_LOGIN);
			}else if(methodStr.startsWith(PREF_LOGOUT)){
				return;
			}else if(methodStr.startsWith(PREF_CHANGEPASSWORD)){
				sysLog.setOperateType(PREF_CHANGEPASSWORD);
			}else if(methodStr.startsWith(PREF_STATIS)){
				sysLog.setOperateType(PREF_STATIS);
			}else{
				return;
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

		String effectId = null;
		if(methodStr.startsWith(PREF_DELETE) ||methodStr.startsWith(PREF_MODIFY) || methodStr.startsWith(PREF_VIEW) || methodStr.startsWith(PREF_INSERT)){
			Object attri = request.getAttribute(Constants.HTTP_REQUEST_PARAMETER_ID);
			if(attri != null){
				effectId = attri.toString();
			}
		}
		if(effectId!=null)
			sysLog.setEffectId(effectId );
		
		insertSysLog(sysLog);
		
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
