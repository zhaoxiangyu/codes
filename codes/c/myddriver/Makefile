obj-m:=hello.o

CURRENT_PATH:=$(shell pwd)
LINUX_KERNEL:=/usr/src/linux-headers-2.6.35-22-generic
SUDO_PASSWD:=123qwe

all:
	$(MAKE) -C $(LINUX_KERNEL) M=$(CURRENT_PATH) modules
	@echo all complete

test-driver:
	echo $(SUDO_PASSWD)|sudo -S insmod $(CURRENT_PATH)/hello.ko
	#modprobe
	sudo lsmod
	sudo rmmod hello
	tail /var/log/messages
	@echo test complete

backup-dev:
	echo $(SUDO_PASSWD)|sudo dpkg --get-selections | grep '[[:space:]]install'| awk '{print $1}' > installedpackages.txt
	cp /etc/apt/sources.list source.list

install-dev:
	cp source.list /etc/apt/sources.list
	echo $(SUDO_PASSWD)|cat installedpackages.txt | xargs sudo aptitude install
