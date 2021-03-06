package org.sharpx.utils;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;

public class AntUtils {

	public static String[] listFiles(String baseDir,String[] includes, String[] excludes){
		String[] includeFiles = null;
		DirectoryScanner ds=new DirectoryScanner();
		ds.setBasedir(new File(baseDir));
		ds.setIncludes(includes);
		ds.setExcludes(excludes);
		ds.scan();
		if(ds.getIncludedFilesCount()>0) {
			includeFiles=ds.getIncludedFiles();
		}
		return (String[]) DsUtils.defaultIfNull(includeFiles, new String[0]);
	}

	public static String[] listFiles(String baseDir,String[] includes){
		return listFiles(baseDir,includes,null);
	}

	public static String[] listDirs(String baseDir,String includes){
		String[] includeDirs = null;
		DirectoryScanner ds=new DirectoryScanner();
		ds.setBasedir(new File(baseDir));
		ds.setIncludes(new String[]{includes});
		ds.scan();
		if(ds.getIncludedDirsCount()>0) {
			includeDirs=ds.getIncludedDirectories();
		}
		return (String[]) DsUtils.defaultIfNull(includeDirs, new String[0]);
	}

}
