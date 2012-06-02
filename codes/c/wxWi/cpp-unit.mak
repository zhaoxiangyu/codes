include common-vars.mak

CPP_UNIT := cppunit-1.12.1
GZ_FILE := $(DOWN_PATH)/$(CPP_UNIT).tar.gz
TEMP_PATH := $(TEMP_PATH)/cppunit

unzip:
	test -d $(TEMP_PATH) || mkdir -p $(TEMP_PATH)
	cd $(TEMP_PATH) \
	;tar zxpvf $(GZ_FILE)

show:
	cd $(TEMP_PATH) \
	;ls $(CPP_UNIT) \
	;cat $(CPP_UNIT)/Makefile.am \
	;cat $(CPP_UNIT)/configure.in \
	;cat $(CPP_UNIT)/Makefile
#	;cat $(CPP_UNIT)/INSTALL-unix \

build:
	cd $(TEMP_PATH)/$(CPP_UNIT) \
	;./configure --disable-shared \
	;make \
	;make check

install:
	cd $(TEMP_PATH)/$(CPP_UNIT) ; echo $(SU_PW) \
	|sudo -S make install


#.PHONY: install
