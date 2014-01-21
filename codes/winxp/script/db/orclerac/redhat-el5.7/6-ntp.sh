#!/bin/bash

/sbin/service ntpd stop
chkconfig ntpd off
rm /etc/ntp.conf
rm /var/run/ntpd.pid
