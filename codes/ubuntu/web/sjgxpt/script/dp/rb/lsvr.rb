#!/usr/bin/env ruby

class Lsvr

	def initialize(options)
		@dry_run=options[:dryrun]
		@tomcat_homes="/home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5"
		@app_dirs="/home/helong/he/lky/share/sjgxpt/udev/codes/dgap"
		@tomcat_dirname="apache-tomcat-8.5.5"

		@sso_service={tomcat_home:@tomcat_homes+"/sso-21080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sso-service/target/sofn-sso-service"}
		@sso_web={tomcat_home:@tomcat_homes+"/sso-web-23080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sso-web/target/sofn-sso-web"}
		@sys_service={tomcat_home:@tomcat_homes+"/sys-22080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sys-service/target/sofn-sys-service"}
		@sys_web={tomcat_home:@tomcat_homes+"/sys-web-25080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sys-web/target/sofn-sys-web"}
		@dgap_service={tomcat_home:@tomcat_homes+"/service-9080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-service/target/sofn-dgap-service"}
		@dgap_web={tomcat_home:@tomcat_homes+"/web-10080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-web/target/sofn-dgap-web"}
		@dgap_pre={tomcat_home:@tomcat_homes+"/pre-11080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-pre/target/sofn-dgap-pre"}
		#@servers = [@dgap_service,@sso_service,@dgap_web,@dgap_pre]
		#@servers = [@dgap_service,@dgap_pre]
		@servers = []
		if options[:login]
			@servers << @sso_service
		end
		if options[:loginw]
			@servers << @sso_web
		end
		if options[:sys]
			@servers << @sys_service
		end
		if options[:sysw]
			@servers << @sys_web
		end
		if options[:service]
			@servers << @dgap_service
		end
		if options[:web]
			@servers << @dgap_web
		end
		if options[:pre]
			@servers << @dgap_pre
		end
	end

	def start_tomcat(tomcat_home)
		run "#{tomcat_home}/bin/startup.sh"
	end

	def kill_tomcat(tomcat_home)
		run "pkill -9 -f #{tomcat_home}"
	end

	def start()
		run "/home/helong/he/lky/share/sjgxpt/udev/sw/zookeeper-3.4.6/bin/zkServer.sh start"
		#run "/usr/bin/redis-server"
		@servers.each do |server|
			start_tomcat server[:tomcat_home]
		end
	end

	def kill()
		run "pkill -f zookeeper-3.4.6"
		#run "echo 123qwe!@# | sudo -S pkill -f redis-server"
		@servers.each do |server|
			kill_tomcat server[:tomcat_home]
		end
	end

	def deploy(tomcat_home:'',app_dir:'')
		run "rm -rf #{tomcat_home}/webapps/*"
		run "cp -a #{app_dir} #{tomcat_home}/webapps"
	end

	def publish
		@servers.each do |server|
			deploy tomcat_home:server[:tomcat_home], app_dir:server[:app_dir]
		end
	end

	def view_logs()
		if not run "tmux has-session -t server_logs"
			run "tmux new-session -s server_logs -d"
			@servers.each do |server|
				#run "tmux new-window -t server_logs tail -f #{server[:tomcat_home]}/[l]ogs/catalina.out #{server[:tomcat_home]}/[l]ogs/localhost_access_log.$(date +%F).txt #{server[:tomcat_home]}/[l]ogs/localhost.$(date +%F).log"
				run "tmux new-window -t server_logs multitail --mergeall #{server[:tomcat_home]}/[l]ogs/catalina.out #{server[:tomcat_home]}/[l]ogs/localhost_access_log.$(date +%F).txt #{server[:tomcat_home]}/[l]ogs/localhost.$(date +%F).log"
			end
		end
		run "tmux attach-session -t server_logs"
	end

	def run(command)
		puts command
		if not @dry_run
			system command
		end
	end
end
#$d_lsvr=Lsvr.new(true)
#$lsvr=Lsvr.new(false)
