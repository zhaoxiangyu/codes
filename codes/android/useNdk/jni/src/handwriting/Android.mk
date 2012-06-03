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

MY_ZINNIA_LIBDIR := $(LOCAL_PATH)/../test/zinnia-shared/lib

LOCAL_MODULE    := handwriting
LOCAL_SRC_FILES += src/handwriting.cpp
LOCAL_SRC_FILES += src/handwriting.c

LOCAL_C_INCLUDES += $(LOCAL_PATH)/3rd/zinnia-0.06

LOCAL_CPP_FEATURES := rtti exceptions
#LOCAL_CXXFLAGS := -DHAVE_PTHREADS -fexceptions
#LOCAL_CLAGS := -DHAVE_PTHREADS
LOCAL_CPPFLAGS := -DHAVE_PTHREADS
#LOCAL_CPPFLAGS += -fpermissive

LOCAL_SHARED_LIBRARIES += -L$(LOCAL_PATH)/3rd/zinnia-0.06/.libs -lzinnia
#LOCAL_LDLIBS += -L$(MY_ZINNIA_LIBDIR) -lzinnia

#LOCAL_LDLIBS += -lstdc++

LDFLAGS +=-Wl,-rpath -Wl,$(MY_ZINNIA_LIBDIR)