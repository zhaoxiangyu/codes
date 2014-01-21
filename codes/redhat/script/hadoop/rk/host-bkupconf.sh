#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
BKUP_DIR=$WRK_DIR/bak-conf

test -d $BKUP_DIR || mkdir -p $BKUP_DIR
cp /etc/hosts $BKUP_DIR
cp /etc/profile $BKUP_DIR
sudo cp /etc/sudoers $BKUP_DIR
