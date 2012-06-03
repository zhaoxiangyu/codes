ENV_SETTINGS_MF_DIR ?= /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/script/make
include $(ENV_SETTINGS_MF_DIR)/autoconf-tools.mk

PRJ_DIR := jni/3rd/libantlr3c-3.4
#CONFIG_HOST := --host=arm-linux-androideabi
CONFIG_PREFIX := --prefix $(shell pwd)/bin/libantlr3c