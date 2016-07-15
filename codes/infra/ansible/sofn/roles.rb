#!/usr/bin/env ruby -w

def install
	puts """`sudo ansible-galaxy install medzin.oracle-jdk
	sudo ansible-galaxy install abessifi.weblogic
	sudo ansible-galaxy install AerisCloud.zookeeper
	sudo ansible-galaxy install ellotheth.oracle`"""
end

def init
	puts """ init roles """
end
