#include <jni.h>
#include <stdio.h>

#ifdef __cplusplus

#ifndef NULL
	#define NULL 0
#endif

jfieldID jni_getFieldID(JNIEnv *env,jobject obj,const char* fname,const char* fdesc);

////////////////////////////////////////////////////////////////////////////
class Jnu {

public:
	Jnu(JNIEnv* env){
		this->env = env;
	}
//	virtual ~Jnu();

	const char* getStringUTFChars(jstring jstr){
		const char* str = env->GetStringUTFChars(jstr, NULL);
	    if (str == NULL) {
			printf("getStringUTFChars return NULL");
	        throw 1; /* OutOfMemoryError already thrown */
	    }else
	    	return str;
	}

	jstring newStringUTF(char* str){
		jstring jstr = env->NewStringUTF(str);
		if(jstr == NULL){
			printf("newStringUTF return NULL");
			throw 1;
		}else
			return jstr;
	}

	int setObjectField(jobject obj,const char* fname,jint value){
		return 0;
	}

	int setObjectField(jobject obj,const char* fname,jstring value){
		jfieldID jfid = ::jni_getFieldID(env,obj,fname,FD_STRING);

		if(jfid == NULL){
			printf("::jni_getFieldID return NULL");
			throw 1;
		}else{
			env->SetObjectField(obj,jfid,value);
		}
		return 0;
	}

private:

	static const char* FD_STRING;
	JNIEnv* env;
};
#else

bool jni_setObjectField(JNIEnv *env,jobject obj,const char* fname,
		const char* fdesc,jobject value);

jfieldID jni_getFieldID(JNIEnv *env,jobject obj,const char* fname,
		const char* fdesc);

jstring jni_newStringUTF(JNIEnv *env,char* str);

const char* jni_getStringUTFChars(JNIEnv *env,jstring jstr);

//////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////

#endif

