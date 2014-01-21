#!/bin/bash
set -e
set -x

sudo rk/hbase-install.sh /home/hadoop/downloads/hbase-0.92.1.tar.gz
./hbase-instconf.sh
./hbase-redeploy.sh
