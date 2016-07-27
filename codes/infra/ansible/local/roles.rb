#!/usr/bin/env ruby -w

class Roles
	def self.install_mesos
		#puts `ansible-galaxy install andrewrothstein.mesosphere-zookeeper -p ./roles`
		#puts `ansible-galaxy install andrewrothstein.mesosphere-mesos -p ./roles`
		#puts `ansible-galaxy install andrewrothstein.mesosphere-mesosmaster -p ./roles`

		puts `ansible-galaxy install azavea.mesos -p ./roles`
	end
	def self.install_cloudstack
		puts `ansible-galaxy install devops.cloudstack-common -p ./roles`
		puts `ansible-galaxy install devops.cloudstack-agent -p ./roles`
		puts `ansible-galaxy install devops.cloudstack-management -p ./roles`
	end
end

