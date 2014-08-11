ENV_SETTINGS_MF_DIR ?= /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/script/make
include $(ENV_SETTINGS_MF_DIR)/settings.mk
include $(ENV_SETTINGS_MF_DIR)/toolchain-ndk.mk
include $(PRJ_ANTTASK_DIR)/script/make/antlr-install.mk
include $(PRJ_ANTTASK_DIR)/script/make/autoconf-tools.mk

#/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/useNdk/antlr/gen/c
ANTLR_GEN_DIR ?=antlr/gen/c

INC_DIRS +=-I$(ANTLR_NDK_INC_DIR)
LIB_DIRS +=-L$(ANTLR_NDK_LIB_DIR)
#PROG_ARGS ?=$(PRJ_ANTTASK_DIR)/bin/voca.filenames
VPATH += :$(ANTLR_GEN_DIR)

MODULE_NAME ?= vocabulary
OBJ_DIR ?=bin/ndk-obj
EXE_DIR ?=bin/ndk-out
JNI_DIR ?=bin/ndk-jni
LIB_DIR ?=bin/ndk-lib
#PRE_TARGET += configure-upgrade
PRE_TARGET += antlr-ndk-build

include $(ENV_SETTINGS_MF_DIR)/prj-template.mk
###############################################################################
.PHONY : jnilibe-after
jnilibe-after:
#	cp $(ANTLR_LIB_DIR)/libantlr3c.so $(JNI_DIR)
#	cp $(ANTLR_LIB_DIR)/libantlr3c.a $(JNI_DIR)
	-nm $(JNI_DIR)/lib$(MODULE_NAME).so | grep parseMp3Name
	-nm $(JNI_DIR)/lib$(MODULE_NAME).so | grep __cxa_allocate_exception
	-nm $(JNI_DIR)/lib$(MODULE_NAME).so | grep JNI_OnLoad
#	-nm $(ANTLR_LIB_DIR)/libantlr3c.so | grep _ZN3JnuD1Ev
	export LD_LIBRARY_PATH=$(JNI_DIR) \
	;ldd $(JNI_DIR)/lib$(MODULE_NAME).so \
	;file $(JNI_DIR)/lib$(MODULE_NAME).so

.PHONY : sharedlib-after
sharedlib-after:
	@echo running sharedlib-after in jpVocParser.mk
	cp $(ANTLR_LIB_DIR)/libantlr3c.so $(LIB_DIR)