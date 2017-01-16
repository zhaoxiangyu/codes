#!/usr/bin/env ruby

def login(ip,port,username,password,captcha)
	baseurl="http://#{ip}:#{port}/sofn-dgap-web"
	uuid=system "http --body get #{baseurl}/sso/getUUID|jq .uuid"
	token=system "http --form --body post #{baseurl}/sso/login account=#{username} code=#{captcha} password=#{password} uuid=#{uuid} | jq .token"
	token
end

def dw_tables(ip,port,tableName)
	baseurl="http://#{ip}:#{port}/sofn-dgap-web"
	system "http --form --body post #{baseurl}/resourcePublish/getTables tableName=#{tableName}"
end
