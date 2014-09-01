package org.sharpx.vocreader.jni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.sharpx.vocreader.jni.beans.Vocabulary;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Vocabulary voc = new Vocabulary();
		if(args.length==0){
			VocabularySupport.parseJpVoc("あいます(合います) ［动1］合适，适合", voc );
			System.out.println("main\tpronun:"+voc.pronun+
					"writing:"+voc.writing+"partOfSpeech:"+voc.partOfSpeech+
					"expl:"+voc.expl);
		}else{
			File file = new File(args[0]);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			do{
				String line = reader.readLine();
				if(line == null)
					break;
				VocabularySupport.parseJpVoc(line, voc );
				System.out.println("pronun:"+voc.pronun+
						"writing:"+voc.writing+"partOfSpeech:"+voc.partOfSpeech+
						"expl:"+voc.expl);
			}while(true);
		}
	}

}
