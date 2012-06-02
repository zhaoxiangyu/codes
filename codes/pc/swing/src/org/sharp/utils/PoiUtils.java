package org.sharp.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.sharp.intf.ContentParser;
import org.sharp.jdkex.Utils;

public class PoiUtils {

	public static class Phrase {
		public String text;
		public int color;
		public static Phrase paragraphSeperator = new Phrase("\n", 0);

		public Phrase(String text, int color) {
			this.text = text;
			this.color = color;
		}

		public static List<Phrase> filter(List<Phrase> all, int color) {
			List<Phrase> result = new ArrayList<Phrase>();
			for (Iterator<Phrase> iterator = all.iterator(); iterator.hasNext();) {
				Phrase phrase = (Phrase) iterator.next();
				if (phrase.color == color) {
					result.add(phrase);
				}
			}
			return result;
		}
	}

	public static String extractWordText(File docFile)
			throws FileNotFoundException, IOException {
		WordExtractor we = new WordExtractor(new FileInputStream(docFile));
		return we.getText();
	}

	public static ContentParser newWorddocConentParser() {
		return new ContentParser() {
			public InputStream contentStream(File file) {
				try {
					return new ByteArrayInputStream(extractWordText(file)
							.getBytes());
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("parse word file error.");
				}
			}
		};
	}

	public static void parseWordText(File docFile, List<Phrase> phrases,
			Set<Integer> colorSet) throws FileNotFoundException, IOException {
		HWPFDocument hwpfDoc = new HWPFDocument(new FileInputStream(docFile));
		Range overallRange = hwpfDoc.getOverallRange();
		int npr = overallRange.numParagraphs();
		for (int j = 0; j < npr; j++) {
			Paragraph pr = overallRange.getParagraph(j);
			int ncr = pr.numCharacterRuns();
			for (int i = 0; i < ncr; i++) {
				CharacterRun cr = pr.getCharacterRun(i);
				int color = cr.getColor();
				String text = cr.text();
				boolean embossed = cr.isEmbossed();
				boolean imprinted = cr.isImprinted();
				boolean outlined = cr.isOutlined();
				boolean shadowed = cr.isShadowed();
				boolean highlighted = cr.isHighlighted();
				Utils.log.info("CharacterRun:color," + color + ";" + "("
						+ (embossed ? "embossed" : " ")
						+ (imprinted ? "imprinted" : " ")
						+ (outlined ? "outlined" : " ")
						+ (shadowed ? "shadowed" : " ")
						+ (highlighted ? "highlighted" : " ") + ")" + "text,"
						+ text);
				phrases.add(new Phrase(text, color));
				if (!colorSet.contains(color)) {
					colorSet.add(color);
				}
			}
			phrases.add(Phrase.paragraphSeperator);
		}
	}

	public static String doc2Html(File docFile) throws FileNotFoundException, IOException {
		StringBuffer ret = new StringBuffer();
		HWPFDocument hwpfDoc = new HWPFDocument(new FileInputStream(docFile));
		Utils.log.info("hwpfDoc:"+ToStringBuilder.reflectionToString(hwpfDoc));
		Range overallRange = hwpfDoc.getOverallRange();
		int npr = overallRange.numParagraphs();
		for (int j = 0; j < npr; j++) {
			Paragraph pr = overallRange.getParagraph(j);
			int ncr = pr.numCharacterRuns();
			for (int i = 0; i < ncr; i++) {
				CharacterRun cr = pr.getCharacterRun(i);
				int color = cr.getColor();
				String text = cr.text();
			}
		}
		return ret.toString();
	}
	
}
