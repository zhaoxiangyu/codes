package org.sharp.vocreader.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.sharp.intf.UnzipEventListener;
import org.sharp.utils.Features;
import org.sharp.utils.IOUtils;
import org.sharp.utils.Option;
import org.sharp.vocreader.beans.AudioInfo;
import org.sharp.vocreader.beans.AudioInfoV2;
import org.sharp.vocreader.beans.Course;
import org.sharp.vocreader.beans.State;
import org.sharp.vocreader.intf.Constants;
import org.sharp.vocreader.intf.OsSupport;

public class CourseUtils {
	
	public static void loadAssetCourse(final OsSupport oss,
			final LevelsInfo lsi,final List<AudioInfoV2> infoList,final List<AudioInfoV2> levelList,final State state){
		state.resetState(1, true);
		if(!CourseUtils.courseUnzipped(oss, 1)){
			oss.copyAssetFile("jpwords/1.zip", CourseUtils.zipFilePath(1));
			oss.unzipFile(CourseUtils.zipFilePath(1),
				CourseUtils.zipFileDir(),
				new UnzipEventListener() {
					@Override
					public void complete(String filePath) {
						CourseUtils.loadCourse(oss,lsi, infoList,state.courseNo);
					}
				});
		}else{
			CourseUtils.loadCourse(oss,lsi, infoList,state.courseNo);
		}
	}

	private static void saveCache4Course(OsSupport oss,int courseNo,
			List<AudioInfoV2> infoList){
		if(CourseUtils.valid(courseNo)){
			oss.writeFile(CourseUtils.courseCacheFilePath(courseNo),
					oss.toString(infoList.toArray(new AudioInfoV2[0])));
		}
	}
	
	public static void saveCourse(OsSupport oss,int courseNo,
			List<AudioInfoV2> infoList){
		if(Features.useSqlite3){
			CourseUtils.saveDB4Course(oss, infoList);
		}else{
			CourseUtils.saveCache4Course(oss,courseNo, infoList);
		}
	}
	
	private static void saveDB4Course(OsSupport oss,List<AudioInfoV2> infoList){
		oss.saveAudioInfos(infoList);
	}
			
	private static void loadMp3s(OsSupport oss,List<AudioInfoV2> infoList,
			LevelsInfo lvs, String path) {
		oss.log("load mp3 from path " + path);
		File[] mp3files = oss.findMp3File(path);
		for (int i = 0; i < mp3files.length; i++) {
			Option<Integer> cn = CourseUtils.courseNoOf(mp3files[i].getPath());
			int un = UnitUtils.unitNoOf(mp3files[i].getPath());
			if (cn.isNull() || un == Integer.MIN_VALUE)
				continue;

			AudioInfoV2 ai = new AudioInfoV2();
			ai.mp3Path = oss.relativePath(mp3files[i].getPath(),Constants.jpMp3Path);
			ai.name = FilenameUtils.getBaseName(mp3files[i].getName());
			AudioInfoUtils.parseJpVoc2(oss, ai);
			
			ai.courseNo = cn.value();
			ai.unitNo = un;
			ai.level = 0;
			infoList.add(ai);
			lvs.add(ai);
		}
		oss.log("from path total " + infoList.size() + " mp3 loaded.");
	}

	private static void loadDB(OsSupport oss, List<AudioInfoV2> iList, 
			LevelsInfo lvs, int courseNo) {
		try {
			long ct = System.currentTimeMillis();
			AudioInfoV2[] o = null;
			o = (AudioInfoV2[]) oss.loadAudioInfos(courseNo);
			long ct2 = System.currentTimeMillis();
			int sec = (int) ((ct2 - ct) / 1000);
			if (o != null){
				oss.log("takes " + sec + " seconds,total " + o.length
						+ " audio info loaded.");
				for(AudioInfoV2 ai:o){
					iList.add(ai);
					lvs.add(ai);
				}
			}
		} catch (Exception e) {
			oss.log(e.getMessage(), e);
		}
	}

	public static void loadCourse(OsSupport oss, LevelsInfo levels, 
			List<AudioInfoV2> iList,int courseNo) {
		levels.clear(-1,Constants.MAX_LEVEL);
		
		if(Features.useSqlite3){
			loadDB(oss, iList, levels, courseNo);
		}else{
			loadCache(oss, iList, levels,
					CourseUtils.courseCacheFilePath(courseNo));
		}
		if(iList.isEmpty()){
			oss.log("loading from cache failed."); 
			loadMp3s(oss,iList,levels,CourseUtils.unzippedDirPath(courseNo));
		}else{
			oss.log("loaded from cache."); 
		}
	}

	private static void loadCache(OsSupport oss, List<AudioInfoV2> iList, 
			LevelsInfo lvs, String filePath) {
		try {
			long ct = System.currentTimeMillis();
			AudioInfoV2[] o = null;
			if(oss.pathExists(filePath)){
				o = (AudioInfoV2[]) oss.fromString(
					oss.readFile(filePath), AudioInfoV2[].class);
			}else{
				oss.log("file "+filePath+ " not exists.");
			}
			long ct2 = System.currentTimeMillis();
			int sec = (int) ((ct2 - ct) / 1000);
			if (o != null){
				oss.log("takes " + sec + " seconds,total " + o.length
						+ " audio info loaded.");
				for(AudioInfoV2 ai:o){
					AudioInfoUtils.parseJpVoc2(oss, ai);
					
					iList.add(ai);
					lvs.add(ai);
				}
			}
		} catch (Exception e) {
			oss.log(e.getMessage(), e);
		}
	}

