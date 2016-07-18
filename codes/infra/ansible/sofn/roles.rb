#!/usr/bin/env ruby -w

def ag_install
	puts """`ansible-galaxy install medzin.oracle-jdk -p ./roles; ansible-galaxy install abessifi.weblogic -p ./roles; ansible-galaxy install AerisCloud.zookeeper -p ./roles; ansible-galaxy install ellotheth.oracle -p ./roles;`"""
	puts """`ansible-galaxy install cchurch.memcached -p ./roles; ansible-galaxy install geerlingguy.nginx -p ./roles `"""
end

