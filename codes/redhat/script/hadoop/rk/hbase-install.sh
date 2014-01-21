#SOURCE_FILE=/home/hadoop/i/downloads/cdh4/hbase-0.92.1-cdh4.0.1.tar.gz
SOURCE_FILE=${1:?SOURCE_FILE must be set!}
BASE_NAME=$(basename $SOURCE_FILE)
BASE_NAME=${BASE_NAME%.tar.gz}

INSTALL_DIR=/home/hadoop
HBASE_HOME=$INSTALL_DIR/$BASE_NAME

copy(){
  echo "tar xzf $SOURCE_FILE"
  if test ! -d $HBASE_HOME; then
    cd $INSTALL_DIR
    tar xzf $SOURCE_FILE
  else
    echo $HBASE_HOME already exists.
  fi
}

install(){
  rm $INSTALL_DIR/hbase
  ln -s $HBASE_HOME $INSTALL_DIR/hbase
  chown -R hadoop:hadoop $HBASE_HOME

  grep '^export HBASE_HOME=' /etc/profile
  if [ $? -eq 0 ]; then
    echo changing HBASE_HOME in /etc/profile
    sed -i -e "/^export HBASE_HOME=/cexport HBASE_HOME=$HBASE_HOME" /etc/profile
  else
    echo adding HBASE_HOME in /etc/profile
    sed -i -e "\$aexport HBASE_HOME=$HBASE_HOME" /etc/profile
  fi

  grep '^export PATH=$HBASE_HOME/bin:$PATH' /etc/profile
  if [ $? -eq 0 ]; then
    echo found HBASE_HOME/bin in PATH
  else
    echo adding HBASE_HOME/bin to PATH
    sed -i -e '$aexport PATH=$HBASE_HOME/bin:$PATH' /etc/profile
  fi

  source /etc/profile
}

copy
install
