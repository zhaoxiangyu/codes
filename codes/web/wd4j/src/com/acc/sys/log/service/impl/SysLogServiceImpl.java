package com.acc.sys.log.service.impl;

import java.util.Date;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acc.framework.util.DateUtil;
import com.acc.framework.util.Logger;
import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.dao.BaseSysLogDao;
import com.acc.sys.commons.pojo.BaseSysLog;
import com.acc.sys.log.service.ISysLogService;

/**
 * 
 * 系统日志实现类。
 * <p>记录用户操作到数据库。</p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-14 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
@Component
@RemoteProxy(name="sysLogService")
public class SysLogServiceImpl implements ISysLogService {
	@Autowired
	private BaseSysLogDao sysLogDao;
	Logger log = new Logger(SysLogServiceImpl.class);

	@RemoteMethod
	public PageBean<BaseSysLog> queryByPage(PageBean<BaseSysLog> pageBean, Map param) {
		if(param !=null && param.get("beginTime")!=null || param.get("endTime") !=null){
		Date  beginTime = DateUtil.formatDate( param.get("beginTime").toString(),"yyyy-MM-dd HH:mm:ss");
		param.remove("beginTime");
		param.put("beginTime", beginTime);
		Date  endTime = DateUtil.formatDate( param.get("endTime").toString(),"yyyy-MM-dd HH:mm:ss");
		param.remove("endTime");
		param.put("endTime", endTime);
		}
		log.info("queryByPage operateModule:"+param.get("operateModule")+",operateType:"+param.get("operateType"));
		if(param.get("operateUserAccount")!=null){
			param.put("operateUserAccount1", param.get("operateUserAccount").toString().replace("%", "/%"));
			}
		if(param.get("userName")!=null){
			param.put("userName1", param.get("userName").toString().replace("%", "/%"));
			}
		pageBean.setResultList(sysLogDao.queryByPage(pageBean,param));
		return pageBean;
	}

}
