#!/bin/bash
set -x

host=${1:-localhost}
#echo cons | nc $host 2181
#echo envi | nc $host 2181
#echo isro | nc $host 2181
#echo dump | nc $host 2181
#echo conf | nc $host 2181
#echo srvr | nc $host 2181
echo ruok | nc $host 2181
echo stat | nc $host 2181
#fuser -n tcp 2181
jps | grep HQuorumPeer
