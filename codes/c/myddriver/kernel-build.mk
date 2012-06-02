SUDO_PASSWD:=123qwe
LINUX_SOURCE:=linux-source-2.6.35
BUILD_DIR:=/home/he/kernel-build
BUILD_OUTPUT:=$(BUILD_DIR)/out
BUILD_OUTPUT2:=$(BUILD_DIR)/out2
BUILD_OUTPUT3:=$(BUILD_DIR)/out3
BACKUP_DIR:=/home/he/kernel-backup
YMD:=$(shell date +%F)

#all:kernel-download
#all:kernel-clean
#all:kernel-config
#all:kernel-build2
all:kernel-build3
#all:kernel-build-info
#all:kernel-build

manuals:
	gedit /usr/src/$(LINUX_SOURCE)/README &

kernel-build2:
#	cd /usr/src/$(LINUX_SOURCE) \
#	; echo $(SUDO_PASSWD) | sudo -S make O=$(BUILD_OUTPUT) V=1 all
#	cd $(BUILD_OUTPUT); echo $(SUDO_PASSWD) \
#	| sudo -S make-kpkg
	test -d $(BUILD_OUTPUT2) || mkdir $(BUILD_OUTPUT2)
	cd $(BUILD_OUTPUT2); fakeroot make-kpkg \
	--initrd --revision usbfs.001 --append-to-version v-usbfs.001 \
	kernel_image
	test -f $(BUILD_OUTPUT2)/arch/x86/boot/bzImage \
	&& du -h $(BUILD_OUTPUT2)/arch/x86/boot/bzImage

kernel-build3:
	test -d $(BUILD_OUTPUT3) &&	echo $(SUDO_PASSWD) \
	| sudo -S rm -rf $(BUILD_OUTPUT3)
	mkdir $(BUILD_OUTPUT3)
	cd $(BUILD_OUTPUT3) ; cp $(BUILD_DIR)/config-2.6.35-22-he .config
	cd /usr/src/$(LINUX_SOURCE) \
	; echo $(SUDO_PASSWD) | sudo -S make O=$(BUILD_OUTPUT3) silentoldconfig
#	cd /usr/src/$(LINUX_SOURCE) \
#	; echo $(SUDO_PASSWD) | sudo -S make O=$(BUILD_OUTPUT3) V=1 all
	test -f $(BUILD_OUTPUT3)/arch/x86/boot/bzImage \
	&& du -h $(BUILD_OUTPUT3)/arch/x86/boot/bzImage

kernel-install-info:
	ls -l /boot
	ls -l /boot/grub | grep .lst
#	cat /boot/grub/menu.lst
	cat /boot/grub/grub.cfg | grep 2.6.35
#	dpkg -l|grep linux
#	man make-kpkg

kernel-install:kernel-backup kernel-install-info
	which make-kpkg || echo $(SUDO_PASSWD) \
	| sudo -S apt-get install kernel-package

kernel-backup:
	test -d $(BACKUP_DIR) || mkdir $(BACKUP_DIR)
	echo current date: $(YMD)
	test -f $(BACKUP_DIR)/vmlinuz-$(YMD) \
	|| cp /boot/vmlinuz-2.6.35-22-generic $(BACKUP_DIR)/vmlinuz-$(YMD)
	test -f $(BACKUP_DIR)/vmlinuz-$(YMD) \
	&& echo backuped to $(BACKUP_DIR)/vmlinuz-$(YMD)

kernel-build-info:
	@uname -r
	ls /boot/vmlinuz* /vmlinuz* #/boot/zImage /zImage
	ls -l /vmlinuz
#	ls /usr/src/$(LINUX_SOURCE)*
#	ls /usr/src/$(LINUX_SOURCE).tar.bz2 -l
	du -h /usr/src/$(LINUX_SOURCE)/$(LINUX_SOURCE).tar.bz2
	test ! -e $(BUILD_OUTPUT)/arch/x86/boot/bzImage || du -h $(BUILD_OUTPUT)/arch/x86/boot/bzImage
	du -h /boot/vmlinuz-2.6.35-22-generic
	test -f $(BUILD_OUTPUT)/.config && wc -l $(BUILD_OUTPUT)/.config
	ls -l /boot/config*
	cat "/boot/config-`uname -r`" | grep CONFIG_USB_DEVICEFS
#	grep CONFIG_USB_DEVICEFS /boot/config*
	grep CONFIG_USB_DEVICEFS $(BUILD_OUTPUT)/.config
#	cat /usr/src/$(LINUX_SOURCE)/README
	
#	echo $(SUDO_PASSWD) | sudo -S apt-file update
#	sudo -S apt-file list $(LINUX_SOURCE)

kernel-download:
	uname -r
	echo $(SUDO_PASSWD)|sudo -S apt-cache search LINUX_SOURCE
	#sudo apt-get source linux-$(shell uname -r)
	sudo apt-get install $(LINUX_SOURCE)
	test -f $(LINUX_SOURCE)/Makefile || cd /usr/src; echo $(SUDO_PASSWD) \
	|sudo -S tar jxvf $(LINUX_SOURCE).tar.bz2
	sudo apt-get -y install libgtk2.0-dev libglib2.0-dev libglade2-dev
	@echo kernel-setup complete
	
kernel-config:
#config xconfig config allnoconfig gconfig oldconfig localmodconfig silentoldconfig
	test -d $(BUILD_OUTPUT) || mkdir -p $(BUILD_OUTPUT)
	echo ---------build kernel out diretory: $(BUILD_OUTPUT)--------
	cd /usr/src/$(LINUX_SOURCE) \
	; echo $(SUDO_PASSWD) | sudo -S make O=$(BUILD_OUTPUT) silentoldconfig

kernel-build:
	cd /usr/src/$(LINUX_SOURCE) \
	; echo $(SUDO_PASSWD) | sudo -S make O=$(BUILD_OUTPUT) V=1 all
	test -f $(BUILD_OUTPUT)/arch/x86/boot/bzImage \
	&& du -h $(BUILD_OUTPUT)/arch/x86/boot/bzImage
	
kernel-clean:
	echo $(SUDO_PASSWD) | sudo -S rm -rf $(BUILD_OUTPUT)
	cd /usr/src/$(LINUX_SOURCE) \
	;sudo make mrproper