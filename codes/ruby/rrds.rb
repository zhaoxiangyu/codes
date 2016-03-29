#!/usr/bin/env ruby -w
require 'redis'

#$host=ARGV[0] || "10.151.30.29"
$host="10.151.30.29"

def showKeys(host=$host)
    $stderr.puts "connecting to host:#{host}"
    redis = Redis.new(:host=>host,:port=>6379,:db=>1)

    keys = redis.keys('agri-credit:*:map:*')
    keys.each do |key|
        ttl = redis.ttl(key)
        puts "#{key}'s expire time:#{ttl}"
        value = redis.hgetall(key)
        puts "#{key}'s value:#{value}"
    end

    keys = redis.keys('agri-credit:*:att:*')
    keys.each do |key|
        ttl = redis.ttl(key)
        puts "#{key}'s expire time:#{ttl}"
        value = redis.get(key)
        puts "#{key}'s value:#{value}"
    end

    redis.quit
end

def getSessionAttribute(sessionId,key,host=$host)
    redis = Redis.new(:host=>host,:port=>6379,:db=>1)
    flkey="agri-credit:#{sessionId}:att:#{key}"
    puts "flkey:#{flkey}"
    value = redis.get(flkey)
    $stderr.puts "value:#{value}"
    redis.quit
    value
end

=begin
if STDIN.tty?
    showKeys
end
=end
