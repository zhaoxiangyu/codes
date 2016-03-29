#!/usr/bin/env ruby
require 'zk'

host=ARGV[0] || "172.21.0.74:2181"
$stderr.puts "connecting to host #{host}"
zk = ZK.new(host)
ZK::Find.find(zk, '/') do |path|
    puts path+":"+(zk.get(path).first||"")
end
zk.close
