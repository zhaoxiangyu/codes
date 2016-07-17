#!/usr/bin/env ruby -w

$vm_dir="/home/he/data/github/codes/codes/infra/vagrant/sofn"

def vagrant_global_status
	puts `vagrant global-status`
end

def vagrant_up
	puts `cd #{$vm_dir};vagrant up`
end

def vagrant_halt
	puts `cd #{$vm_dir};vagrant halt`
end
