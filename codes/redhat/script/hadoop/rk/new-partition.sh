#!/bin/bash
set -x

NO_PARTITION_DISK=$(fdisk -l 2>&1|grep "doesn't contain a valid partition table"|awk '{print $2}')
HD=${NO_PARTITION_DISK:?disk without valid partitions DOES NOT exists!}

test $# -eq 0 && { echo "usage like: $0 /home/hadoop/data3"; exit 1; }

TARGET_DIR=${1:?target directory must set first!}

fdisk $HD<<EOI
n
p
1


p
w
EOI

mkfs -t ext3 ${HD}1
mkdir $TARGET_DIR
chown -R hadoop:hadoop $TARGET_DIR
mount ${HD}1 $TARGET_DIR
sed -i "\$a${HD}1\t\t$TARGET_DIR\text3\tdefaults\t0 0" /etc/fstab
