package org.sharpx.parser.antlr;

import java.io.IOException;
import java.lang.reflect.Method;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.sharpx.parser.antlr.JpVocabularyLexer;
import org.sharpx.parser.antlr.JpVocabularyParser;
import org.sharpx.parser.antlr.JpVocabularyParser.vocabulary_return;


public class JpVocabulary {
	
	public static boolean parseJpVocx(String mp3name, Vocabulary voc){
    	if("あんまり　（副） 太，非常，过于".equals(mp3name)){
    		voc.pronun="あんまり";
    		voc.writing="";
    		voc.partOfSpeech="［副］";
    		voc.expl="太，非常，过于";
    	}else if("あキューせいでん（阿Q正伝［专］）阿Q正传".equals(mp3name)){
    		voc.pronun="あキューせいでん";
    		voc.writing="阿Q正伝";
    		voc.partOfSpeech="［专］";
    		voc.expl="阿Q正传";
    	}else if("ばんりのちょうじょう（万里の長城 〔专〕 万里长城".equals(mp3name)){
    		voc.pronun="ばんりのちょうじょう";
    		voc.writing="万里の長城";
    		voc.partOfSpeech="〔专〕";
    		voc.expl="万里长城";
    	}else if("查（看），弄清".equals(mp3name)){
    		voc.pronun="こい";
    		voc.writing="濃い";
    		voc.partOfSpeech="〔形1〕";
    		voc.expl="（口味）重；浓";
    	}else if("～".equals(mp3name)){
    		voc.pronun="じょう";
    		voc.writing="上";
    		voc.partOfSpeech="";
    		voc.expl="";
    	}else if("をたし 〔代〕 我".equals(mp3name)){
    		voc.pronun="わたし";
    		voc.writing="上";
    		voc.partOfSpeech="〔代〕";
    		voc.expl="我";
    	}else{
        	return parseJpVoc(mp3name,voc);
    	}
    	return false;
    }
	
	public static boolean parseJpVoc(String mp3name, Vocabulary voc){
		JpVocabularyLexer lexer = new JpVocabularyLexer(
				new ANTLRStringStream(mp3name));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JpVocabularyParser parser = new JpVocabularyParser(tokens);
		try {
			vocabulary_return vocabulary = parser.vocabulary();
			voc.pronun = vocabulary.pronun;
			voc.writing = vocabulary.writing;
			voc.partOfSpeech = vocabulary.partOfSpeech;
			voc.expl = vocabulary.expl;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int main(String[] args) throws IOException{
		
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		JpVocabularyLexer lexer = new JpVocabularyLexer(input);
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JpVocabularyParser parser = new JpVocabularyParser(tokens);
		try {
			if(args.length == 0) {
				vocabulary_return vocabulary = parser.vocabulary();
				System.out.print("pronun="+vocabulary.pronun);
				System.out.print("\twriting="+vocabulary.writing);
				System.out.print("\tpartOfSpeech="+vocabulary.partOfSpeech);
				System.out.print("\texpl="+vocabulary.expl);
			} else if(args.length == 1){
				Method m = JpVocabularyParser.class.getMethod(args[0]);
				if(m!=null)
					m.invoke(parser);
				else{
					System.err.println("rule named "+args[0]+" not exists.");
					return -2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
