#!/bin/bash

IF_FILE=/etc/sysconfig/network-scripts/ifcfg-eth0

grep '^BOOTPROTO' $IF_FILE
if [ $? -eq 0 ];then
  sed -i '/^BOOTPROTO/cBOOTPROTO="dhcp"' $IF_FILE
else
  sed -i '$aBOOTPROTO="dhcp"' $IF_FILE
fi

grep '^ONBOOT' $IF_FILE
if [ $? -eq 0 ];then
  sed -i '/^ONBOOT/cONBOOT="yes"' $IF_FILE
else
  sed -i '$aONBOOT="yes"' $IF_FILE
fi
