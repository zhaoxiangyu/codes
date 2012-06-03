#include <jni.h>

#define INT(name,value) int name = value;

#define FD_hello \
void hello(char* msg) { \
	/*printf(msg);*/ \
}

#define CD_Person \
class Person { \
}
