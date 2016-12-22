#!/bin/bash
#debug=echo
#set -ex
set -e
debug=$2
#debug="tmux new-window "

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

lrebuild(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	$debug mvn -U -P local,nexus-repo,!dev clean install 
	$debug popd
}

deploy(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	#kill tomcat
	appname=$1
	ip=$2
	tomcat_dir=$3
	webapp_dir=$tomcat_dir/webapps/$appname

	kill_command='"ps -ef|grep '$(basename $tomcat_dir)'|grep -v grep|awk '"'"'{print $2}'"'"'|xargs kill -9"'
	kill_command='bash -ic '$kill_command

	#echo sshpass -p sofn@123 ssh root@$ip $kill_command
	$debug sshpass -p sofn@123 ssh root@$ip $kill_command
	rm_app_command="\"rm -rf $webapp_dir\""
	rm_app_command='bash -ic '$rm_app_command
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

ldeploy(){
	tomcat_home=$1
	app_dir=$2

	set +e
	$debug "ps -ef | grep $tomcat_home | grep -v grep | awk '{print \$2}' | xargs kill -9"
	$debug cp -a $app_dir $tomcat_home/webapps
	$debug $tomcat_home/bin/startup.sh
}

lpublish(){
	ldeploy /home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5/service-9080/apache-tomcat-8.5.5 /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-dgap-service/target/sofn-dgap-service
	ldeploy /home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5/sso-21080/apache-tomcat-8.5.5 /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-sso-service/target/sofn-sso-service
	ldeploy /home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5/web-10080/apache-tomcat-8.5.5 /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-dgap-web/target/sofn-dgap-web
	ldeploy /home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5/pre-11080//apache-tomcat-8.5.5 /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-dgap-pre/target/sofn-dgap-pre
}

vl(){
	#sshpass -p sofn@123 ssh root@192.168.21.247 "tail -f /usr/local/tomcat_test/tomcat_dgap_web/logs/catalina.out"
	#sshpass -p sofn@123 ssh root@192.168.21.246 "tail -f /usr/local/tomcat_test/tomcat_dgap_pre/logs/catalina.out"
	sshpass -p sofn@123 ssh root@192.168.21.248 "tail -f /usr/local/tomcat_test/tomcat_dgap_service/logs/catalina.out"
}

publish(){
	deploy sofn-dgap-service 192.168.21.248 /usr/local/tomcat_test/tomcat_dgap_service
	deploy sofn-dgap-pre 192.168.21.246 /usr/local/tomcat_test/tomcat_dgap_pre
	deploy sofn-dgap-web 192.168.21.247 /usr/local/tomcat_test/tomcat_dgap_web
}

publish_wf(){
	$debug sshpass -p sofn@123 ssh root@192.168.21.245 "rm -rf /usr/local/sofn-webfont"
	$debug sshpass -p sofn@123 scp -r /home/helong/he/lky/share/sjgxpt/udev/codes/soft-webfont root@192.168.21.245:/usr/local/sofn-webfont
}

$1
#deploy sofn-webfont 192.168.21.245
