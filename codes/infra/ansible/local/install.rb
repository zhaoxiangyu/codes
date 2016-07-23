#!/usr/bin/env ruby

class Install
	def initialize(env)
		@inventory = env
	end

	def install
		cmd = %Q/ansible-playbook -i #{@inventory} -t install site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def setup
		cmd = %Q/ansible-playbook -i #{@inventory} -t setup site.yml/
		puts cmd
		puts `#{cmd}`
	end

=begin
=end
end
