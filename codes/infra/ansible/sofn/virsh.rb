#!/usr/bin/env ruby -w

class Virsh

	def global_status
		puts `virsh list`
	end

	def up
	end

	def halt
	end

	def to_s
		puts "vm_name:#{@vm_name}"
	end

    def initialize
        @vm_name="centos6.4x"
    end
end
