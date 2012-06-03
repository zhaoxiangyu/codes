package org.sharp.vocreader.core;

import java.util.Arrays;
import java.util.List;

import org.sharp.utils.LogTags;
import org.sharp.vocreader.beans.AudioInfo;
import org.sharp.vocreader.beans.AudioInfoV2;
import org.sharp.vocreader.beans.State;
import org.sharp.vocreader.intf.OsSupport;
import org.sharp.vocreader.jni.VocabularySupport;
import org.sharp.vocreader.jni.beans.Vocabulary;

import android.util.Log;

public class AudioInfoUtils {
	@Deprecated
	public static void parseJpVoc(AudioInfoV2 ai){
		Vocabulary vocabulary = new Vocabulary();
		boolean suc = VocabularySupport.parseJpVoc(ai.name, vocabulary);
		if(suc){
			ai.pronun = vocabulary.pronun;
			ai.writing = vocabulary.writing;
			ai.partOfSpeech = vocabulary.partOfSpeech;
			ai.expl = vocabulary.expl;
		}
	}
	
	public static void parseJpVoc2(final OsSupport oss,AudioInfoV2 ai){
		AudioInfoV2 voc = null;
		voc = oss.loadAudioInfo(ai.name);
		if(voc != null){
			ai.pronun = voc.pronun;
			ai.writing = voc.writing;
			ai.partOfSpeech = voc.partOfSpeech;
			ai.expl = voc.expl;
		}else{
			Log.d(LogTags.LT_SQL, "audio name="+ai.name+" not found in DB");
		}
	}
	
	public static AudioInfoV2 parseJpVoc2(final OsSupport oss,AudioInfo ai){
		AudioInfoV2 voc = null;
		voc = oss.loadAudioInfo(ai.name);
		if(voc != null){
			voc.level = ai.level;
		}else{
			Log.d(LogTags.LT_SQL, "audio name="+ai.name+" not found in DB");
		}
		return voc;
	}
	
	public static void importAssetVocabulary(final OsSupport oss){
		AudioInfoV2[] aa = (AudioInfoV2[])oss.fromString(
				oss.readAssetResource("jpwords/voca.json"), AudioInfoV2[].class);
		oss.saveAudioInfos(Arrays.asList(aa));
	}
	
	public static AudioInfoV2 current(State state,List<AudioInfoV2> levelList){
		if(state == null || state.current<0 || state.current >= levelList.size())
			return null; 
		else
			return levelList.get(state.current);
	}

}