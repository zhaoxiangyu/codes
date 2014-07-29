/**
 * 
 */
package com.acc.framework.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Copyright (c) 2010,  卓华软件</p>
 * <p>All right reserved. 版权</p>
 * <p>@desc:   获取客户端ip地址</p>
 * @author bh
 * @data Aug 2, 2010
 */
public class IpUtils {
	public static String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
}
