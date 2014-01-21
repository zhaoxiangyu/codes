#!/bin/bash
set -x

INSTALL_DIR=/home/hadoop

func_unzip(){
  SOURCE_FILE=${1:?install file not set!}

  BASE_NAME=$(basename $SOURCE_FILE)
  BASE_NAME=${BASE_NAME%.tar.gz}
  export INSTALL_HOME=$INSTALL_DIR/$BASE_NAME

  echo "tar xzf $SOURCE_FILE"
  if test ! -d $INSTALL_HOME; then
    cd $INSTALL_DIR
    tar xzf $SOURCE_FILE
  else
    echo $INSTALL_HOME already exists.
  fi
}
