#!/bin/bash
set -x

chkconfig --list|grep iptables
service iptables status
