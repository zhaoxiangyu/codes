package org.sharpx.swing.beans;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sharpx.utils.FsUtils;

public class Config {
	public static Config config;
	
	public Config(){
	}
	
	public static Config load(String file){
		config = (Config)FsUtils.loadJox2(file, Config.class, null);
		if(config==null)
			config = new Config();
		//Utils.log.debug("config is:"+config);
		return config;
	}
	
	public Config save(String file){
		return (Config)FsUtils.saveJox(this, file);
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
