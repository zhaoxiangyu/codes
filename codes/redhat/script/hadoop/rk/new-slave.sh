#!/bin/bash
set -x

if [ $# -eq 0 ];then
  echo "usage:$0 hostname ipaddres"
  exit 1
fi
HOST=${1:?hostname must set!}
IP=${2:?ip address must set!}


