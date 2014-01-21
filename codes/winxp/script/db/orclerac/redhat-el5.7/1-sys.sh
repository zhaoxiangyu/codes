#!/bin/bash

dd if=/dev/zero of=/swapfile bs=1024 count=7340032
mkswap /swapfile
swapon /swapfile
sed -i -e '$a/swapfile		swap	swap	defaults	0 0' /etc/fstab
#sed -i -e '$aexport LANG=zh_CN.GB18030' ~/.bash_profile
#cat /proc/swaps #after reboot
