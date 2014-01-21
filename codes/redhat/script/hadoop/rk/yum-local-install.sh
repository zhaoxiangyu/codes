#!/bin/bash
set -x

test -d /media/cdrom || mkdir /media/cdrom
test -d /media/cdrom/CentOS || mount /dev/cdrom /media/cdrom

rpm --import /etc/pki/rpm-gpg/RPM-GPG-KEY-CentOS-5
yum --disablerepo=\* --enablerepo=c5-media install iptraf
yum --disablerepo=\* --enablerepo=c5-media install sysstat.i386 

#yum --disablerepo=\* --enablerepo=c5-media search sar|grep ' sar ' -C 3
#yum --disablerep=\* --enablerep=c5-media install xinetd.i386
