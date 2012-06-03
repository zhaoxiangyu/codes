ENV_SETTINGS_MF_DIR ?= /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/script/make
include $(ENV_SETTINGS_MF_DIR)/settings.mk
include $(ENV_SETTINGS_MF_DIR)/ndk-build.mk

export MY_ANDROID_MK=src/jpVocParser/Android.mk
export PRJ_LOC=jni