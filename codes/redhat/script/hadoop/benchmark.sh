#!/bin/bash
set -x

#TARGET_DIR=../tmp
TARGET_DIR=/home/hadoop/data2/tmp

hdspeed(){
  test -d $1 || mkdir -p $1
  echo testing writing
  dd if=/dev/zero bs=1024 count=1000000 of=$1/1g.txt
  echo testing reading
  dd if=$1/1g.txt bs=64k|dd of=/dev/null
  echo testing reading\&writing
  dd if=$1/1g.txt bs=64k of=$1/1g.cp.txt
  rm $1/{1g.txt,1g.cp.txt}
}

#hdspeed ../tmp
hdspeed $TARGET_DIR
