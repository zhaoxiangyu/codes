#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
BKUP_DIR=$WRK_DIR/bak-conf
BAK_SUFFIX=ori

mv /etc/hosts /etc/hosts.$BAK_SUFFIX
cp $BKUP_DIR/hosts /etc/hosts
