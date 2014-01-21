#!/bin/bash
set -x

HIVE_CONF_DIR=$HIVE_INSTALL/conf

grep -C1 hive.metastore.local $HIVE_CONF_DIR/hive-site.xml
