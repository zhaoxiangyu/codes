#!/bin/bash
set -x

host=${1:-localhost}
#echo srvr | nc $host 2181
echo stat | nc $host 2181
