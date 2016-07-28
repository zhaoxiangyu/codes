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

	def setup
		cmd = %Q/ansible-playbook -i #{@inventory} -t setup site.yml/
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

	def t_install_oracle11g
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_oracledb -l 192.168.122.109 site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def t_upload_files
		cmd = %Q/ansible-playbook -i #{@inventory} -t copy_files -l 192.168.122.109 site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def t_autostart_sshd
		cmd = %Q/ansible-playbook -i #{@inventory} -t autostart_sshd -l 192.168.122.109 site.yml/
		puts cmd
		puts `#{cmd}`
	end
	
=begin
	def t_setup
		puts `ansible-playbook -i #{@inventory} -t setup --extra-vars "target=test" site.yml`
	end

	def setup
		puts `ansible-playbook -i #{@inventory} -t setup --extra-vars "target=all" site.yml`
	end

	def t_install_java8
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_java8 --extra-vars "target=test" site.yml/
		puts cmd
		puts `#{cmd}`
	end

	def install_java8
		cmd = %Q/ansible-playbook -i #{@inventory} -t install_java8 --extra-vars "target=java8" site.yml/
		puts cmd
		puts `#{cmd}`
	end
=end
end
