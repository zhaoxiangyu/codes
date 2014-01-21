DIST_DIR=`pwd`/job-deps
test -d $DIST_DIR || mkdir $DIST_DIR
cd $HBASE_HOME
cp hbase*.jar $DIST_DIR
cd lib
cp *.jar $DIST_DIR
#cp hadoop-core*.jar zookeeper*.jar log4j*.jar $DIST_DIR
#cp commons-*.jar $DIST_DIR
cd $HBASE_HOME
cp conf/hbase-site.xml $DIST_DIR
