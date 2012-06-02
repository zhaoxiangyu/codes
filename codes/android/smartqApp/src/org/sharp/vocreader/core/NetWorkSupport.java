package org.sharp.vocreader.core;

import org.sharp.intf.DownloadEventListener;
import org.sharp.intf.Response;
import org.sharp.intf.Response.StatusCode;
import org.sharp.utils.IOUtils;
import org.sharp.utils.Option;
import org.sharp.vocreader.beans.Course;
import org.sharp.vocreader.beans.ItemInfo;
import org.sharp.vocreader.beans.UrlSetting;
import org.sharp.vocreader.intf.Constants;
import org.sharp.vocreader.intf.OsSupport;

public class NetWorkSupport {

	private final static String appUseUrl = "http://wd4web.sinaapp.com/new-appuse.php";
	private final static String jpwordCourseZipUrlBase = "http://web-word-list.googlecode.com/files/";
	private final static String jpwordQueryUrl = "http://wd4web.sinaapp.com/jpword-httpquery.php";

	private final static String APP_NAME = "JpWordReader";
	private final static String QUERY_COMMAND="q";
	private final static String QUERY_COMMAND_COURSE_DOWN="jp-course";
	private final static String QUERY_COMMAND_COURSES_MANIFEST="jp-courses-manifest";
	private final static String QUERY_COMMAND_PAYINFO="payInfo";

	private final static String QUERY_PNAME_COURSENO="courseNo";
	
	public final static String AD_ADMOB = "admob";
	public final static String AD_YOUMI_BANNER = "youmi_banner";
	public final static String AD_YOUMI_POINTS = "youmi_points";

	private static UrlSetting mOnlineSetting;
	
	/*@Deprecated
	public static void addAppUse(OsSupport os,Option<UrlSetting> setting){
		if (!setting.isNull()) {
			os.log("server setting:" + os.toString(setting.value()));
			os.httpGet(appUseUrl(setting.value().appuse_url,
					APP_NAME, os.getUniqueId()));
		} else {
			os.log("failed to fetch server setting.");
			os.httpGet(appUseUrl(appUseUrl,
					APP_NAME, os.getUniqueId()));
		}
	}*/
	
	public static boolean isPayed(OsSupport os,int price, String type){
		Response response = os.httpGet(payQueryUrl(os.getMdn(),
				os.getImei(), os.getUniqueId(), price, type));
		//TODO
		if(Response.StatusCode.NORMAL.equals(response.statusCode())){
			String content = response.content();
			os.log("setting string:"+content);
			mOnlineSetting = (UrlSetting)os.fromString(content, UrlSetting.class);
		}
		
		return true;
	}
	
	public static Option<UrlSetting> addAppUse(OsSupport os){
		Response response = os.httpGet(appUseUrl(appUseUrl,
				APP_NAME, os.getUniqueId()));
		if(Response.StatusCode.NORMAL.equals(response.statusCode())){
			String content = response.content();
			os.log("setting string:"+content);
			mOnlineSetting = (UrlSetting)os.fromString(content, UrlSetting.class);
		}
		
		return new Option<UrlSetting>(mOnlineSetting);
	}
	
	private static String appUseUrl(String url,String appName,String userid){
		return url + "?appname="+appName+"&userid="+userid;
	}
	
	private static String courseZipUrl(OsSupport os,int courseNo){
		String zipDownUrl = null;
		//http://wd4web.sinaapp.com/jpword-httpquery.php?q=jp-course&courseNo=1
		Response rs = os.httpGet(jpwordQUrl()+
				"?"+QUERY_COMMAND+"="+QUERY_COMMAND_COURSE_DOWN+
				"&"+QUERY_PNAME_COURSENO+"="+courseNo);
		//
		if(StatusCode.NORMAL.equals(rs.statusCode())){
			zipDownUrl = rs.content();
		}else{
			zipDownUrl = IOUtils.fullPath(jpwordCourseZipUrlBase,courseNo+".zip");
		}
		return zipDownUrl;
	}
	
	private static String payQueryUrl(String mdn,String imei, String userid,
			int amount, String type){
		return jpwordQUrl() + "?"+QUERY_COMMAND+"="+QUERY_COMMAND_PAYINFO+
			"&mdn="+mdn+"&imei="+imei+"&userid="+userid +
			"&amount="+amount+"&type="+type;
	}
	
	private static String coursesManifestUrl(){
		return jpwordQUrl() + "?"+QUERY_COMMAND+"="+QUERY_COMMAND_COURSES_MANIFEST;
	}
	
	public static void downloadCourseZip(OsSupport os,
			int courseNo,String savePath,DownloadEventListener del){
		String downloadUrl = courseZipUrl(os, courseNo);
		//http://web-word-list.googlecode.com/files/1.zip
			//IOUtils.downFile(downloadUrl.value(), savePath);
		os.downloadFile(downloadUrl, savePath, del);
	}
	
	/*@Deprecated
	public static Option<UrlSetting> fetchSetting(OsSupport os){
		Response response = os.httpGet(jpwordQueryUrl);
		UrlSetting setting = null;
		if(Response.StatusCode.NORMAL.equals(response.statusCode())){
			String content = response.content();
			os.log("setting string:"+content);
			setting = (UrlSetting)os.fromString(content, UrlSetting.class);
		}
		return new Option<UrlSetting>(setting);
	}*/
	
	public static ItemInfo fetchItemInfo(){
		ItemInfo ii = new ItemInfo();
		return ii;
	}

	public static Course[] fetchCourseList(OsSupport os){
		Response response = os.httpGet(coursesManifestUrl());
		Course[] courses = null;
		if(Response.StatusCode.NORMAL.equals(response.statusCode())){
			String content = response.content();
			os.log("course list string:"+content);
			courses = (Course[])os.fromString(content, Course[].class);
		}
		return courses;
	}

	private static boolean isOnlineSettingNull() {
		return mOnlineSetting == null;
	}
	
	private static String jpwordQUrl(){
		if(isOnlineSettingNull())
			return jpwordQueryUrl;
		else
			return mOnlineSetting.jpcourse_qurl;
	}
	
	public static UrlSetting onlineSetting(){
		if(isOnlineSettingNull())
			return defaultOnlineSetting();
		else
			return mOnlineSetting;
	}
	
	private static UrlSetting defaultOnlineSetting(){
		UrlSetting urlSetting = new UrlSetting();
		urlSetting.appuse_url = jpwordQueryUrl;
		urlSetting.bonus_offer = 100;
		urlSetting.jpcourse_qurl = "";
		urlSetting.use_ad = AD_YOUMI_POINTS;
		urlSetting.course_points = 50;
		return urlSetting;
	}
}
