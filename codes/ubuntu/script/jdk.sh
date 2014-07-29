install7() {
  pushd ~/work/down
  INSTALL_FILE=jdk-7u65-linux-i586.tar.gz
  test -f $INSTALL_FILE || wget -O $INSTALL_FILE http://download.oracle.com/otn-pub/java/jdk/7u65-b17/$INSTALL_FILE?AuthParam=1405761355_8a023d8315e4583ea29c749115d5531b
  test -d /usr/lib/jvm || echo 123qwe | sudo -S mkdir /usr/lib/jvm
  test -f /usr/lib/jvm/$INSTALL_FILE || echo 123qwe | sudo -S cp -r ~/work/down/$INSTALL_FILE /usr/lib/jvm
  cd /usr/lib/jvm
  echo 123qwe | sudo -S tar -zxvf $INSTALL_FILE

  echo 123qwe | sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk1.7.0_65/bin/java 300
  echo 123qwe | sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk1.7.0_65/bin/javac 300
  echo 123qwe | sudo update-alternatives --set java /usr/lib/jvm/jdk1.7.0_65/bin/java
  echo 123qwe | sudo update-alternatives --set javac /usr/lib/jvm/jdk1.7.0_65/bin/javac
  popd
}

install7
