#!/bin/bash

cd /work/oracle-2.6.18
rpm -Uvh oracleasm-2.6.18-274.el5-2.0.5-1.el5.x86_64.rpm oracleasmlib-2.0.4-1.el5.x86_64.rpm oracleasm-support-2.1.7-1.el5.x86_64.rpm
/usr/sbin/oracleasm configure -i <<EOI
oracle
dba
y
y
EOI
/usr/sbin/oracleasm init
/etc/init.d/oracleasm scandisks
/etc/init.d/oracleasm listdisks
#sed -i -e '/ORACLEASM_SCANORDER=""/cORACLEASM_SCANORDER="mpath"' /etc/sysconfig/oracleasm
sed -i -e '/ORACLEASM_SCANORDER=""/cORACLEASM_SCANORDER="dm"' /etc/sysconfig/oracleasm
sed -i -e '/ORACLEASM_SCANEXCLUDE=""/cORACLEASM_SCANEXCLUDE="sda"' /etc/sysconfig/oracleasm
