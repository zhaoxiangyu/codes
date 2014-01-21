#!/bin/bash

groupadd -g 1000 oinstall
groupadd -g 1031 dba
useradd -u 1101 -g oinstall -G dba oracle
mkdir -p /u01/app/11.2.0/grid
mkdir -p /u01/app/oracle
chown -R oracle:oinstall /u01
chmod -R 775 /u01/
sed -i -e '$aexport LANG=zh_CN.GB18030' /home/oracle/.bash_profile
