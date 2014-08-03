package org.sharpx.utils.beans;

import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

public class DirSnap {
	String topDir;
	
	FileInfo[] fis;

	public FileInfo[] getFis() {
		return fis;
	}

	public void setFis(FileInfo[] fis) {
		this.fis = fis;
	}

	public String findFile(final String name) {
		/*
		 * Collections.binarySearch(Arrays.asList(fis),new
		 * FileInfo("",name));
		 */
		/* System.out.println(fis.length); */
		FileInfo fi = (FileInfo) CollectionUtils.find(Arrays.asList(fis),
				new Predicate() {
					public boolean evaluate(Object object) {
						FileInfo fi = (FileInfo) object;
						/* System.out.println(fi.getPath()); */
						return StringUtils.equals(fi.getName(), name);
					}
				});
		if (fi == null)
			return null;

		return fi.getPath();
	}
	
}