package org.sharp.vocreader.intf;

import java.io.File;
import java.io.IOException;

import org.sharp.intf.DownloadEventListener;
import org.sharp.intf.Response;
import org.sharp.intf.UnzipEventListener;
import org.sharp.vocreader.beans.State;

import android.content.Context;

public interface OsSupport {
	void playMp3(String mp3Path);
	void log(String message);
	void log(String msg, Throwable thrown);
	State loadState();
	void saveState(State state);
	Object fromString(String str,Class<?> clazz);
	String toString(Object object);
	void writeFile(String path, String str);
	String readFile(String path);
	Response httpGet(String url);
	String getUniqueId();
	String getMdn();
	String getImei();
	void downloadFile(String url, String savePath,DownloadEventListener del);
	void unzipFile(String path, String parent,
		UnzipEventListener unzipEventListener);
	void logStoragePath(String path);
	File[] findMp3File(String path);
	String relativePath(String fullPath, String base);
	boolean pathExists(String path);
	void copyAssetFile(String srcFileName, String targetFilePath);
	
}
