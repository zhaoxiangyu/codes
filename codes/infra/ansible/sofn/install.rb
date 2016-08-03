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

	def upload_files
		cmd = %Q/ansible-playbook -i #{@inventory} -t copy_files site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def install_java8
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_java8 site.yml/
		puts cmd
		puts `#{cmd}`
	end
	
	def install_oracle11g
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_oracledb site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def setup(limit)
		cmd = %Q/ansible-playbook -i #{@inventory} --tags="setup" -l #{limit}" site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def upload_files(limit)
		cmd = %Q/ansible-playbook -i #{@inventory} -t copy_files -l #{limit} site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def install_oracle11g(limit)
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_oracledb -l #{limit} site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def post_install_oracle11g(limit)
		cmd = %Q/ansible-playbook -i #{@inventory} -t post_oracle_install -l #{limit} site.yml/
		puts cmd
		puts `#{cmd}`
	end

end
