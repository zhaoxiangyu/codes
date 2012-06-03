ZINNIA_TOMOE_BZ2 := /media/sf_ubuntu/zinnia/zinnia-tomoe-0.6.0-20080911.tar.bz2
ZINNIA_TOMOE_BZ2_BASENAME := zinnia-tomoe-0.6.0-20080911
ZINNIA_TOMOE_DIR := test/zinnia-tomoe

ZINNIA_DIR:=jni/3rd/zinnia-0.06

clean:
	cd $(PRJ_LOC)/$(ZINNIA_DIR) \
	; $(MAKE) clean \
	; echo $(SU_PW) | sudo -S $(MAKE) uninstall
	
install-tomoe:
	cd $(PRJ_LOC)/$(ZINNIA_TOMOE_DIR) \
	;test -d zinnia-tomoe-0.6.0-20080911 || tar jxvf $(ZINNIA_TOMOE_BZ2)

.PHONY: build help check-result install-model check-model \
build-shared build-static build-4ubuntu gen-model-headerfile