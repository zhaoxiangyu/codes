#MKF_DIR := $(dir $(lastword $(MAKEFILE_LIST)))
#MODULE_NAME ?=
ifndef MODULE_NAME
  $(error MODULE_NAME not set)
endif

#SRC_DIR ?=
ifndef SRC_DIR
  $(warning SRC_DIR not set)
else
  SRC_FILES ?=$(shell find $(SRC_DIR) -name *.cpp -o -name *.c)
  SRC_FILES :=$(subst $(SRC_DIR)/, ,$(SRC_FILES))
endif

ifdef INC_PATH
  INC_PATH :=$(subst :, ,$(INC_PATH))
  INC_DIRS +=$(addprefix -I,$(INC_PATH))
endif

ifdef LIB_PATH
  LIB_PATH :=$(subst :, ,$(LIB_PATH))
  LIB_DIRS +=$(addprefix -L,$(LIB_PATH))
endif

LIB_NAMES ?=
LIBS :=$(addprefix -l,$(LIB_NAMES))

CFLAGS += -g

TEMP :=$(subst .cpp,.o,$(SRC_FILES))
OBJS :=$(subst .c,.o,$(TEMP))
OBJ_DIR ?=bin/obj

EXE_DIR ?=bin/out
JNI_DIR ?=bin/jni
LIB_DIR ?=bin/lib
PRE_TARGET ?=

LIB_DIRS +=-L$(LIB_DIR)
VPATH := $(OBJ_DIR):$(SRC_DIR)

ifndef MAIN_SRC
#  $(error MAIN_SRC not set)
else
  MAIN_FILE := $(subst $(SRC_DIR)/, ,$(MAIN_SRC))
  TEMP :=$(subst .cpp,.o,$(MAIN_FILE))
  MAIN_OBJ :=$(subst .c,.o,$(MAIN_FILE))
endif

ifndef JNISRC_DIR
#  $(error JNISRC_DIR not set)
else
  VPATH +=$(JNISRC_DIR)
  ifndef JNISRC_FILES
    JNISRC_FILES :=$(shell find $(JNISRC_DIR) -name *.cpp -o -name *.c)
  endif
  JNISRC_FILES :=$(subst $(JNISRC_DIR)/, ,$(JNISRC_FILES))
  TEMP :=$(subst .cpp,.o,$(JNISRC_FILES))
  JNI_OBJS :=$(subst .c,.o,$(TEMP))
endif

##########################################
.PHONY : build
build: $(PRE_TARGET) clean sharedlib dynamiclink

##########################################
.PHONY : jnilib
jnilib: echo_vars $(PRE_TARGET) clean jnilibe jnilibe-after

.PHONY : jnilibe
jnilibe: LDFLAGS +=-fPIC -shared -static
jnilibe: $(OBJS) $(JNI_OBJS)
	mkdir -p $(JNI_DIR)
	$(LD) $(LDFLAGS) $(LIB_DIRS) \
	 $(addprefix $(OBJ_DIR)/,$(JNI_OBJS)) \
	 $(addprefix $(OBJ_DIR)/,$(OBJS)) \
	 $(LIBS) -lstdc++ -lsupc++ -o $(JNI_DIR)/lib$(MODULE_NAME).so

.PHONY : jnilibe-after
jnilibe-after: ;

.PHONY : echo_vars
echo_vars:
	@echo VPATH: $(VPATH)
	@echo OBJS: $(OBJS)
	@echo JNISRC_FILES: $(JNISRC_FILES)
	@echo JNISRC_DIR: $(JNISRC_DIR)
	@echo JNI_OBJS: $(JNI_OBJS)
	@echo LIBS: $(LIBS)
	@echo INC_DIRS: $(INC_DIRS)
	@echo LIB_DIRS: $(LIB_DIRS)
	@echo CC: $(CC)
	@echo CXX: $(CXX)
	@echo LD: $(LD)
	@echo PATH: $(PATH)
	
.PHONY : clean
clean :
	rm -rf $(OBJ_DIR)
	rm -rf $(EXE_DIR)
	rm -rf $(LIB_DIR)
	rm -rf $(JNI_DIR)
	-mkdir -p $(OBJ_DIR)
	-mkdir -p $(EXE_DIR)
	-mkdir -p $(LIB_DIR)
	-mkdir -p $(JNI_DIR)

###################################################
.PHONY : run-dynamiclink
run-dynamiclink: sharedlib-after
	export LD_LIBRARY_PATH=$(LIB_PATH):$(LIB_DIR) \
	; ldd $(EXE_DIR)/$(MODULE_NAME)-dl \
	; $(EXE_DIR)/$(MODULE_NAME)-dl $(PROG_ARGS)

.PHONY : sharedlib-after
sharedlib-after:
	@echo running sharedlib-after in toolchain-gcc.mk

.PHONY : run-staticlink
run-staticlink:
	$(EXE_DIR)/$(MODULE_NAME)-sl $(PROG_ARGS)
	
.PHONY : exeutable
exeutable: staticlink dynamiclink

.PHONY : staticlink
staticlink: LDFLAGS+=-static -rdynamic
staticlink: staticlib $(MAIN_OBJ)
	$(LD) $(LDFLAGS) $(LIB_DIRS) $(addprefix $(OBJ_DIR)/,$(MAIN_OBJ)) \
	-l$(MODULE_NAME) $(LIBS) -o $(EXE_DIR)/$(MODULE_NAME)-sl
	
.PHONY : dynamiclink
dynamiclink: LDFLAGS+=-rdynamic
dynamiclink: sharedlib $(MAIN_OBJ)
	$(LD) $(LDFLAGS) $(LIB_DIRS) $(addprefix $(OBJ_DIR)/,$(MAIN_OBJ)) \
	 -l$(MODULE_NAME) $(LIBS) -o $(EXE_DIR)/$(MODULE_NAME)-dl
	
.PHONY : staticlib
staticlib:$(OBJS)
	$(AR) -r $(LIB_DIR)/lib$(MODULE_NAME).a \
	$(addprefix $(OBJ_DIR)/,$^) 
	
.PHONY : sharedlib
sharedlib: LDFLAGS +=-fPIC -shared
sharedlib:$(OBJS)
	$(LD) $(LDFLAGS) $(LIB_DIRS) $(addprefix $(OBJ_DIR)/,$^) \
	 $(LIBS) -o $(LIB_DIR)/lib$(MODULE_NAME).so

%.o: %.cpp
	mkdir -p $(dir $(OBJ_DIR)/$@)
	$(CXX) -c $(CFLAGS) $(INC_DIRS) $< -o $(OBJ_DIR)/$@

%.o: %.c
	mkdir -p $(dir $(OBJ_DIR)/$@)
	$(CC) -c $(CFLAGS) $(INC_DIRS) $< -o $(OBJ_DIR)/$@

