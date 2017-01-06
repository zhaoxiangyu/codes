#!/usr/bin/env ruby

class Lsvr

	def initialize(options)
		@dry_run=options[:dryrun]
		@tomcat_homes="/home/helong/he/lky/share/sjgxpt/udev/sw/tomcat-8.5.5"
		@app_dirs="/home/helong/he/lky/share/sjgxpt/udev/codes/dgap"
		@tomcat_dirname="apache-tomcat-8.5.5"

		@dgap_service={tomcat_home:@tomcat_homes+"/service-9080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-service/target/sofn-dgap-service"}
		@sso_service={tomcat_home:@tomcat_homes+"/sso-21080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sso-service/target/sofn-sso-service"}
		@sys_service={tomcat_home:@tomcat_homes+"/sys-22080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-sys-service/target/sofn-sys-service"}
		@dgap_web={tomcat_home:@tomcat_homes+"/web-10080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-web/target/sofn-dgap-web"}
		@dgap_pre={tomcat_home:@tomcat_homes+"/pre-11080/"+@tomcat_dirname,app_dir:@app_dirs+"/sofn-dgap-pre/target/sofn-dgap-pre"}
		#@servers = [@dgap_service,@sso_service,@dgap_web,@dgap_pre]
		#@servers = [@dgap_service,@dgap_pre]
		@servers = []
		if options[:pre]
			@servers << @dgap_pre
		end
		if options[:login]
			@servers << @sso_service
		end
		if options[:web]
			@servers << @dgap_web
		end
		if options[:service]
			@servers << @dgap_service
		end
		if options[:sys]
			@servers << @sys_service
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
		#start_tomcat @sso_service[:tomcat_home]
		#start_tomcat @dgap_service[:tomcat_home]
		#start_tomcat @dgap_web[:tomcat_home]
		#start_tomcat @dgap_pre[:tomcat_home]
		@servers.each do |server|
			start_tomcat server[:tomcat_home]
		end
	end

	def kill()
		run "pkill -f zookeeper-3.4.6"
		#kill_tomcat @sso_service[:tomcat_home]
		#kill_tomcat @dgap_service[:tomcat_home]
		#kill_tomcat @dgap_web[:tomcat_home]
		#kill_tomcat @dgap_pre[:tomcat_home]
		@servers.each do |server|
			kill_tomcat server[:tomcat_home]
		end
	end

	def view_logs()
		if not run "tmux has-session -t server_logs"
			run "tmux new-session -s server_logs -d"
			#run "tmux new-window -t server_logs -n sso tail -f #{@sso_service[:tomcat_home]}/logs/{catalina.out,localhost_access_log.$(date +%F).txt,localhost.$(date +%F).log}"
			#run "tmux new-window -t server_logs -n service tail -f #{@dgap_service[:tomcat_home]}/logs/{catalina.out,localhost_access_log.$(date +%F).txt,localhost.$(date +%F).log}"
			#run "tmux new-window -t server_logs -n web tail -f #{@dgap_web[:tomcat_home]}/logs/{catalina.out,localhost_access_log.$(date +%F).txt,localhost.$(date +%F).log}"
			#run "tmux new-window -t server_logs -n pre tail -f #{@dgap_pre[:tomcat_home]}/logs/{catalina.out,localhost_access_log.$(date +%F).txt,localhost.$(date +%F).log}"
			@servers.each do |server|
				run "tmux new-window -t server_logs tail -f #{server[:tomcat_home]}/[l]ogs/catalina.out #{server[:tomcat_home]}/[l]ogs/localhost_access_log.$(date +%F).txt #{server[:tomcat_home]}/[l]ogs/localhost.$(date +%F).log"
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
