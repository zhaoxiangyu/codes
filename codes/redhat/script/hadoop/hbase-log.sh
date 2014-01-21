#!/bin/bash
set -x

./hbase-err.sh || ./hbase-warn.sh
