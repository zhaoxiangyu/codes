package org.sharp;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.apache.commons.lang3.time.StopWatch;
import org.sharp.jdkex.Utils;
import org.sharp.utils.FsUtils;
import org.sharp.utils.beans.DirSnap;

public class UtilsTest extends TestCase {

	private int courseNoOf(String path){
		//jp/jpwords/unit1/1/abc.mp3
		Pattern p = Pattern.compile("unit\\d+/(\\d+)/");
		Matcher m = p.matcher(path);
		if(m.find())
			return Integer.parseInt(m.group(1));
		else
			throw new RuntimeException("illegal path:"+path);
	}
	
	public void testCouseNoOf(){
		assertEquals(courseNoOf("jp/jpwords/unit7/29/aa.mp3"), 29);
	}
	/*public void testFindFiles() {
		File[] fs = Utils.findNamedFiles("C:/""C:/Program Files", "java.exe");
		for (int i = 0; i < fs.length; i++) {
			Console.log.debug(fs[i].getPath());
		}
	}*/
	
	public void testJdk(){
		System.out.println("key".getClass());
		System.out.println(((Object)"key").getClass());
	}
	
	public void testJox(){
		
		JoxBean joxBean = new JoxBean();
		joxBean.setInnerBeans(new JoxInnerBean[]{new JoxInnerBean(),new JoxInnerBean()});
		Utils.saveJox(joxBean, "c:/joxtest.txt");
	}

	public void testUnzip() throws IOException{
		FsUtils.unzip(new File("test/data/maven-ant-tasks-2.1.1.zip"), new File("test/data/maven-ant-tasks-2.1.1"));
	}

	public void testZip() throws IOException{
		FsUtils.zip(new File("test/data/maven-ant-tasks-2.1.1"), 2, new File("test/data"));
	}

	public void testDirectorySnap(){
		StopWatch sw = new StopWatch();
		sw.start();
		/*Utils.save(Utils.directorySnap("v:/未刻录/语音库/[美音版真人语音库三合一].voice/voice")new JdkUtils.KeyValue<String,String>("key","value"), "c:/d.txt");
		sw.split();
		System.out.println(sw.toSplitString());*/
		DirSnap ds = (DirSnap) Utils.loadJox2("c:/d.txt", DirSnap.class);
		sw.split();
		System.out.println(sw.toSplitString());
		System.out.println(ds.findFile("strut.mp3"));
		sw.stop();
		System.out.println(sw.toString());
	}
}
