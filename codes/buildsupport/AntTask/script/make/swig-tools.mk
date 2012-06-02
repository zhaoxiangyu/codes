include script/make/settings.mk
include script/make/env-settings.mk

all:
	install
	
install:
	echo $(SU_PW) | sudo -S apt-get install swig
	
swiglib: JNI_INC ?=-I/home/he/sw/jdk-6u25/jdk1.6.0_25/include -I/home/he/sw/jdk-6u25/jdk1.6.0_25/include/linux 
swiglib: INC_DIRS =$(JNI_INC) -Ijni/src/swig
swiglib: OUT_LIB ?=bin/swig/lib$(MODULE_NAME).so
swiglib: LDFLAGS =-fPIC -shared
swiglib: CC=g++
swiglib:
	$(CC) -c $(INC_DIRS) jni/swig/c/jpvoc_wrap.cpp -o $(OBJ_DIR)/jpvoc_wrap.o
	mkdir -p $(dir $(OUT_LIB))
	$(LD) $(LDFLAGS) $(LIB_DIRS) $(OBJ_DIR)/jpvoc_wrap.o $(OBJ_DIR)/jpVoc.o \
	$(OBJ_DIR)/JpVocabularyParser.o $(OBJ_DIR)/JpVocabularyLexer.o \
	 -lantlr3c -o$(OUT_LIB)