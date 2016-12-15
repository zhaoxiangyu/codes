pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
sshpass -p sofn@123 ssh root@192.168.21.248 bash -ic "ps -ef|grep tomcat_dgap_service|grep -v grep|awk '{print $2}'|xargs kill -9"
sshpass -p sofn@123 ssh root@192.168.21.248 bash -ic "rm -rf /usr/local/tomcat_test/tomcat_dgap_service/webapps/sofn-dgap-service"
sshpass -p sofn@123 scp -r sofn-dgap-service/target/sofn-dgap-service root@192.168.21.248:/usr/local/tomcat_test/tomcat_dgap_service/webapps/sofn-dgap-service
sshpass -p sofn@123 ssh root@192.168.21.248 /usr/local/tomcat_test/tomcat_dgap_service/bin/startup.sh
popd

pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
sshpass -p sofn@123 ssh root@192.168.21.246 bash -ic "ps -ef|grep tomcat_dgap_pre|grep -v grep|awk '{print $2}'|xargs kill -9"
sshpass -p sofn@123 ssh root@192.168.21.246 bash -ic "rm -rf /usr/local/tomcat_test/tomcat_dgap_pre/webapps/sofn-dgap-pre"
sshpass -p sofn@123 scp -r sofn-dgap-pre/target/sofn-dgap-pre root@192.168.21.246:/usr/local/tomcat_test/tomcat_dgap_pre/webapps/sofn-dgap-pre
sshpass -p sofn@123 ssh root@192.168.21.246 /usr/local/tomcat_test/tomcat_dgap_pre/bin/startup.sh
popd

pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
sshpass -p sofn@123 ssh root@192.168.21.247 bash -ic "ps -ef|grep tomcat_dgap_web|grep -v grep|awk '{print $2}'|xargs kill -9"
sshpass -p sofn@123 ssh root@192.168.21.247 bash -ic "rm -rf /usr/local/tomcat_test/tomcat_dgap_web/webapps/sofn-dgap-web"
sshpass -p sofn@123 scp -r sofn-dgap-web/target/sofn-dgap-web root@192.168.21.247:/usr/local/tomcat_test/tomcat_dgap_web/webapps/sofn-dgap-web
sshpass -p sofn@123 ssh root@192.168.21.247 /usr/local/tomcat_test/tomcat_dgap_web/bin/startup.sh
popd
