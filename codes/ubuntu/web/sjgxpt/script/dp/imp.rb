#!/usr/bin/env ruby
require 'optparse'
require './rb/db'

options = {}
OptionParser.new do |opt|
	opt.on('-d','--dryrun') { |o| options[:dryrun] = o }
end.parse!

if options[:dryrun]
	$dry_run.imp "192.168.21.243","oracle","oracle","oracle","gjzspt","gjzspt","./dmpfiles/gjzspt2016-12-23_nd.dmp"	
else
	$run.imp "192.168.21.243","oracle","oracle","oracle","gjzspt","gjzspt","./dmpfiles/gjzspt2016-12-23_nd.dmp"
end

