HIVE_INSTALL_NAME=hive-0.9.0
INSTALL_DIR=/home/hadoop
HIVE_HOME=$INSTALL_DIR/$HIVE_INSTALL_NAME
DOWN_DIR=/he/downloads

cd $HIVE_HOME
echo "tar xzf $DOWN_DIR/$HIVE_INSTALL_NAME.tar.gz"
test -d $HIVE_HOME || tar xzf $DOWN_DIR/$HIVE_INSTALL_NAME.tar.gz
echo "export HIVE_HOME=$HIVE_HOME">>/etc/profile
echo 'export PATH=$HIVE_HOME/bin:$PATH'>>/etc/profile
source /etc/profile
