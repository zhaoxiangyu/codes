package com.acc.sys.log.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.acc.framework.util.page.PageBean;
import com.acc.sys.commons.pojo.BaseSysLog;

/**
 * 
 * 系统日志服务接口.
 * <p>  </p>
 * @author 何龙
 * @version v1.0.0 <p> 最后更新 by 何龙 @ 2012年12月4日15:15:22 </p>
 * @since
 */
@SuppressWarnings("unchecked")
@Component
public interface ISysLogService {


	/**
	 * 
	 * 系统日志分页查询。
	 * <p></p>
	 * @param pageBean 分页对象
	 * @param param 
	 * @return
	 */
	public PageBean<BaseSysLog> queryByPage(PageBean<BaseSysLog> pageBean, Map param);
	
}
