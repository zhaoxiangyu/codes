#include "sharpx_jni_Test.h"

jstring JNICALL Java_sharpx_jni_Test_testJniCpp(JNIEnv* env, jobject thiz, jint i)
{
	Bean bean();
	bean.id();
	bean.getName();
    return env->NewStringUTF("Hello from JNI c++ !");
}
