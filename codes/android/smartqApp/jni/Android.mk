LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

TARGET_PLATFORM := android-7
TARGET_ARCH_ABI := armeabi

LOCAL_MODULE    := vocabulary

MY_JPVOCPARSER_DIR := jpVocParser

LOCAL_SRC_FILES += ${MY_JPVOCPARSER_DIR}/javah/vocabularySupport.cpp
LOCAL_SRC_FILES += ${MY_JPVOCPARSER_DIR}/jpVoc.c
LOCAL_SRC_FILES += ${MY_JPVOCPARSER_DIR}/jniUtils.cpp
LOCAL_SRC_FILES += ${MY_JPVOCPARSER_DIR}/antlr-gen/JpVocabularyLexer.c
LOCAL_SRC_FILES += ${MY_JPVOCPARSER_DIR}/antlr-gen/JpVocabularyParser.c

LOCAL_C_INCLUDES += $(LOCAL_PATH)/${MY_JPVOCPARSER_DIR}
LOCAL_C_INCLUDES += $(LOCAL_PATH)/${MY_JPVOCPARSER_DIR}/javah
LOCAL_C_INCLUDES += $(LOCAL_PATH)/${MY_JPVOCPARSER_DIR}/antlr-gen
LOCAL_C_INCLUDES += $(LOCAL_PATH)/libantlr3c-ndk/include

LOCAL_CPP_FEATURES := rtti exceptions
LOCAL_LDLIBS += -L$(LOCAL_PATH)/libantlr3c-ndk/lib -lantlr3c
LOCAL_LDLIBS += -L/home/he/sw/android/android-ndk-r7/sources/cxx-stl/gnu-libstdc++/libs/armeabi -lsupc++
LOCAL_LDLIBS += -lstdc++
LOCAL_LDLIBS += -static

include $(BUILD_SHARED_LIBRARY)
