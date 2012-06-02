# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

LOCAL_PATH := $(call my-dir)
MY_ZINNIA_LIBDIR := $(LOCAL_PATH)/../test/zinnia-shared/lib
MY_JACE_HEADERDIR := $(LOCAL_PATH)/../../AntTask/tools/jace1.1.1/release/include
#MY_ZINNIA_LIBDIR := /usr/local/lib

include $(CLEAR_VARS)

LOCAL_MODULE    := handwriting
LOCAL_SRC_FILES := src/jniTest.c
LOCAL_SRC_FILES += src/jniTest2.cpp
#LOCAL_SRC_FILES += src/handwriting.cpp
#LOCAL_SRC_FILES += src/handwriting.c

#LOCAL_SRC_FILES += jace/jace/peer/sharpx/jni/Bean_peer.cpp jace/jace/peer/sharpx/jni/BeanMappings.cpp
#LOCAL_SRC_FILES += jace/jace/proxy/sharpx/jni/Bean.cpp

LOCAL_C_INCLUDES += $(LOCAL_PATH)/zinnia-0.06
LOCAL_C_INCLUDES += $(MY_JACE_HEADERDIR)
LOCAL_C_INCLUDES += $(LOCAL_PATH)/jace
LOCAL_C_INCLUDES += $(LOCAL_PATH)/jace/rt-gen

LOCAL_CPP_FEATURES := rtti exceptions
#LOCAL_CXXFLAGS := -DHAVE_PTHREADS -fexceptions
#LOCAL_CLAGS := -DHAVE_PTHREADS
LOCAL_CPPFLAGS := -DHAVE_PTHREADS
#LOCAL_CPPFLAGS += -fpermissive

#LOCAL_SHARED_LIBRARIES += -L$(LOCAL_PATH)/zinnia-0.06/.libs -lzinnia
#LOCAL_LDLIBS += -L$(MY_ZINNIA_LIBDIR) -lzinnia
#LOCAL_LDLIBS += -lstdc++

LDFLAGS +=-Wl,-rpath -Wl,$(MY_ZINNIA_LIBDIR)

include $(BUILD_SHARED_LIBRARY)
