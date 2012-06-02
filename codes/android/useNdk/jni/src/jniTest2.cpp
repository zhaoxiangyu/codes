#include <string.h>
#include <jni.h>
#include "jni-codetemplate.h"

FD_hello
//CD_Person

jstring Java_org_sharp_vocreader_core_testJniStringCxx(JNIEnv* env,
                                                  jobject thiz)
{
	INT(i,0)
	hello("world");
    return env->NewStringUTF("Hello from JNI c++ !");
}
