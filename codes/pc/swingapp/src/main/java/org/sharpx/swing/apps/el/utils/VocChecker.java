package org.sharpx.swing.apps.el.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.sharpx.utils.beans.DirSnap;

public class VocChecker {

	public static String lookUpAndCopy(DirSnap ds,String key, String toDir) {
		String filePath;
		String mp3file = ds.findFile(key+".mp3");
		String wavfile = ds.findFile(key+".wav");
		filePath= (String) ObjectUtils.defaultIfNull(mp3file, wavfile);
		
		if(filePath != null){
			File voicefile = new File(filePath);
			try {
				FileUtils.copyFileToDirectory(voicefile, new File(toDir));
				//Utils.log.debug("voice of term '"+key+"' copied to "+toDir);
				filePath = FilenameUtils.concat(toDir, voicefile.getName());
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}else{
			//Utils.log.debug("voice of term '"+key+"' not found.");
		}
		return filePath;
	}
	
}