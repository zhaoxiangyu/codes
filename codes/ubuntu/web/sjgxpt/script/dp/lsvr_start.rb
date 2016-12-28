#!/usr/bin/env ruby
require 'optparse'
require './rb/lsvr'

options = {}
OptionParser.new do |opt|
	opt.on('-d','--dryrun') { |o| options[:dryrun] = o }
	opt.on('-p','--pre') { |o| options[:pre] = o }
	opt.on('-w','--web') { |o| options[:web] = o }
	opt.on('-l','--sso') { |o| options[:login] = o }
	opt.on('-s','--service') { |o| options[:service] = o }
end.parse!

$lsvr = Lsvr.new options
$lsvr.start

