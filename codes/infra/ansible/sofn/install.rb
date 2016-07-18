#!/usr/bin/env ruby

class Install
	def initialize(env)
		@inventory = env
	end

	def install
		puts `ansible-playbook -i #{@inventory} -t install site.yml`
	end

	def t_setup
		puts `ansible-playbook -i #{@inventory} -t setup --extra-vars "target=test" site.yml`
	end

	def setup
		puts `ansible-playbook -i #{@inventory} -t setup --extra-vars "target=all" site.yml`
	end

end
