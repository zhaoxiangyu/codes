pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
sshpass -p sofn@123 ssh root@192.168.21.248 "ps -ef|grep tomcat_dgap_service|grep -v grep|awk '{print \$2}'|xargs kill -9"
sshpass -p sofn@123 ssh root@192.168.21.248 "rm -rf /usr/local/tomcat_test/tomcat_dgap_service/webapps/sofn-dgap-service"
sshpass -p sofn@123 scp -r sofn-dgap-service/target/sofn-dgap-service root@192.168.21.248:/usr/local/tomcat_test/tomcat_dgap_service/webapps/sofn-dgap-service
sshpass -p sofn@123 ssh root@192.168.21.248 /usr/local/tomcat_test/tomcat_dgap_service/bin/startup.sh
popd
