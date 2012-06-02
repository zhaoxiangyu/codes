#include "../build/c/org_sharp_console_jni_Win32.h"

JNIEXPORT jstring JNICALL Java_org_sharp_console_jni_Win32_sayHello
  (JNIEnv * env, jclass jc, jstring js){
	return (*env)->NewStringUTF(env, "Hello from JNI !");
}

JNIEXPORT jstring JNICALL Java_org_sharp_console_jni_Win32_lnkTarget
  (JNIEnv * env, jclass jc, jstring js){
	return (*env)->NewStringUTF(env, "About to link target !");
}
