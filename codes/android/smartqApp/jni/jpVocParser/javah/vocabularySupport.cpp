#include "org_sharp_vocreader_jni_VocabularySupport.h"
#include "jpVoc.h"
#include "jniUtils.h"

JNIEXPORT jboolean JNICALL Java_org_sharp_vocreader_jni_VocabularySupport_parseMp3Name(
		JNIEnv *jenv, jclass jcl,jstring jstr, jobject jvi){

	Jnu jnu(jenv);

	const char* str = jnu.getStringUTFChars(jstr);
//    printf("Java_org_sharp_vocreader_jni_VocabularySupport_parseMp3Name:%s\n", str);

	VocInfo vi;
	initVocInfo(&vi);
	short ret = parseJpVoc(str, vi);

//	printf("Java_org_sharp_vocreader_jni_VocabularySupport_parseMp3Name:pronun:%s\twriting:%s\tpartOfSpeech:%s\texpl:%s\n",
//    	vi.pronun, vi.writing, vi.partOfSpeech, vi.expl);
	try{
		jstring pronun = jnu.newStringUTF(vi.pronun);
		jstring writing = jnu.newStringUTF(vi.writing);
		jstring partOfSpeech = jnu.newStringUTF(vi.partOfSpeech);
		jstring expl = jnu.newStringUTF(vi.expl);

		jnu.setObjectField(jvi, "pronun", pronun);
		jnu.setObjectField(jvi, "writing", writing);
		jnu.setObjectField(jvi, "partOfSpeech", partOfSpeech);
		jnu.setObjectField(jvi, "expl", expl);
	}catch(int i){
		printf("exception caught.");
	}

    clearVocInfo(&vi);
    return ret == 0;
}
