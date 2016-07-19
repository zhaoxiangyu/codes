#!/usr/bin/env ruby -w

class Vagrant

	@vm_dir="/home/he/data/github/codes/codes/infra/vagrant/sofn"

	def global_status
		puts `vagrant global-status`
	end

	def up
		puts `cd #{@vm_dir};vagrant up`
	end

	def halt
		puts `cd #{@vm_dir};vagrant halt`
	end
end
