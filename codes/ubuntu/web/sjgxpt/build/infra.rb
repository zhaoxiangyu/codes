#!/usr/bin/ruby -w

class Shell

	def run(cmd)
		puts cmd
		system(cmd)
	end

end

class Nginx < Shell

	def stop
		run("sudo nginx -s stop")
	end

	def start
		run("sudo nginx -c /home/helong/he/lky/share/sjgxpt/dev/nginx.conf")
	end

	def reload
		run "sudo nginx -s reload"
	end

end

class Tomcat < Shell

	def initialize(home)
		@server_home = home
	end

end

$nginx = Nginx.new
$web_server = Tomcat.new("/home/helong/he/lky/share/sjgxpt/udev/infra/apache-tomcat-8.5.5-8080")
$service_server = Tomcat.new("/home/helong/he/lky/share/sjgxpt/udev/infra/apache-tomcat-8.5.5-9080")
