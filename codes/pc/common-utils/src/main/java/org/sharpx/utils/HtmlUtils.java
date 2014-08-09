package org.sharpx.utils;

import java.io.FileInputStream;
import java.io.Reader;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;


public class HtmlUtils {

	public static String htmlFile2Text(String path, int maxlength) {
		Source source;
		try {
			source = new Source(new FileInputStream(path));
			Renderer renderer = new Renderer(new Segment(source, 0,
					source.length()));
			renderer.setMaxLineLength(maxlength);
			renderer.setIncludeHyperlinkURLs(false);
			return renderer.toString();
		} catch (Exception e) {
			DsUtils.log.error("", e);
		}
		return "";
	}
	
	public static String htmlFile2Text(Reader reader) {
		Source source;
		try {
			source = new Source(reader);
			Renderer renderer = new Renderer(new Segment(source, 0,
					source.length()));
			//renderer.setMaxLineLength(maxlength);
			renderer.setIncludeHyperlinkURLs(false);
			return renderer.toString();
		} catch (Exception e) {
			DsUtils.log.error("", e);
		}
		return "";
	}
	
}
