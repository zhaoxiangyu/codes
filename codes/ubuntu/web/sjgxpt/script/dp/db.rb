#!/usr/bin/env ruby

class Db 
	def initialize(dry_run)
		@dry_run=dry_run
	end

	def exp(ip,db_password,schema,local_dir)
		dmpfile_name=schema+`date "+%F_%T")`+".dmp"
		localfile=local_dir+dmpfile_name

		#exp userid=system/Oe123qwe### owner=gjzspt file=gjzspt.dmp
		exp_command='"exp userid=system/'+db_password+" owner=#{schema} file=/home/oracle/#{dmpfile_name}"+'"'
		exp_command='bash -ic '+exp_command

		run "sshpass -p oracle ssh -t oracle@#{ip} #{exp_command}"
		cp_command="scp oracle@#{ip}:/home/oracle/#{dmpfile_name} #{localfile}"
		run "sshpass -p oracle #{cp_command}"
	end

	def imp()
	end

	def run(command)
		puts command
		if not @dry_run
			system command
		end
	end
end

$dry_run=Db.new(true)
$run=Db.new(false)
