package org.sharp.swing.apps.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sharp.jdkex.Utils;

public class Config {
	public static Config config;
	
	public Config(){
	}
	
	public static Config load(String file){
		config = (Config)Utils.loadJox2(file, Config.class, null);
		if(config==null)
			config = new Config();
		Utils.log.debug("config is:"+config);
		return config;
	}
	
	public Config save(String file){
		return (Config)Utils.saveJox(this, file);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
