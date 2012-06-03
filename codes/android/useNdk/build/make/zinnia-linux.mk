include toolchain-gcc.mk
include zinnia.mk
ZINNIA_LIB_INSTALL_DIR:=/usr/local/lib
ZINNIA_STATIC_LIB_INSTALL_DIR:=test/zinnia-static
ZINNIA_SHARED_LIB_INSTALL_DIR:=test/zinnia-shared

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
	
gen-model-headerfile:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME) \
	;export LD_LIBRARY_PATH=$(ZINNIA_LIB_INSTALL_DIR):$(LD_LIBRARY_PATH) \
	;echo $$LD_LIBRARY_PATH \
	;$(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert handwriting-ja.model.txt handwriting-ja.model \
	;$(PRJ_LOC)/jni/zinnia-0.06/.libs/zinnia_convert --header-name=hw_ja --make-header handwriting-ja.model.txt hw_ja.h
	ls -l $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME)/handwriting-ja.model
	ls -l $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR)/$(ZINNIA_TOMOE_BZ2_BASENAME)/hw_ja.h

check-tomoe:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR); find . -name handwriting-ja*
	
.PHONY: help check-result install-model check-model \
build-4ubuntu gen-model-headerfile