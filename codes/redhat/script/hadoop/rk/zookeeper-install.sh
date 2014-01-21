#!/bin/bash
set -x

#SOURCE_FILE=/home/hadoop/i/downloads/cdh3/zookeeper-3.3.3-cdh3u1.tar.gz
#SOURCE_FILE=/home/hadoop/i/downloads/cdh4/zookeeper-3.4.3-cdh4.0.1.tar.gz
SOURCE_FILE=${1:?SOURCE_FILE must be set!}
BASE_NAME=$(basename $SOURCE_FILE)
BASE_NAME=${BASE_NAME%.tar.gz}

INSTALL_DIR=/home/hadoop
ZOOKEEPER_HOME=$INSTALL_DIR/$BASE_NAME

copy(){
  echo "tar xzf $SOURCE_FILE"
  if test ! -d $ZOOKEEPER_HOME; then
    cd $INSTALL_DIR
    tar xzf $SOURCE_FILE
  else
    echo $ZOOKEEPER_HOME already exists.
  fi
}

install(){
  rm $INSTALL_DIR/zookeeper
  ln -s $ZOOKEEPER_HOME $INSTALL_DIR/zookeeper
  chown -R hadoop:hadoop $ZOOKEEPER_HOME
  grep '^export ZOOKEEPER_HOME' /etc/profile
  if [ $? -eq 0 ]; then
    sed -i -e "/^export ZOOKEEPER_HOME=/cexport ZOOKEEPER_HOME=$ZOOKEEPER_HOME" /etc/profile
  else
    sed -i -e "\$aexport ZOOKEEPER_HOME=$ZOOKEEPER_HOME" /etc/profile
  fi
  #grep ZOOKEEPER_HOME /etc/profile || sed -i -e "\$aexport ZOOKEEPER_HOME=$ZOOKEEPER_HOME" /etc/profile
  #grep ZOOKEEPER_HOME/bin /etc/profile || sed -i -e '$aexport PATH=$ZOOKEEPER_HOME/bin:$PATH' /etc/profile
  #grep ZOO_LOG_DIR /etc/profile || sed -i -e '$aexport ZOO_LOG_DIR=$ZOOKEEPER_HOME/logs' /etc/profile
  #source /etc/profile
  #mkdir -p $ZOO_LOG_DIR
}

copy
install
