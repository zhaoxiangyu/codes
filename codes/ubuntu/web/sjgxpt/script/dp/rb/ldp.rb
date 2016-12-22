#!/usr/bin/env ruby

class Ldp

	def initialize(dry_run)
		@dry_run=dry_run
		@tomcat_homes="/home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5"
		@app_dirs="/home/helong/he/lky/share/sjgxpt/udev/codes/dgap"
		@tomcat_dirname="apache-tomcat-8.5.5"
		@dgap_service={tomcat_home:@tomcat_homes+"/service-9080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-service/target/sofn-dgap-service"}
		@sso_service={tomcat_home:@tomcat_homes+"/sso-21080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sso-service/target/sofn-sso-service"}
		@dgap_web={tomcat_home:@tomcat_homes+"/web-10080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-web/target/sofn-dgap-web"}
		@dgap_pre={tomcat_home:@tomcat_homes+"/pre-11080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-pre/target/sofn-dgap-pre"}
	end

	def deploy(tomcat_home:'',app_dir:'')
		#run "ps -ef | grep #{tomcat_home} | grep -v grep | awk '{print $2}' | xargs kill -9"
		run "pkill -f #{tomcat_home}"
		run "rm -rf #{tomcat_home}/webapps/*"
		run "cp -a #{app_dir} #{tomcat_home}/webapps"
		#run "#{tomcat_home}/bin/startup.sh"
	end

	def publish
		deploy @dgap_service
		deploy @sso_service
		deploy @dgap_web
		deploy @dgap_pre
	end

	def run(command)
		puts command
		if not @dry_run
			system command
		end
	end
end
$dry_ldp=Ldp.new(true)
$ldp=Ldp.new(false)