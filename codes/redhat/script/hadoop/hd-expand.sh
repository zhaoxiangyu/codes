#!/bin/bash
set -x

GSIZE=${1:?usage $0 size in giga bytes!}
COUNT=$(($GSIZE*1000*1000))

dd if=/dev/zero bs=1024 count=$COUNT of=/home/hadoop/data/xxx.pading
dd if=/dev/zero bs=1024 count=$COUNT of=/home/hadoop/data2/xxx.pading
rm /home/hadoop/{data,data2}/xxx.pading
