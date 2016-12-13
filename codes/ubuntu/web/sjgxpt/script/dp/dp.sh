#!/bin/bash
#debug=echo
#set -ex
set -e
debug=$2

update_code(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	$debug svn update sofn-common sofn-generator sofn-sso-api sofn-sso-service sofn-sys-api common-files
	$debug svn update sofn-dgap-pre sofn-dgap-web sofn-dgap-api sofn-dgap-service
	$debug popd
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes
	$debug svn update soft-webfont
	$debug popd
}

rebuild(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	$debug mvn -U -P test clean install 
	$debug popd
}

deploy(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	#kill tomcat
	appname=$1
	ip=$2
	tomcat_dir=$3
	webapp_dir=$tomcat_dir/webapps/$appname
	kill_command='"ps -ef|grep '$(basename $tomcat_dir)'|grep -v grep|awk '"'"'{print \$2}'"'"'|xargs kill -9"'
	#echo $kill_command
	rm_app_command="\"rm -rf $webapp_dir\""

	#echo sshpass -p sofn@123 ssh root@$ip $kill_command
	$debug sshpass -p sofn@123 ssh root@$ip $kill_command
	$debug sshpass -p sofn@123 ssh root@$ip $rm_app_command
	#upload files
	$debug sshpass -p sofn@123 scp -r $appname/target/$appname root@$ip:$webapp_dir
	#start tomcat
	$debug sshpass -p sofn@123 ssh root@$ip $tomcat_dir/bin/startup.sh
	$debug popd
}

ur(){
	update_code
	rebuild
}

vl(){
	sshpass -p sofn@123 ssh root@192.168.21.247 "tail -f /usr/local/tomcat_test/tomcat_dgap_web/logs/catalina.out"
}

publish(){
	deploy sofn-dgap-service 192.168.21.248 /usr/local/tomcat_test/tomcat_dgap_service
	#deploy sofn-dgap-pre 192.168.21.246 /usr/local/tomcat_test/tomcat_dgap_pre
	#deploy sofn-dgap-web 192.168.21.247 /usr/local/tomcat_test/tomcat_dgap_web
}

publish_wf(){
	$debug sshpass -p sofn@123 ssh root@192.168.21.245 "rm -rf /usr/local/sofn-webfont"
	$debug sshpass -p sofn@123 scp -r /home/helong/he/lky/share/sjgxpt/udev/codes/soft-webfont root@192.168.21.245:/usr/local/sofn-webfont
}

$1
#deploy sofn-webfont 192.168.21.245
