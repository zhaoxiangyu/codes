package com.acc.framework.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * 
 * 配置项操作类。
 * <p>从配置文件读取配置项取值的工具类。</p> 
 * @author 何龙
 * @version v1.0.1
 * <p>最后更新 by 何龙 @ 2012-12-7 </p>
 * @since v1.0.0
 */
public class ConfigUtil {
	private Configuration config;

	/**
	 * 
	 * 构造函数
	 */
	public ConfigUtil() {
		init("config.properties");
	}

	/**
	 * 
	 * 构造函数
	 * @param configFileName 配置文件路径
	 */
	public ConfigUtil(String configFileName) {
		init(configFileName);
	}
	
	private static Configuration newConfig(String configFileName){
		File configFile = new File(configFileName);
		if (!configFile.exists()) {
			URL fileURL = ConfigUtil.class.getClassLoader().getResource(configFileName);

			if (fileURL != null)
				configFile = new File(fileURL.getFile());
			else {
				throw new RuntimeException("File Not Found: " + configFileName);
			}
		}
		try {
			Configuration configuration = null;
			if(configFileName.endsWith(".properties")){
				configuration = new PropertiesConfiguration(configFile);
			}else if(configFileName.endsWith(".xml")){
				configuration = new XMLConfiguration(configFile);
			}
			return configuration;
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void init(String configFileName) {
		Configuration configu = newConfig(configFileName);
		if(configu != null){
			this.config = configu;
		}
	}

	/**
	 * 
	 * 取得字符串类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 字符串类型的配置项值
	 */
	public String getString(String key) {
		return this.config.getString(key);
	}

	/**
	 * 
	 * 取得大实数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 大实数类型的配置项值
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
		return this.config.getBigDecimal(key, defaultValue);
	}

	/**
	 * 
	 * 取得大实数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 大实数类型的配置项值
	 */
	public BigDecimal getBigDecimal(String key) {
		return this.config.getBigDecimal(key);
	}

	/**
	 * 
	 * 取得大整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 大整数类型的配置项值
	 */
	public BigInteger getBigInteger(String key, BigInteger defaultValue) {
		return this.config.getBigInteger(key, defaultValue);
	}

	/**
	 * 
	 * 取得大整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 大整数类型的配置项值
	 */
	public BigInteger getBigInteger(String key) {
		return this.config.getBigInteger(key);
	}

	/**
	 * 
	 * 取得布尔类型的配置项。
	 * <p>配置项不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 布尔类型的配置项值
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		return this.config.getBoolean(key, defaultValue);
	}

	/**
	 * 
	 * 取得布尔类型的配置项。
	 * <p>配置项不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 布尔类型的配置项值
	 */
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return this.config.getBoolean(key, defaultValue);
	}

	/**
	 * 
	 * 取得布尔类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 布尔类型的配置项值
	 */
	public boolean getBoolean(String key) {
		return this.config.getBoolean(key);
	}

	/**
	 * 
	 * 取得字节类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 字节类型的配置项值
	 */
	public byte getByte(String key, byte defaultValue) {
		return this.config.getByte(key, defaultValue);
	}

	/**
	 * 
	 * 取得字节类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 字节类型的配置项值
	 */
	public Byte getByte(String key, Byte defaultValue) {
		return this.config.getByte(key, defaultValue);
	}

	/**
	 * 
	 * 取得字节类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 字节类型的配置项值
	 */
	public byte getByte(String key) {
		return this.config.getByte(key);
	}

	/**
	 * 
	 * 取得双精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 双精度小数类型的配置项值
	 */
	public double getDouble(String key, double defaultValue) {
		return this.config.getDouble(key, defaultValue);
	}

	/**
	 * 
	 * 取得双精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 双精度小数类型的配置项值
	 */
	public Double getDouble(String key, Double defaultValue) {
		return this.config.getDouble(key, defaultValue);
	}

	/**
	 * 
	 * 取得双精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 双精度小数类型的配置项值
	 */
	public double getDouble(String key) {
		return this.config.getDouble(key);
	}

	/**
	 * 
	 * 取得单精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 单精度小数类型的配置项值
	 */
	public float getFloat(String key, float defaultValue) {
		return this.config.getFloat(key, defaultValue);
	}

	/**
	 * 
	 * 取得单精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 单精度小数类型的配置项值
	 */
	public Float getFloat(String key, Float defaultValue) {
		return this.config.getFloat(key, defaultValue);
	}

	/**
	 * 
	 * 取得单精度小数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 单精度小数类型的配置项值
	 */
	public float getFloat(String key) {
		return this.config.getFloat(key);
	}

	/**
	 * 
	 * 取得整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 整数类型的配置项值
	 */
	public int getInt(String key, int defaultValue) {
		return this.config.getInt(key, defaultValue);
	}

	/**
	 * 
	 * 取得整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 整数类型的配置项值
	 */
	public int getInt(String key) {
		return this.config.getInt(key);
	}

	/**
	 * 
	 * 取得整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 整数类型的配置项值
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		return this.config.getInteger(key, defaultValue);
	}

	/**
	 * 
	 * 取得长整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 长整数类型的配置项值
	 */
	public long getLong(String key, long defaultValue) {
		return this.config.getLong(key, defaultValue);
	}

	/**
	 * 
	 * 取得长整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 长整数类型的配置项值
	 */
	public Long getLong(String key, Long defaultValue) {
		return this.config.getLong(key, defaultValue);
	}

	/**
	 * 
	 * 取得长整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 长整数类型的配置项值
	 */
	public long getLong(String key) {
		return this.config.getLong(key);
	}

	/*public Properties getProperties(String key) {
		return this.config.getProperties(key);
	}*/

	/**
	 * 
	 * 取得短整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 短整数类型的配置项值
	 */
	public short getShort(String key, short defaultValue) {
		return this.config.getShort(key, defaultValue);
	}

	/**
	 * 
	 * 取得短整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 短整数类型的配置项值
	 */
	public Short getShort(String key, Short defaultValue) {
		return this.config.getShort(key, defaultValue);
	}

	/**
	 * 
	 * 取得短整数类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 短整数类型的配置项值
	 */
	public short getShort(String key) {
		return this.config.getShort(key);
	}

	/**
	 * 
	 * 取得字符串数组类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @param defaultValue 缺省值
	 * @return 字符串数组类型的配置项值
	 */
	public String getString(String key, String defaultValue) {
		return this.config.getString(key, defaultValue);
	}

	/**
	 * 
	 * 取得字符串数组类型的配置项。
	 * <p>配置项不存在或者不符合格式时，抛出运行时异常。</p>
	 * @param key 配置项的键值
	 * @return 字符串数组类型的配置项值
	 */
	public String[] getStringArray(String key) {
		return this.config.getStringArray(key);
	}
}