package org.sharp.swing.apps.dirSnaps;

import org.sharp.intf.AppContext;
import org.sharp.intf.Pluggable;
import org.sharp.utils.FsUtils.DirSnaps;
import org.sharp.utils.FsUtils.DirSnaps.CacheSupport;

public class DirSnapsApp implements Pluggable{

	public static class Config {
		public String getCacheDir() {
			return cacheDir;
		}

		public void setCacheDir(String cacheDir) {
			this.cacheDir = cacheDir;
		}

		String cacheDir;
	}
	
	public AppLifeCycle lifeCycle() {
		return new AppLifeCycle() {
			
			public void init(AppContext context) {
				final Config cf = context.getConfig(DirSnapsApp.class.getSimpleName(), Config.class);
				DirSnaps ds = new DirSnaps(new CacheSupport() {
					
					public String cacheFile(String dir) {
						return null;
					}
					
					public String cacheDir() {
						return cf.getCacheDir();
					}
				});
				context.shareObject(ds);
			}
			
			public void exit() {
			}
		};
	}

	public TabbedUI tabbedUI() {
		return null;
	}

}
