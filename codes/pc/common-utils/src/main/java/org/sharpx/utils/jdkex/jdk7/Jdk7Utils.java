package org.sharpx.utils.jdkex.jdk7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;

public class Jdk7Utils {

	public static void monitorDir(String dirPath) throws IOException{
		WatchService watcher = FileSystems.getDefault().newWatchService();
		Path dir = Paths.get(dirPath);
	}
}
