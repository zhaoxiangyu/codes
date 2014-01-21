SOURCE_FILE=${1:?SOURCE_FILE must be set!}
BASE_NAME=$(basename $SOURCE_FILE)
BASE_NAME=${BASE_NAME%.tar.gz}

INSTALL_DIR=/home/hadoop
HADOOP_HOME=$INSTALL_DIR/$BASE_NAME

copy(){
  echo "tar xzf $SOURCE_FILE"
  if test ! -d $HADOOP_HOME; then
    cd $INSTALL_DIR
    tar xzf $SOURCE_FILE
  else
    echo $HADOOP_HOME already exists.
  fi
}

install(){
  rm $INSTALL_DIR/hadoop
  ln -s $HADOOP_HOME $INSTALL_DIR/hadoop
  chown -R hadoop:hadoop $HADOOP_HOME

  grep '^export HADOOP_HOME=' /etc/profile
  if [ $? -eq 0 ]; then
    echo changing HADOOP_HOME in /etc/profile
    sed -i -e "/^export HADOOP_HOME=/cexport HADOOP_HOME=$HADOOP_HOME" /etc/profile
  else
    echo adding HADOOP_HOME in /etc/profile
    sed -i -e "\$aexport HADOOP_HOME=$HADOOP_HOME" /etc/profile
  fi

  grep '^export PATH=$HADOOP_HOME/bin:$PATH' /etc/profile
  if [ $? -eq 0 ]; then
    echo found HADOOP_HOME/bin in PATH
  else
    echo adding HADOOP_HOME/bin to PATH
    sed -i -e '$aexport PATH=$HADOOP_HOME/bin:$PATH' /etc/profile
  fi

}

copy
install
