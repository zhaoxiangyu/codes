#!/usr/bin/env ruby

class Diagno

	def setup
		`ansible -i #{@inventory} all -m setup >output/setup.txt`
	end

	def ping
		puts `ansible -i #{@inventory} all -m ping`
	end

	def check_inet
		puts `ansible -i #{@inventory} all -m command -a 'ping -c 5 www.baidu.com'`
	end

	def check_info
		puts `ansible-playbook -i #{@inventory} -t diagnose site.yml`
	end

	def initialize(env)
		@inventory = env
	end

	def interactive
		print "inventory:"
		env=gets()
		puts "inventory:#{env}"
		setup(env)
	end
end
