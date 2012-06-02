ANTLR_DIR = /home/he/antlr
GZ_LOC = /media/sf_VMshare/java/tools/antlr/libantlr3c-3.4.tar.gz
EXAMPLE_GZ_LOC = /media/sf_VMshare/java/tools/antlr/examples-v3.tar.gz

CONFIGURE := $(shell find $(ANTLR_DIR) -name configure)
CONF_PREFIX := /home/he/antlr/build
CONF_NDK_PREFIX := /home/he/antlr/build-ndk

export ANTLR_LIB_DIR :=$(CONF_PREFIX)/lib
export ANTLR_INC_DIR :=$(CONF_PREFIX)/include

export ANTLR_NDK_LIB_DIR :=$(CONF_NDK_PREFIX)/lib
export ANTLR_NDK_INC_DIR :=$(CONF_NDK_PREFIX)/include

CONFIG_INSTALLDIR:= $(ANTLR_DIR)/libantlr3c-3.4


antlr-install:
	if test ! -d $(ANTLR_DIR) ; \
	then \
		mkdir -p $(ANTLR_DIR) \
		;cd $(ANTLR_DIR) \
		;tar --gzip -xf $(GZ_LOC); \
	fi
	
antlr-build:
	cd $(ANTLR_DIR)/libantlr3c-3.4 \
	;$(CONFIGURE) --prefix $(CONF_PREFIX) \
	;$(MAKE) \
	;$(MAKE) install
	
antlr-ndk-build:
	cd $(ANTLR_DIR)/libantlr3c-3.4 \
	;$(CONFIGURE) --host=arm-linux-androideabi --prefix $(CONF_NDK_PREFIX) \
	;$(MAKE) \
	;$(MAKE) install
	
antlr-install-examples:
	if test ! -d $(ANTLR_DIR)/examples-v3 ; \
	then \
		cd $(ANTLR_DIR) \
		;tar --gzip -xf $(EXAMPLE_GZ_LOC); \
	fi

antlr-info:
	$(CONFIGURE) --help
	
