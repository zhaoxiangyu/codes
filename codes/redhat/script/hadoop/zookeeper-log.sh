#!/bin/bash
set -x

./zookeeper-error.sh || ./zookeeper-warn.sh
#./zookeeper-error.sh && ./zookeeper-warn.sh
