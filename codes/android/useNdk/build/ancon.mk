#	; ./configure --host arm-linux-androideabi --target arm-linux-androideabi
# --host=arm-none-linux-gnueabi
# fix error: http://stackoverflow.com/questions/6759315/how-does-configure-file-find-the-correct-cross-compile-tools

ANCON_DIR=jni/ancon

help:
	@echo ANCON_DIR:$(ANCON_DIR)
	
build-shared:

build-static:
	@echo -----building static--------
	
clean:
	
PHONY += help
PHONY += build-shared build-static
