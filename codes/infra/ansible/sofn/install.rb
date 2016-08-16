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

	def install_nexus
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_nexus -l code site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def install_nexus_service
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_nexus_service -l code site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def setup(limit)
		cmd = %Q/ansible-playbook -i #{@inventory} --tags="setup" -l #{limit} site.yml/
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

	def preview(tags,limit)
		cmd = %Q/ansible-playbook --list-hosts --list-tags --list-tasks -i #{@inventory} -l #{limit} site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def syntax(limit)
		cmd = %Q/ansible-playbook --syntax-check -i #{@inventory} site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def dryrun(tags,limit)
		cmd = %Q/ansible-playbook -C -i #{@inventory} -l #{limit} site.yml/
		puts cmd
		puts `#{cmd}`
	end

end
