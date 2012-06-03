#include "jniUtils.h"

const char* jni_getStringUTFChars(JNIEnv *env,jstring jstr){
	const char* str = (*env)->GetStringUTFChars(env,jstr, NULL);
	return str;
}

jstring jni_newStringUTF(JNIEnv *env,char* str){
	jstring jstr = (*env)->NewStringUTF(env, str);
	return jstr;
}

bool jni_setObjectField(JNIEnv *env,jobject obj,const char* fname,
		const char* fdesc, jobject value){
	jfieldID jfid = jni_getFieldID(env,obj,fname,fdesc);
	if(jfid == NULL){
		return false;
	}else{
		(*env)->SetObjectField(env,obj,jfid,value);
		return true;
	}
}

jfieldID jni_getFieldID(JNIEnv *env,jobject obj,const char* fname,
		const char* fdesc){
    jclass cls = (*env)->GetObjectClass(env,obj);
    return (*env)->GetFieldID(env, cls, fname, fdesc);
}

const char* FD_STRING = "Ljava/lang/String;";

