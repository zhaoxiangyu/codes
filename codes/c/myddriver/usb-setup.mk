
add-user:
	echo 123qwe | sudo -S groupadd usbfs
	sudo adduser $(USER) usbfs
	
install-fstab:
	groupid=`cat /etc/group | grep usbfs | awk -F":" '{print $$3}'` \
	; echo $$groupid \
	; line=`echo none /proc/bus/usb usbfs devgid=$$groupid,devmode=664 0 0` \
	; echo $$line \
	; echo 123qwe | sudo -S sed -i "$$ a $${line}" /etc/fstab
	cat /etc/fstab
	
change-permission:
	sudo chmod 777 /proc/bus/usb
	sudo chown $(USER) /proc/bus/usb
	
diagnose:
	ls /proc/bus
	lsusb -t
	ls /proc/bus/usb
	
mount-usb:
#echo 123qwe | sudo mkdir /proc/bus/usb
	sudo -S mount -t usbfs usbfs /proc/bus/usb/
	
enable-ubuntu-usb:
	ls /etc/init.d | grep dev
	cat /etc/init.d/mountdevsubfs.sh
	
reboot:
	sudo reboot