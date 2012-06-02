CONFIGTOOLS_DIR:=/media/sf_VMshare/ubuntu/tools/autoconfig
ifndef CONFIG_INSTALLDIR
  $(warning CONFIG_INSTALLDIR not set)
endif

PRJ_DIR ?= 
SRC_DIR ?= $(shell pwd)
BUILD_DIR := automake
AUTOMAKE_DIR := automake
AUTOCONF_OPTS := -i --verbose
VERBOSE := --verbose
#FORCE := --force

all: autoreconf configure

autotools-install:
	sudo apt-get install libtool

configure-upgrade:
	cp $(CONFIGTOOLS_DIR)/config.sub $(CONFIGTOOLS_DIR)/config.guess $(CONFIG_INSTALLDIR)
		
info:
#	autoscan --version
#	automake --version
#	ifnames --version
#	autoreconf --version
#	autoheaders --version
	cd $(PRJ_DIR)/$(BUILD_DIR);./configure --help
	
autoscan:
	cd $(PRJ_DIR)/$(BUILD_DIR);autoscan $(SRC_DIR)
	
autoconf:
	cd $(PRJ_DIR);rm $(BUILD_DIR)/configure\
	;autoconf $(AUTOCONF_OPTS) -o $(BUILD_DIR)/configure $(AUTOMAKE_DIR)/configure.ac
	
autoreconf:
	cd $(PRJ_DIR)/;autoreconf $(VERBOSE) $(FORCE) --install $(AUTOMAKE_DIR)
	
configure:
	cd $(PRJ_DIR)/$(BUILD_DIR) \
	;./configure $(CONFIG_HOST) $(CONFIG_PREFIX) \
	;$(MAKE) \
	;$(MAKE) install
	
clean:
	cd $(PRJ_DIR)/$(BUILD_DIR);$(MAKE) clean;$(MAKE) distclean
	