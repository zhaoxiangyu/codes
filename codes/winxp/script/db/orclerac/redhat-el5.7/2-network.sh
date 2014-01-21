#!/bin/bash
grep '116.bj.cn' /etc/resolv.conf || sed -i -e '1isearch 116.bj.cn' /etc/resolv.conf
grep '10.9.113.117' /etc/resolv.conf || sed -i -e '2inameserver 10.9.113.117' /etc/resolv.conf
sed -i -e '$rhosts' /etc/hosts
