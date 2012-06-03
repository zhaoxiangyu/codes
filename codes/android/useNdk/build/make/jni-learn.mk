ENV_SETTINGS_MF_DIR ?= /home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/script/make
include $(ENV_SETTINGS_MF_DIR)/settings.mk
include $(ENV_SETTINGS_MF_DIR)/env-settings.mk

ANTLR_GEN_DIR ?=/home/he/sw/android/Indigo-SR1+ADT+CDT+DLTK+ANTLR/codes/natviewer/android/AntTask/antlr/gen/c
INSTALL_DIR ?= bin/ant/classes
INSTALL_LIBNAME ?=jnilearn

OBJ_DIR =bin/obj/jnilearn
OUT_DIR =bin/out/jnilearn

SRC_DIR =jni/src/javah
INC_DIRS =-Ijni/javah/header
LIB_DIRS =

LDFLAGS+=-fPIC -shared
OBJS = test2JniImpl.o

VPATH = $(SRC_DIR)

.PHONY : echo_vars
echo_vars:
	@echo VPATH: $(VPATH)
	@echo OBJS: $(OBJS)
	
all: out install

out: $(OBJS)
	mkdir -p $(OUT_DIR)
	$(LD) $(LDFLAGS) $(LIB_DIRS) $(addprefix $(OBJ_DIR)/,$^) \
	-o $(OUT_DIR)/libjnilearn.so
	
install:
	mkdir -p $(INSTALL_DIR)
	cp $(OUT_DIR)/libjnilearn.so $(INSTALL_DIR)/lib$(INSTALL_LIBNAME).so
	
%.o: %.cpp
	mkdir -p $(OBJ_DIR)
	$(CC) -c $(CFLAGS) $(INC_DIRS) $< -o $(OBJ_DIR)/$@
	