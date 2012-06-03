LOCAL_MODULE    := vocabulary

LOCAL_SRC_FILES += src/jpVocParser/javah/vocabularySupport.cpp
LOCAL_SRC_FILES += src/jniUtils.cpp
LOCAL_SRC_FILES += src/jpVocParser/jpVoc.c
LOCAL_SRC_FILES += ../antlr/gen/c/JpVocabularyLexer.c
LOCAL_SRC_FILES += ../antlr/gen/c/JpVocabularyParser.c

LOCAL_C_INCLUDES += $(LOCAL_PATH)/src
LOCAL_C_INCLUDES += $(LOCAL_PATH)/src/jpVocParser
LOCAL_C_INCLUDES += $(LOCAL_PATH)/gen/javah/jpVocParser
LOCAL_C_INCLUDES += $(LOCAL_PATH)/../antlr/gen/c
LOCAL_C_INCLUDES += /home/he/antlr/build-ndk/include

LOCAL_CPP_FEATURES := rtti exceptions
#LOCAL_STATIC_LIBRARIES += -L$(LOCAL_PATH)/../bin/libantlr3c-ndk/lib -lantlr3c
#LOCAL_STATIC_LIBRARIES += -L/home/he/sw/android/android-ndk-r7/sources/cxx-stl/gnu-libstdc++/libs/armeabi -lsupc++
LOCAL_LDLIBS += -L/home/he/antlr/build-ndk/lib -lantlr3c
LOCAL_LDLIBS += -L/home/he/sw/android/android-ndk-r7/sources/cxx-stl/gnu-libstdc++/libs/armeabi -lsupc++
LOCAL_LDLIBS += -lstdc++
LOCAL_LDLIBS += -static