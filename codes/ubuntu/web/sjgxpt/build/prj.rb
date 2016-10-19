#!/usr/bin/ruby -w
require './infra.rb'

class Prj < Shell
  attr :basedir
  attr :vcurl

  def checkout
  end

  def update
    run "svn checkout #{@vcurl}"
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
