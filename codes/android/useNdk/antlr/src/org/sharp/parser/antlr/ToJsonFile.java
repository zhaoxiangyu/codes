package org.sharp.parser.antlr;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ToJsonFile {

	public static int main(String[] args) throws IOException{
		if(args.length != 2)
			return 1;
		
		parseFile(args[0],args[1]);
		//"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/bin/voca.filenames"
		//"/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/bin/voca.json"
		return 0;
	}
	
	public static void parseFile(String fileIn, String fileOut){
		BufferedReader br = null;
		FileWriter fw = null;
		try {
			br = new BufferedReader(new FileReader(fileIn));
			String line = null;
			Vocabulary voc = null;
			List<Vocabulary> vocList = new ArrayList<Vocabulary>();
			do{
				line = br.readLine();
				if(line == null)
					break;
				line = line.trim();
				if(line.length()==0)
					continue;
				voc = new Vocabulary();
				System.out.println(line);
				voc.name = line;
				JpVocabulary.parseJpVocx(line, voc);
				vocList.add(voc);
			}while(true);
			Type type = new TypeToken<List<Vocabulary>>(){}.getType();
			Gson gson = new Gson();
			String out = gson.toJson(vocList, type);
			fw = new FileWriter(fileOut);
			fw.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)
					br.close();
				if(fw!=null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
