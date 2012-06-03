#include "sharpx_jni_ubuntu_Test2JniImpl.h"
#include <stdio.h>

void JNICALL Java_sharpx_jni_ubuntu_Test2JniImpl_staticHaha(JNIEnv *jenv, jclass jcl){
	printf("statichaha called\n");
}

void JNICALL Java_sharpx_jni_ubuntu_Test2JniImpl_haha(JNIEnv *jenv, jobject jobj){
	printf("haha called\n");
}

jstring JNICALL Java_sharpx_jni_ubuntu_Test2JniImpl_abc(JNIEnv *jenv, jobject jobj, jint ji){
	return jenv->NewStringUTF("Hello from JNI c++ !");
}

void JNICALL Java_sharpx_jni_ubuntu_Test2JniImpl_callBackJ(JNIEnv *jenv, jobject jo1, jobject jo2){
	printf("callBackJ called\n");
}
