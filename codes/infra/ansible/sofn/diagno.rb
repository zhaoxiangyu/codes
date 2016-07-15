#!/usr/bin/env ruby -w

def setup
	`ansible -u root -k -i hosts all -m setup >setup.txt`
end

def ping
	puts `ansible -u root -k -i hosts all -m ping`
end

def check_inet
	puts `ansible -u root -k -i hosts all -m command -a 'ping -c 5 www.baidu.com'`
end
