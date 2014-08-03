package org.sharpx.utils;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.proxy.Interceptor;
import org.apache.commons.proxy.Invocation;
import org.apache.commons.proxy.factory.cglib.CglibProxyFactory;
import org.sharpx.utils.annotation.Log;
import org.sharpx.utils.jdkex.Utils;

public class Proxies {
	private static CglibProxyFactory fac = new CglibProxyFactory();

	public static Object cachedCallProxy(Object target){
		return fac.createInterceptorProxy(target, new CacheInterceptor(), new Class[]{target.getClass()});
	}
	
	public static Object exceptionLogProxy(Object target){
		return fac.createInterceptorProxy(target, new Interceptor(){

			public Object intercept(Invocation inv) throws Throwable {
				Object ret = null;
				try{
					ret = inv.proceed();
				}catch (Exception e) {
					Utils.log.error(ToStringBuilder.reflectionToString(inv.getProxy())+"."+inv.getMethod().getName()+":"+Arrays.toString(inv.getArguments()));
					throw e;
				}
				return ret;
			}
			
		}, new Class[]{target.getClass()});
	}
	
	public static class CacheInterceptor implements Interceptor {
		HashMap<String, Object> map = new HashMap<String, Object>();
		@Log
		public Object intercept(Invocation inv) throws Throwable {
			Object ret = null;
			String key = obtainKey(inv.getMethod().getName(),inv.getArguments());
			if(map.containsKey(key)){
				ret = map.get(key);
				Utils.log.debug("cached retvalue "+key+".");
			}else{
				ret = inv.proceed();
				map.put(key, ret);
			}
			return ret;
		}
		
		private String obtainKey(String name, Object[] arguments) {
			StringBuffer buf = new StringBuffer();
			buf.append(name+"-");
			for (int i = 0; i < arguments.length; i++) {
				buf.append(arguments[i]+":");
			}
			return buf.toString();
		}
		
	}
}
