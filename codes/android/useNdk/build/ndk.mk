NDK_BZ2 := /media/sf_ubuntu/android/android-ndk-r7-linux-x86.tar.bz2
NDK_INSTALL_DIR := /home/he/sw/android
TOOLCHAIN_INSTALL_DIR := /home/he/sw/android/ndk-r7-toolchain
TOOLCHAIN_SCRIPT_PATH := $(NDK_INSTALL_DIR)/android-ndk-r7/build/tools/make-standalone-toolchain.sh

build-static-ancon:
	export PATH=$(TOOLCHAIN_INSTALL_DIR)/bin:$(PATH) \
	; export CC=arm-linux-androideabi-gcc \
	; echo PWD:`pwd` \
	; $(MAKE) -f build/ancon.mk build-static
	

help:
	$(TOOLCHAIN_SCRIPT_PATH) --help

install-ndk:
	cd $(NDK_INSTALL_DIR) ; tar jxvf $(NDK_BZ2)
	
install-toolchain:
	@echo creating toolchain
	$(TOOLCHAIN_SCRIPT_PATH) \
	--platform=android-8 \
	--arch=arm \
	--install-dir=$(TOOLCHAIN_INSTALL_DIR)

build-shared-zinnia:
	export PATH=$(TOOLCHAIN_INSTALL_DIR)/bin:$(PATH) \
	; export CC=arm-linux-androideabi-gcc \
	; echo PWD:`pwd` \
	; $(MAKE) -f build/zinnia.mk build-shared

build-static-zinnia:
	export PATH=$(TOOLCHAIN_INSTALL_DIR)/bin:$(PATH) \
	; export CC=arm-linux-androideabi-gcc \
	; echo PWD:`pwd` \
	; $(MAKE) -f build/zinnia.mk build-static

.PHONY: help install-ndk install-toolchain build-zinnia
	