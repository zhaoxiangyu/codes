#!/bin/bash
set -x

source common.sh

func_install_path(){
  grep '^export PIG_INSTALL=' /etc/profile
  if [ $? -eq 0 ]; then
    echo changing PIG_INSTALL in /etc/profile
    sudo sed -i -e "/^export PIG_INSTALL=/cexport PIG_INSTALL=$INSTALL_HOME" /etc/profile
  else
    echo adding PIG_INSTALL in /etc/profile
    sudo sed -i -e "\$aexport PIG_INSTALL=$INSTALL_HOME" /etc/profile
  fi

  grep '^export PATH=$PIG_INSTALL/bin:$PATH' /etc/profile
  if [ $? -eq 0 ]; then
    echo found PIG_INSTALL/bin in PATH
  else
    echo adding PIG_INSTALL/bin to PATH
    sudo sed -i -e '$aexport PATH=$PIG_INSTALL/bin:$PATH' /etc/profile
  fi

  source /etc/profile
}

func_unzip downloads/pig-0.10.0.tar.gz
func_install_path
