SOURCE_FILE=${1:?SOURCE_FILE must be set!}
BASE_NAME=$(basename $SOURCE_FILE)
BASE_NAME=${BASE_NAME%.tar.gz}

INSTALL_DIR=/home/hadoop
SQOOP_HOME=$INSTALL_DIR/$BASE_NAME

copy(){
  echo "tar xzf $SOURCE_FILE"
  if test ! -d $SQOOP_HOME; then
    cd $INSTALL_DIR
    tar xzf $SOURCE_FILE
  else
    echo $SQOOP_HOME already exists.
  fi
}

install(){
  chown -R hadoop:hadoop $SQOOP_HOME

  grep '^export SQOOP_HOME=' /etc/profile
  if [ $? -eq 0 ]; then
    echo changing SQOOP_HOME in /etc/profile
    sed -i -e "/^export SQOOP_HOME=/cexport SQOOP_HOME=$SQOOP_HOME" /etc/profile
  else
    echo adding SQOOP_HOME in /etc/profile
    sed -i -e "\$aexport SQOOP_HOME=$SQOOP_HOME" /etc/profile
  fi

  grep '^export PATH=$SQOOP_HOME/bin:$PATH' /etc/profile
  if [ $? -eq 0 ]; then
    echo found SQOOP_HOME/bin in PATH
  else
    echo adding SQOOP_HOME/bin to PATH
    sed -i -e '$aexport PATH=$SQOOP_HOME/bin:$PATH' /etc/profile
  fi

  #source /etc/profile
}

copy
install
