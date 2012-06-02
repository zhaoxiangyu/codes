package org.sharp.pm.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sharp.android.autils.AUtils;
import org.sharp.pm.beans.OsInfo;
import org.sharp.pm.beans.PackageInfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class PMUtils {
	public static OsInfo osInfo(){
		OsInfo oi = new OsInfo();
		oi.sdkId = Build.VERSION.SDK_INT;
		oi.sdkLabel = Build.VERSION.RELEASE;
		oi.fingerPrint = Build.FINGERPRINT;
		return oi;
	}
	
	public static List<PackageInfo> installedPackages(Context ctx){
		PackageManager pm = ctx.getPackageManager();
		List<android.content.pm.PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		List<PackageInfo> pkgList = new ArrayList<PackageInfo>();
		for(android.content.pm.PackageInfo pi : packages){
			PackageInfo pkg = new PackageInfo();
			pkg.packageName = pi.packageName;
			pkg.versionName = pi.versionName;
			pkg.versionCode = pi.versionCode;
			
			pkg.appLabel = (String) pi.applicationInfo.loadLabel(pm);
			pkg.appIcon = AUtils.drawableToBytes(pi.applicationInfo.loadIcon(pm));
			pkg.appFlags = pi.applicationInfo.flags;
			pkgList.add(pkg);
		}
		return null;
	}
}
