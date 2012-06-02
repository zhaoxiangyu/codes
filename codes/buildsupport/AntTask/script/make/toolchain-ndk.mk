#MKF_DIR = $(dir $(lastword $(MAKEFILE_LIST)))

NDK_BZ2 := /media/sf_ubuntu/android/android-ndk-r7-linux-x86.tar.bz2
NDK_INSTALL_DIR := /home/he/sw/android
TOOLCHAIN_PLATFORM ?= android-5
TOOLCHAIN_ARCH ?= arm
TOOLCHAIN_INSTALL_DIR := /home/he/sw/android/ndk-r7-toolchain-$(TOOLCHAIN_PLATFORM)-$(TOOLCHAIN_ARCH)
TOOLCHAIN_SCRIPT_PATH := $(NDK_INSTALL_DIR)/android-ndk-r7/build/tools/make-standalone-toolchain.sh

export PATH:=$(TOOLCHAIN_INSTALL_DIR)/bin:$(PATH)
export CC:=arm-linux-androideabi-gcc
export CXX:=arm-linux-androideabi-g++
export LD:=arm-linux-androideabi-g++

NDK_ARMEABI_STDCXX_LIBDIR := $(NDK_INSTALL_DIR)/android-ndk-r7/sources/cxx-stl/gnu-libstdc++/libs/armeabi
LIB_PATH :=$(NDK_ARMEABI_STDCXX_LIBDIR)
 
help:
	$(TOOLCHAIN_SCRIPT_PATH) --help

install-ndk:
	cd $(NDK_INSTALL_DIR) ; tar jxvf $(NDK_BZ2)
	
install-toolchain:
	@echo creating toolchain
#	mkdir -p TOOLCHAIN_ARCH
	$(TOOLCHAIN_SCRIPT_PATH) \
	--platform=$(TOOLCHAIN_PLATFORM) \
	--arch=$(TOOLCHAIN_ARCH) \
	--install-dir=$(TOOLCHAIN_INSTALL_DIR)
	
.PHONY: help install-ndk install-toolchain
	