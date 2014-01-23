package com.acc.sys.log.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.acc.framework.util.DateUtil;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseSysLog;
import com.acc.sys.log.service.ISysLogService;

/**
 * 系统日志控制器 <p> </p>
 * 
 * @author 何龙
 * @version v1.0.0 <p> 最后更新 by 何龙 @ 2012年12月4日14:45:09 </p>
 * @since
 */
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/sys/syslog")
public class SysLogController {

	/** 系统日志Service对象声明 */
	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 系统日志列表 。 <p> </p>
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return result ModelAndView
	 */
	@RequestMapping(value = "/logList")
	public ModelAndView queryLogs(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute PageBean<BaseSysLog> pageBean) {
		Map<String, Object> param = WebUtils.getParametersStartingWith(request, "");
		pageBean = sysLogService.queryByPage(pageBean, param);
		ModelAndView result = new ModelAndView("/pages/sys/log/logList");
		request.setAttribute("page", pageBean);
		if(param !=null && param.get("beginTime")!=null){
			String  beginTime = DateUtil.getFullDateToString((Date) param.get("beginTime"));
			param.remove("beginTime");
			param.put("beginTime", beginTime);
			String  endTime = DateUtil.getFullDateToString((Date) param.get("endTime"));
			param.remove("endTime");
			param.put("endTime", endTime);
			}
		request.setAttribute("parameter", param);
		return result;
	}

}
