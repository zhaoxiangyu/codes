usb-info:
	echo 123qwe | sudo -S lsusb
	tree -d /sys/bus/usb/devices
	ls /usr/src/linux-source-2.6.35/Documentation/usb | grep power
	#dmesg | grep usb2
	tree -if -d /sys/devices| grep pci.*usb.*power
	tree -f /sys/devices | grep pci.*usb1
	ls /proc/bus/usb/
	
usb-doc:
	ls /usr/src/linux-source-2.6.35/Documentation/usb
	
