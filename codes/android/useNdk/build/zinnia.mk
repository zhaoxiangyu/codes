#	; ./configure --host arm-linux-androideabi --target arm-linux-androideabi
# --host=arm-none-linux-gnueabi
# fix error: http://stackoverflow.com/questions/6759315/how-does-configure-file-find-the-correct-cross-compile-tools
ZINNIA_TOMOE_BZ2 := /media/sf_ubuntu/zinnia/zinnia-tomoe-0.6.0-20080911.tar.bz2
ZINNIA_TOMOE_BZ2_BASENAME := zinnia-tomoe-0.6.0-20080911
ZINNIA_TOMOE_DIR := test/zinnia-tomoe
ZINNIA_LIB_INSTALL_DIR:=/usr/local/lib
ZINNIA_STATIC_LIB_INSTALL_DIR:=test/zinnia-static
ZINNIA_SHARED_LIB_INSTALL_DIR:=test/zinnia-shared

build-shared:
	cd $(PRJ_LOC)/jni/zinnia-0.06 \
	; echo $(SU_PW) | sudo -S chmod 766 $(PRJ_LOC)/jni/zinnia-0.06 \
	; chmod 755 $(PRJ_LOC)/jni/zinnia-0.06/configure \
	; ./configure --host=arm-linux-androideabi --prefix $(PRJ_LOC)/$(ZINNIA_SHARED_LIB_INSTALL_DIR) \
	; $(MAKE) \
	; $(MAKE) install
	ls -l $(PRJ_LOC)/$(ZINNIA_SHARED_LIB_INSTALL_DIR)/bin/zinnia_convert


build-static:
	cd $(PRJ_LOC)/jni/zinnia-0.06 \
	; echo $(SU_PW) | sudo -S chmod 766 $(PRJ_LOC)/jni/zinnia-0.06 \
	; chmod 755 $(PRJ_LOC)/jni/zinnia-0.06/configure \
	; ./configure --host=arm-linux-androideabi --enable-static --disable-shared --prefix $(PRJ_LOC)/$(ZINNIA_STATIC_LIB_INSTALL_DIR) \
	; $(MAKE) LDFLAGS=-all-static \
	; echo $(SU_PW) | sudo -S -E bash ../../build/temp.sh \
	; $(MAKE) install
	ls -l $(PRJ_LOC)/$(ZINNIA_STATIC_LIB_INSTALL_DIR)/bin/zinnia_convert
	
build-4ubuntu:
	cd $(PRJ_LOC)/jni/zinnia-0.06 \
	; echo $(SU_PW) | sudo -S chmod 766 $(PRJ_LOC)/jni/zinnia-0.06 \
	; chmod 755 $(PRJ_LOC)/jni/zinnia-0.06/configure \
	; ./configure\
	; $(MAKE) \
	; sudo $(MAKE) install
	ldd $(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert
	
clean:
	cd $(PRJ_LOC)/jni/zinnia-0.06 \
	; $(MAKE) clean \
	; echo $(SU_PW) | sudo -S $(MAKE) uninstall
	
check-result:
	cd $(PRJ_LOC)/jni/zinnia-0.06 \
	;ls -l .libs
	cd /usr/local/lib \
	;ls -l | grep zinnia
	file -L $(PRJ_LOC)/jni/zinnia-0.06/.libs/libzinnia.so
	file -L /usr/local/lib/libzinnia.so
	file -L $(PRJ_LOC)/test/libhello-jni.so
	ldd $(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert
	
help:
	export LD_LIBRARY_PATH=$(ZINNIA_LIB_INSTALL_DIR):$(LD_LIBRARY_PATH) \
	;echo $$LD_LIBRARY_PATH \
	;$(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert --help \
	
install-model:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR) \
	;test -d zinnia-tomoe-0.6.0-20080911 || tar jxvf $(ZINNIA_TOMOE_BZ2)

gen-model-headerfile:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME) \
	;export LD_LIBRARY_PATH=$(ZINNIA_LIB_INSTALL_DIR):$(LD_LIBRARY_PATH) \
	;echo $$LD_LIBRARY_PATH \
	;$(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert handwriting-ja.model.txt handwriting-ja.model \
	;$(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert --header-name=hw_ja --make-header handwriting-ja.model.txt hw_ja.h
	ls -l $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME)/handwriting-ja.model
	ls -l $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME)/hw_ja.h

check-model:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR); find . -name handwriting-ja*
	
.PHONY: build help check-result install-model check-model \
build-shared build-static build-4ubuntu gen-model-headerfile