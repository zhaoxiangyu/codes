#!/usr/bin/env bash

#web_ip=192.168.21.247
#tomcat_dir="/usr/local/tomcat_test/tomcat_dgap_web"

web_ip=10.0.51.14
#tomcat_dir="/usr/local/tomcat_dgap_web"
tomcat_dir="/usr/local"

#sshpass -p sofn@123 ssh root@192.168.21.247 'tail -f /usr/local/tomcat_test/tomcat_dgap_web/logs/catalina.out /usr/local/tomcat_test/tomcat_dgap_web/logs/*2017-01-19.log'

#multitail -l "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/logs/catalina.out" -l "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/logs/*$(date +%F).log"

#multitail -l "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_pre/logs/catalina.out" -L "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_pre/logs/*$(date +%F).log"

multitail -l "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_web/logs/catalina.out" -L "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_web/logs/*$(date +%F).log" -l "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_service/logs/catalina.out" -L "sshpass -p sofn@123 ssh root@$web_ip tail -f $tomcat_dir/tomcat_dgap_service/logs/*$(date +%F).log"
