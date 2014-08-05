package org.sharpx.utils.jdkex;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.wutka.jox.JOXBeanReader;
import com.wutka.jox.JOXBeanWriter;

public class Utils {

	public static interface PropertyHandler {
		
		void property(Object obj,PropertyDescriptor p);

		<T> void simpleProperty(Object obj, String pName, T pValue, Class<T> pType);
	}

	public static Logger log = Logger.getLogger(Utils.class);

	static {
		try {
			initLog4j();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initLog4j() throws IOException {
		String pattern = "%d [%t] %c %p - %m @ %l%n";
		Appender fileAppender = new FileAppender(new PatternLayout(pattern),
				"console.log");
		Utils.log.addAppender(fileAppender);
		ConsoleAppender consoleAppender = new ConsoleAppender(
				new PatternLayout(pattern), "System.out");
		Utils.log.addAppender(consoleAppender);
	
		TextAreaAppender taa = new TextAreaAppender();
		Utils.log.addAppender(taa);
		
		/*Logger.getLogger(BasicPlayer.class).addAppender(fileAppender);
		Logger.getLogger(BasicPlayer.class).addAppender(consoleAppender);
		Logger.getLogger(BasicPlayer.class).setLevel(Level.INFO);*/
	}

	public static Object saveJox(Object obj, String file) {
		FileOutputStream fileOut;
		try {
			/*fileOut = FileUtils.openOutputStream(new File(file));
			JOXBeanOutputStream joxOut = new JOXBeanOutputStream(fileOut,
					"utf-8");*/
			fileOut = FileUtils.openOutputStream(new File(file));
			JOXBeanWriter joxOut = new JOXBeanWriter(
					new OutputStreamWriter(fileOut,"utf-8"), "utf-8");
			if(obj!=null)
				joxOut.writeObject(obj.getClass().getSimpleName(), obj);
			joxOut.close();
		} catch (FileNotFoundException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return obj;
	}

	private static Object loadJox(String file, Class clazz) {
		FileInputStream in;
		try {
			in = new FileInputStream(file);
			/*JOXBeanInputStream joxIn = new JOXBeanInputStream(in);*/
			JOXBeanReader joxIn = new JOXBeanReader(new InputStreamReader(in,"utf-8"));
			Object conf = joxIn.readObject(clazz);
			in.close();
			return conf;
		} catch (FileNotFoundException e) {
			log.error("", e);
		} catch (IOException e) {
			log.error("", e);
		}
		return null;
	}

	@Deprecated
	public static <T> T loadJox2(String filePath, Class<T> clazz) {
		T c = null;
		try {
			File file = new File(filePath);
			if(file.exists() && file.isFile())
				c = (T) Utils.loadJox(filePath, clazz);
			
			if (c == null)
				c = (T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static <T> T loadJox2(String filePath, Class<T> clazz, T def) {
		T c = null;
		try {
			File file = new File(filePath);
			if(file.exists() && file.isFile())
				c = (T) Utils.loadJox(filePath, clazz);
			
			if (c == null)
				c = def;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	public static <T> void saveJox2(String file,T config){
		Utils.saveJox(config, file);
	}

	public static void introspect(Object obj,PropertyHandler ph){
		BeanInfo info;
		try {
			info = (BeanInfo) Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for (int i = 0; i < pds.length; i++) {
				PropertyDescriptor p = pds[i];
				final String pName = p.getName();
				Object value = PropertyUtils.getProperty(obj, p.getName());
				Class pt = p.getPropertyType();
				/*log.info("property:"
						+ (pt == null ? "indexed_property" : pt.getSimpleName())
						+ " " + pName);*/
				if (!"class".equalsIgnoreCase(pName) && p.getReadMethod() != null){
					if(isSimple(pt)){
						ph.simpleProperty(obj, pName, value, pt);
					}
					ph.property(obj, p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isSimple(Class clz){
		if(clz == null)
			return false;
		
		return clz.isPrimitive() || clz.getPackage().getName().startsWith("java.lang");
	}

	public static PropertyHandler htmlGenerator(final StringBuffer strBuf){
		return new PropertyHandler() {
			public void property(Object obj, PropertyDescriptor p) {
				try {
					Object value = PropertyUtils.getProperty(obj, p.getName());
					if(value ==null)
						return;
					Class<?> clazz = value.getClass();
					log.debug(p.getName()+":"+value);
					if(clazz.isArray() && value != null){
						int len = Array.getLength(value);
						strBuf.append("<div id='"+p.getName()+"'>");
						for(int i=0;i<len;i++){
							Object e = Array.get(value, i);
							if(isSimple(e.getClass())){
								strBuf.append("<span id='"+p.getName()+"["+i+"]'>");
								strBuf.append(e);
								strBuf.append("</span><br/>");
							}else{
								strBuf.append("<div id='"+p.getName()+"["+i+"]'>");
								Utils.introspect(e, this);
								strBuf.append("</div>");
							}
						}
						strBuf.append("</div>");
					}else if(!isSimple(clazz)){
						strBuf.append("<div id='"+p.getName()+"'>");
						Utils.introspect(value, this);
						strBuf.append("</div>");
					}else if(isSimple(clazz)){
						strBuf.append("<span id='"+p.getName()+"'>"+
								value+
								"</span><br/>");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public <T> void simpleProperty(Object obj, String pName, T pValue, Class<T> pType) {
			}
		};
	}

	public static void sort(Object[] array){
		if(array!=null)
			Arrays.sort(array);
	}
	
	public static <T> T elementAt(T[] array, int i) {
		if(array == null)
			return null;
		if(i >= 0 && i< array.length)
			return array[i];
		else
			return null;
	}

	public static <T> T elementAt(T[] array, int i,T def) {
		if(array == null)
			return def;
		if(i >= 0 && i< array.length)
			return array[i];
		else{
			System.out.println("array index outofbound.");
			return def;
		}
	}

	public static <T> T newInstance(Class<T> clazz){
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static<T> void add(List<T> list, T t, boolean allowDuplicated) {
		if(allowDuplicated){
			list.add(t);
			return;
		}	
		if(!list.contains(t))
			list.add(t);
		else{
			list.remove(t);
			list.add(t);
		}
	}

	public static <T> T[] toArray(List<T> list, Class<T> clazz) {
		if(list ==null)
			return null;
		else
			return (T[])list.toArray((T[])Array.newInstance(clazz, 0));
	}
	
	public static <T> T[] removeNull(T[] array, Class<T> clazz) {
		if(array == null)
			return null;
		List<T> list = new ArrayList<T>();
		for (T t : array) {
			if(t!=null)
				list.add(t);
		}
		return Utils.toArray(list, clazz);
	}

	public static <T> void removeNull(List<T> list) {
		if(list == null)
			return;
		for (int i = 0;i<list.size();i++) {
			if(list.get(i) == null)
				list.remove(i);
		}
	}

	public static <T> T defaultIfNull(T value, T def){
		return value==null? def :value;	
	}
	
	public static <T> T[] append(T[] dest, T el){
		List<T> list = Arrays.asList(dest);
		list.add(el);
		return list.toArray(dest);
	}

	public static String toShortStr(Date date) {
		if(date == null)
			return "";
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
	}

	public static String toShortStr(long time) {
		return toShortStr(new Date(time));
	}

	public static boolean bgrep(String regexp, String file, String encodingName) {
		boolean ret = false;
		if (encodingName == null)
			encodingName = "UTF-8"; // Default to UTF-8 encoding
		int flags = Pattern.MULTILINE; // Default regexp flags

		try {
			// Get the Charset for converting bytes to chars
			Charset charset = Charset.forName(encodingName);

			// Next argument must be a regexp. Compile it to a Pattern object
			Pattern pattern = Pattern.compile(regexp, flags);

			// Loop through each of the specified filenames
			CharBuffer chars; // This will hold complete text of the file
			try { // Handle per-file errors locally
					// Open a FileChannel to the named file
				FileInputStream stream = new FileInputStream(file);
				FileChannel f = stream.getChannel();

				// Memory-map the file into one big ByteBuffer. This is
				// easy but may be somewhat inefficient for short files.
				ByteBuffer bytes = f.map(FileChannel.MapMode.READ_ONLY, 0,
						f.size());

				// We can close the file once it is is mapped into memory.
				// Closing the stream closes the channel, too.
				stream.close();

				// Decode the entire ByteBuffer into one big CharBuffer
				chars = charset.decode(bytes);
			} catch (IOException e) { // File not found or other problem
				Utils.log.error("", e); // Print error message
				return false;
			}

			// This is the basic regexp loop for finding all matches in a
			// CharSequence. Note that CharBuffer implements CharSequence.
			// A Matcher holds state for a given Pattern and text.
			Matcher matcher = pattern.matcher(chars);
			while (matcher.find()) { // While there are more matches
				// Print out details of the match
				Utils.log.info(file + ":" + // file name
						matcher.start() + ": " + // character pos
						matcher.group()); // matching text
				return true;
			}
		} catch (UnsupportedCharsetException e) { // Bad encoding name
			Utils.log.error("Unknown encoding: " + encodingName);
		} catch (PatternSyntaxException e) { // Bad pattern
			Utils.log.error("Syntax error in search pattern:\n"
					+ e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) { // Wrong number of
			// arguments
		}
		return ret;
	}

	public static <T> List<T> asList(T[] docs) {
		return Arrays.asList(docs);
	}

	public static<T> T elementAt(List<T> data, Integer index,T def) {
		if(data==null || index <0 || index >= data.size())
			return def;
		else
			return data.get(index);
		
	}

	public static<T> List<T> subList(List<T> data, int[] rows) {
		List<T> li = new ArrayList<T>();
		for (int i : rows) {
			li.add(elementAt(data,i,null));
		}
		removeNull(li);
		return li;
	}

	public static<T> boolean isEmpty(List<T> sal) {
		return sal == null && sal.size() <= 0;
	}

	public static<T> int at(T[] ar, T pos, int def) {
		if(ar == null )
			return def;
		for(int i=0;i<ar.length;i++){
			if(pos == ar[i])
				return i;
		}
		return def;
	}

	public static int nextInt(int size) {
		return (int) Math.round(Math.random()*size);
	}

	public static <T> boolean isNull(T sa) {
		return sa == null;
	}

}
