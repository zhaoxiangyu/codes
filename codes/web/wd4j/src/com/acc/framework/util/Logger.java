package com.acc.framework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 日志处理类。
 * <p>写日志，建议使用此日志类，不要直接使用第三方的日志类。</p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-5 </p>
 * @since v1.0.0
 */
@SuppressWarnings("unchecked")
public class Logger {
	
	private Log log;
	
	/**
	 * 
	 * 构造函数
	 * @param name 日志名称
	 */
	public Logger(String name){
		log = LogFactory.getLog(name);
	}
	
	/**
	 * 
	 * 构造函数
	 * @param clazz Class对象
	 */
	public Logger(Class clazz){
		log = LogFactory.getLog(clazz);
	}
	
	/**
	 * 
	 * 构造函数
	 */
	public Logger(){
		log = LogFactory.getLog("com.acc.framework");
	}
	
	/**
	 * 
	 * 一般性日志。
	 * <p>记录一般性程序输出，包括关键组件的状态、和重要的外部事件。</p>
	 * @param message 输出的消息
	 */
	public void info(String message){
		log.info(message);
	}
	
	/**
	 * 
	 * 警告性日志。
	 * <p>记录可能会引发问题的事件。</p>
	 * @param message 输出的消息
	 */
	public void warn(String message){
		log.warn(message);
	}
	
	/**
	 * 
	 * 错误日志。
	 * <p>记录程序无法处理的事件。</p>
	 * @param message 输出的消息
	 */
	public void error(String message){
		log.error(message);
	}
	
	/**
	 * 
	 * 错误日志。
	 * <p>程序出错时，记录提示性信息和引发错误的异常。</p>
	 * @param message 输出的消息
	 * @param e 异常对象
	 */
	public void error(String message, Exception e){
		log.error(message, e);
	}
	
	/**
	 * 
	 * 调试排错类日志。
	 * <p>用于调试排错时，记录排错时的重要信息。</p>
	 * @param message 输出的消息
	 */
	public void debug(String message){
		log.debug(message);
	}

	/**
	 * 
	 * 调试排错类日志。
	 * <p>用于调试排错时，记录排错时的重要信息，附带引发错误的异常。</p>
	 * @param message 输出的消息
	 * @param e 异常对象
	 */
	public void debug(String message, Exception e){
		log.debug(message, e);
	}
}
