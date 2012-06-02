NDK_ROOT := /home/he/sw/android/android-ndk-r7

info:
	@echo PRJ_LOC:$(PRJ_LOC)
	@echo NDK_ROOT:$(NDK_ROOT)
	@echo MY_ANDROID_MK:$(MY_ANDROID_MK)
	$(NDK_ROOT)/ndk-gdb --help
	
ndk_build: info
	cd $(PRJ_LOC) ; $(NDK_ROOT)/ndk-build V=1 --debug=j
	
ndk_clean: info
	cd $(PRJ_LOC) ; $(NDK_ROOT)/ndk-build clean
	
ndk_gdb:
	@echo $(NDK_ROOT)/ndk-gdb --project=$(PRJ_LOC) --adb=${ADB_PATH} \
	--verbose --start

PHONY += info
PHONY += ndk_build