#!/bin/bash
set -x

#for p in $*; do
#  echo $p
#done

cp /etc/profile profile
#sed -i -n '/export JAVA_HOME=/{=;p}' profile
sed -i '/export JAVA_HOME=/{p}' profile
#sed -i '/export JAVA_HOME=/axxx' profile
