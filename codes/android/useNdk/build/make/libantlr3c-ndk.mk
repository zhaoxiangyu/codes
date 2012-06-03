ENV_SETTINGS_MF_DIR ?= /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/script/make
include $(ENV_SETTINGS_MF_DIR)/settings.mk
include $(ENV_SETTINGS_MF_DIR)/toolchain-ndk.mk

include $(PRJ_ANTTASK_DIR)/script/make/antlr-install.mk

PRJ_DIR := jni/3rd/libantlr3c-3.4
CONFIG_INSTALLDIR := $(PRJ_DIR)/automake/build-aux
CONFIG_HOST := --host=arm-linux-androideabi
CONFIG_PREFIX := --prefix $(shell pwd)/bin/libantlr3c-ndk
include $(ENV_SETTINGS_MF_DIR)/autoconf-tools.mk

checklib:
	file bin/libantlr3c-ndk/lib/*
	cd $(PRJ_DIR);file automake/*.lo;file automake/*.o
