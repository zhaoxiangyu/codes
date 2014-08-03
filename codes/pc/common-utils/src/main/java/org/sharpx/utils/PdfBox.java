package org.sharpx.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.pdfbox.searchengine.lucene.LucenePDFDocument;

public class PdfBox {
	public static Document getDocument(File file){
		try {
			return LucenePDFDocument.getDocument(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
