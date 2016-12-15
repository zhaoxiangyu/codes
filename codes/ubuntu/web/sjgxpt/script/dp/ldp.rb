#!/usr/bin/env ruby

class Ldp
	def initialize(env)
		@inventory = env
	end


	def run(command)
		puts command
		system command
	end
end
