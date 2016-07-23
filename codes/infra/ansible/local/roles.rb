#!/usr/bin/env ruby -w

class Roles
	def self.install_mesos
		#puts `ansible-galaxy install andrewrothstein.mesosphere-zookeeper -p ./roles`
		#puts `ansible-galaxy install andrewrothstein.mesosphere-mesos -p ./roles`
		#puts `ansible-galaxy install andrewrothstein.mesosphere-mesosmaster -p ./roles`

		puts `ansible-galaxy install azavea.mesos -p ./roles`
	end
end

