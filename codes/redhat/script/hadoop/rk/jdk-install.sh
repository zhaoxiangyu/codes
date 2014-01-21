WRK_DIR=/home/hadoop/i

rpm -qa|grep gcj|xargs rpm -e --nodeps
chmod a+x $WRK_DIR/downloads/jdk-6u27-linux-i586-rpm.bin
$WRK_DIR/downloads/jdk-6u27-linux-i586-rpm.bin

#grep JAVA_HOME /etc/profile || echo -e '\nexport JAVA_HOME=/usr/local/jdk1.6.0_27' >>/etc/profile
#grep 'JAVA_HOME\/bin' /etc/profile || echo -e '\nexport PATH=$JAVA_HOME/bin:$JAVA_HOME/jre:$PATH' >>/etc/profile
#grep 'JAVA_HOME\/lib' /etc/profile || echo -e '\nexport CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar' >>/etc/profile

grep JAVA_HOME /etc/profile || sed -i '$aexport JAVA_HOME=/usr/local/jdk1.6.0_27' /etc/profile
grep 'JAVA_HOME\/bin' /etc/profile || sed -i '$aexport PATH=$JAVA_HOME/bin:$JAVA_HOME/jre:$PATH' /etc/profile
grep 'JAVA_HOME\/lib' /etc/profile || sed -i '$aexport CLASSPATH=$JAVA_HOME/lib:$JAVA_HOME/lib/tools.jar' /etc/profile
