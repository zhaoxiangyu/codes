#include "jniUtils.h"

const char* Jnu::FD_STRING = "Ljava/lang/String;";

jfieldID jni_getFieldID(JNIEnv *env,jobject obj,const char* fname,const char* fdesc){
    jclass cls = env->GetObjectClass(obj);
    return env->GetFieldID(cls, fname, fdesc);
}
