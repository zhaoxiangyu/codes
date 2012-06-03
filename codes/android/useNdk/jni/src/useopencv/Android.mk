LOCAL_MODULE    := useopencv

OPENCV_CAMERA_MODULES:=off
OPENCV_PACKAGE_DIR := /home/he/opencv/OpenCV-2.3.1-android-bin/OpenCV-2.3.1
include $(OPENCV_PACKAGE_DIR)/share/OpenCV/OpenCV.mk

LOCAL_SRC_FILES += src/jpVocParser/javah/vocabularySupport.cpp

LOCAL_C_INCLUDES += $(LOCAL_PATH)/src

LOCAL_CPP_FEATURES := rtti exceptions
LOCAL_LDLIBS += -L$(LOCAL_PATH)/../bin/libantlr3c-ndk/lib -lantlr3c
LOCAL_LDLIBS += -L/home/he/sw/android/android-ndk-r7/sources/cxx-stl/gnu-libstdc++/libs/armeabi -lsupc++
LOCAL_LDLIBS += -lstdc++
LOCAL_LDLIBS += -static