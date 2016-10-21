#!/usr/bin/ruby

class WebTest

  def resource_list
    run("curl -v -X POST '#{@baseurl}/sofn-dgap-web/resource/list?start=1&length=10'")
  end

  def ws_error_list
    run("curl -v -X POST '#{@baseurl}/sofn-dgap-web/wsErrorStat/list?start=1&length=10'")
  end

  def res_dirs
    run("curl -v -X POST '#{@baseurl}/sofn-dgap-web/resourceDirectory/all'")
  end

  def open_front
    run("firefox 'http://localhost:8001/'")
  end

  def initialize(baseurl)
		@baseurl = baseurl
	end

  def run(command)
		puts command
		system command
	end
end

$ltest = WebTest.new("http://localhost:10080")
