#!/usr/bin/ruby -w

class Prj
  attr :basedir
  attr :vcurl

  def checkout
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