	private static String courseCacheFilePath(int courseNo){
		return CourseUtils.unzippedDirPath(courseNo) + 
			File.separator + Constants.cacheFileName;
	}
	
	@Deprecated
	private static String[] courseTitles() {
		List<String> tl = new ArrayList<String>();
		for (int i = 0; i < Constants.MAX_COURSE_NO; i++) {
			tl.add("Course " + (i + 1));
		}
		return tl.toArray(new String[0]);
	}

	private static Option<Integer> courseNoOf(String path) {
		// jp/jpwords/unit1/1/abc.mp3
		Pattern p = Pattern.compile("unit\\d+/(\\d+)/");
		Matcher m = p.matcher(path);
		if (m.find())
			return new Option<Integer>(new Integer(m.group(1)));
		else
			return new Option<Integer>(null);
		/*
		 * else throw new RuntimeException("illegal path:"+path);
		 */
	}

	private static String courseZipFileName(int courseNo) {
		return courseNo + ".zip";
	}

	private static String courseDir(int courseNo) {
		return UnitUtils.unitDir((courseNo - 1) / 4 + 1) + File.separator
				+ courseNo;
	}

	private static Course[] courseList(OsSupport os) {
		List<Course> tl = new ArrayList<Course>();
		for (int i = 1; i <= Constants.MAX_COURSE_NO; i++) {
			Course course = new Course(i);

			if (courseDownloaded(os,i))
				course.setStatus(Constants.COURSE_STATUS_DOWNLOADED);
			else
				course.setStatus(Constants.COURSE_STATUS_NOTEXIST_NOINFO);

			tl.add(course);
		}
		return tl.toArray(new Course[0]);
	}

	public static Course[] courseList2(OsSupport os) {
		Course[] ca = NetWorkSupport.fetchCourseList(os);
		if(ca == null){
			return courseList(os);
		}
		for (int i = 1; i <= Constants.MAX_COURSE_NO; i++) {
			Course course = ca[i-1];

			if (courseDownloaded(os,i))
				course.setStatus(Constants.COURSE_STATUS_DOWNLOADED);
			else
				course.setStatus(Constants.COURSE_STATUS_NOTEXIST);
			//os.log("course "+course.courseNo+" zipSize:"+course.zipSize+" status:"+course.status);
		}
		return ca;
	}

	public static String zipFilePath(int courseNo) {
		return IOUtils.fullPath(Constants.jpMp3Path,
				courseZipFileName(courseNo));
	}

	public static String zipFileDir() {
		return Constants.jpMp3Path;
	}

	private static String unzippedDirPath(int courseNo) {
		return IOUtils.fullPath(zipFileDir(),
				courseDir(courseNo));
	}

	public static boolean courseUnzipped(OsSupport os, int courseNo) {
		return os.pathExists(unzippedDirPath(courseNo));
	}

	private static boolean courseDownloaded(OsSupport os, int courseNo) {
		return os.pathExists(zipFilePath(courseNo));
	}

	public static boolean valid(int courseNo) {
		return courseNo != Constants.ILLEGAL_INT &&
			courseNo >=1 && courseNo <= Constants.MAX_COURSE_NO;
	}

	public static Course[] loadCourseList(OsSupport oss) {
		return (Course[])oss.fromString(oss.readFile(courseListFilePath()),Course[].class);
	}
	
	public static void saveCourseList(OsSupport oss,Course[] courses) {
		oss.writeFile(courseListFilePath(),oss.toString(courses));
	}
	
	private static String courseListFilePath(){
		return IOUtils.fullPath(Constants.jpMp3Path, Constants.coursesInfoFileName);
	}

	public static int importLearned(OsSupport oss) {
		int cc = 0;
		for(int cNo = 1; cNo <= Constants.MAX_COURSE_NO; cNo ++){

			try {
				AudioInfo[] o = null;
				String cacheFilePath = courseCacheFilePath(cNo);
				if(oss.pathExists(cacheFilePath)){
					o = (AudioInfo[]) oss.fromString(
						oss.readFile(cacheFilePath), AudioInfo[].class);
				}else{
					oss.log("file "+cacheFilePath+ " not exists.");
				}

				if (o != null && o.length > 0){
					List<AudioInfoV2> infoList = new ArrayList<AudioInfoV2>();
					for(AudioInfo ai:o){
						AudioInfoV2 ai2 = AudioInfoUtils.parseJpVoc2(oss, ai);
						if(ai2 != null)
							infoList.add(ai2);
					}
					saveDB4Course(oss, infoList);
					oss.removeFile(cacheFilePath);
					cc++;
				}
			} catch (Exception e) {
				oss.log(e.getMessage(), e);
			}
		}
		return cc;
	}

}