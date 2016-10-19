#!/usr/bin/ruby -w
require_relative './infra.rb'

class Prj < Shell
  attr_accessor :basedir
  attr_accessor :vcurl

  def checkout
  end

  def update
    run "cd #{@basedir};svn checkout #{@vcurl}"
  end

  def commit
  end

  def build
  end

  def clean
  end

  def initialize(basedir,vcurl)
    @basedir = basedir
    @vcurl = vcurl
  end

end

svnbase = "https://192.168.21.251/svn/CodeRepository/GuoJiaZhuiSuPingTai/BusinessSystem/sofn-server"
codebase = "/home/helong/he/lky/share/sjgxpt/udev/codes/dgap"

$prj_web = Prj.new codebase+"/sofn-dgap-web" svnbase+"/sofn-dgap-web"
$prj_pre = Prj.new codebase+"/sofn-dgap-pre" svnbase+"/sofn-dgap-pre"
$prj_api = Prj.new codebase+"/sofn-dgap-api" svnbase+"/sofn-dgap-api"
$prj_service = Prj.new codebase+"/sofn-dgap-service" svnbase+"/sofn-dgap-service"
