#!/usr/bin/env ruby

def setup(inventory)
	`ansible -u root -k -i #{inventory} all -m setup >setup.txt`
end

def ping
	puts `ansible -u root -k -i env/dev all -m ping`
end

def check_inet
	puts `ansible -u root -k -i env/dev all -m command -a 'ping -c 5 www.baidu.com'`
end

def check_hw
	puts `ansible-playbook -i env/local -t diagnose site.yml`
end

def interactive
	print "inventory:"
	env=gets()
	puts "inventory:#{env}"
	setup(env)
end
