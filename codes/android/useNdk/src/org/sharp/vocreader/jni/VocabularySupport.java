package org.sharp.vocreader.jni;

import org.sharp.vocreader.jni.beans.Vocabulary;

public class VocabularySupport {

    static {
//    	System.loadLibrary("antlr3c");
//    	System.load("/data/data/sharpx.vocreader/lib/libvocabulary.so");
//    	String libName = System.mapLibraryName("vocabulary");
//    	System.out.println("mapped library name:"+libName);
   		System.loadLibrary("vocabulary");
    }
    
    public static boolean parseJpVoc(String mp3name, Vocabulary voc){
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
    		if(mp3name != null && voc != null){
    	    	System.gc();
    			System.out.println("parsing "+mp3name);
    			return parseMp3Name(mp3name,voc);
    		}else
    			return false;
    	}
    	return true;
    }
    
	public native static boolean parseMp3Name(String mp3name,Vocabulary voc);
}
