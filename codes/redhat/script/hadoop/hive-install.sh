#!/bin/bash
set -x

source common.sh

func_install_path(){
  grep '^export HIVE_INSTALL=' /etc/profile
  if [ $? -eq 0 ]; then
    echo changing HIVE_INSTALL in /etc/profile
    sudo sed -i -e "/^export HIVE_INSTALL=/cexport HIVE_INSTALL=$INSTALL_HOME" /etc/profile
  else
    echo adding HIVE_INSTALL in /etc/profile
    sudo sed -i -e "\$aexport HIVE_INSTALL=$INSTALL_HOME" /etc/profile
  fi

  grep '^export PATH=$HIVE_INSTALL/bin:$PATH' /etc/profile
  if [ $? -eq 0 ]; then
    echo found HIVE_INSTALL/bin in PATH
  else
    echo adding HIVE_INSTALL/bin to PATH
    sudo sed -i -e '$aexport PATH=$HIVE_INSTALL/bin:$PATH' /etc/profile
  fi

  source /etc/profile
}

func_unzip downloads/hive-0.9.0.tar.gz
func_install_path
