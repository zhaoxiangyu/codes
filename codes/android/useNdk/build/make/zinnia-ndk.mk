#	; ./configure --host arm-linux-androideabi --target arm-linux-androideabi
# --host=arm-none-linux-gnueabi
# fix error: http://stackoverflow.com/questions/6759315/how-does-configure-file-find-the-correct-cross-compile-tools
include toolchain-ndk.mk
include zinnia.mk
#ZINNIA_LIB_INSTALL_DIR:=/usr/local/lib
ZINNIA_STATIC_LIB_INSTALL_DIR:=test/zinnia-static
ZINNIA_SHARED_LIB_INSTALL_DIR:=test/zinnia-shared

build-shared:
	cd $(PRJ_LOC)/$(ZINNIA_DIR) \
	; echo $(SU_PW) | sudo -S chmod 766 $(PRJ_LOC)/$(ZINNIA_DIR) \
	; chmod 755 $(PRJ_LOC)/$(ZINNIA_DIR)/configure \
	; ./configure --host=arm-linux-androideabi --prefix $(PRJ_LOC)/$(ZINNIA_SHARED_LIB_INSTALL_DIR) \
	; $(MAKE) \
	; $(MAKE) install
	ls -l $(PRJ_LOC)/$(ZINNIA_SHARED_LIB_INSTALL_DIR)/bin/zinnia_convert


build-static:
	cd $(PRJ_LOC)/$(ZINNIA_DIR) \
	; echo $(SU_PW) | sudo -S chmod 766 $(PRJ_LOC)/$(ZINNIA_DIR) \
	; chmod 755 $(PRJ_LOC)/$(ZINNIA_DIR)/configure \
	; ./configure --host=arm-linux-androideabi --enable-static --disable-shared --prefix $(PRJ_LOC)/$(ZINNIA_STATIC_LIB_INSTALL_DIR) \
	; $(MAKE) LDFLAGS=-all-static \
	; echo $(SU_PW) | sudo -S -E bash ../../build/temp.sh \
	; $(MAKE) install
	ls -l $(PRJ_LOC)/$(ZINNIA_STATIC_LIB_INSTALL_DIR)/bin/zinnia_convert
	
clean:
	cd $(PRJ_LOC)/$(ZINNIA_DIR) \
	; $(MAKE) clean \
	; echo $(SU_PW) | sudo -S $(MAKE) uninstall
	
check-result:
	cd $(PRJ_LOC)/$(ZINNIA_DIR) \
	;ls -l .libs
	cd /usr/local/lib \
	;ls -l | grep zinnia
	file -L $(PRJ_LOC)/$(ZINNIA_DIR)/.libs/libzinnia.so
	file -L /usr/local/lib/libzinnia.so
	file -L $(PRJ_LOC)/test/libhello-jni.so
	ldd $(PRJ_LOC)/$(ZINNIA_DIR)/.libs/zinnia_convert
	
.PHONY: check-result build-shared build-static