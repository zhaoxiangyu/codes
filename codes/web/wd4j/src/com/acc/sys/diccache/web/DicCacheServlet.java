package com.acc.sys.diccache.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.acc.framework.util.DicCacheUtil;
import com.acc.framework.util.Logger;
import com.acc.sys.diccache.service.DicCacheService;

/**
 * 
 * 加载字典缓存的servlet。
 * <p></p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-17 </p>
 * @since v1.0.0
 */
@SuppressWarnings("serial")
public class DicCacheServlet extends HttpServlet {

	Logger log = new Logger(DicCacheServlet.class);
	
	/**
	 * 
	 * 构造函数
	 */
	public DicCacheServlet() {
		super();
	}

	/**
	 * 销毁方法
	 */
	public void destroy() {
		super.destroy(); 
	}

	/**
	 * 初始化方法
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		loadCache(config);
	}
	
	private void loadCache(ServletConfig config){
		ServletContext sc = config.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		SqlSessionFactory sessionFactory = (SqlSessionFactory)ctx.getBean("sqlSessionFactory");
		
		DicCacheService dicCacheService = (DicCacheService)ctx.getBean("dicCacheService");
		List<Map<String,Object>> dictValues = dicCacheService.getDictionary(null);
		List<Map<String,Object>> orgList = dicCacheService.getOrganDict();
		DicCacheUtil.init(sc.getRealPath("dictionary"), dictValues, orgList);
		log.info("dictionary written to "+sc.getRealPath("dictionary"));
		sc.setAttribute("AppCacheDict", DicCacheUtil.getEnumAll());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		String cmd = req.getParameter("cmd");
		log.debug("got command:"+cmd);
		if("reload".equalsIgnoreCase(cmd)){
			loadCache(getServletConfig());
			writer.write("true");
			writer.flush();
			log.debug("diccache reloaded.");
		}else{
			writer.write("false");
			writer.flush();
			log.debug("reload diccache failed.");
		}
	}
}
