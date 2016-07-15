#!/usr/bin/env ruby -w

def setup
	`ansible -u root -k -i hosts all -m setup`
end

def ping
	puts `ansible -u root -k -i hosts all -m ping`
end
