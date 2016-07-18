#!/usr/bin/env ruby

class Diagno

	def setup
		`ansible -i #{@inventory} all -m setup >setup.txt`
	end

	def ping
		puts `ansible -i #{@inventory} all -m ping`
	end

	def check_inet
		puts `ansible -i #{@inventory} all -m command -a 'ping -c 5 www.baidu.com'`
	end

	def check_hw
		puts `ansible-playbook -i #{@inventory} -t diagnose --extra-vars "target=all" site.yml`
	end

	def t_check_hw
		puts `ansible-playbook -i #{@inventory} -t diagnose --extra-vars "target=test" site.yml`
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
