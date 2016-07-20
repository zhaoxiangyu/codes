#!/usr/bin/env ruby -w

class Vagrant

	#@vm_dir="/home/he/data/github/codes/codes/infra/vagrant/sofn"

	def global_status
		puts `vagrant global-status`
	end

	def up
		puts `cd #{@vm_dir};vagrant up`
	end

	def halt
		puts `cd #{@vm_dir};vagrant halt`
	end

	def initialize
		@vm_dir="/home/he/data/github/codes/codes/infra/vagrant/sofn"
	end

	def to_s
		puts "vm_dir:#{@vm_dir}"
	end

    def initialize
        @vm_dir="/home/he/data/github/codes/codes/infra/vagrant/sofn"
    end
end
