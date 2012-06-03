LOCAL_MODULE    := jnilearn
LOCAL_SRC_FILES += src/jnilearn/jniTest.c
LOCAL_SRC_FILES += src/jnilearn/jniTest2.cpp
LOCAL_SRC_FILES += src/jnilearn/javah/test2JniImpl.cpp

LOCAL_C_INCLUDES += $(LOCAL_PATH)/gen/javah/jni-learn
#LOCAL_CPP_FEATURES := rtti exceptions
#LOCAL_SHARED_LIBRARIES += -L$(LOCAL_PATH)/zinnia-0.06/.libs -lzinnia
#LOCAL_LDLIBS += -lstdc++
#LDFLAGS +=-Wl,-rpath -Wl
