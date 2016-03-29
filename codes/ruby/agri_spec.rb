#!/usr/bin/env ruby -w
require "json"
require "selenium-webdriver"
require "rspec"
require "./rrds"
include RSpec::Expectations

describe "agriculture web app" do

  before(:each) do
    puts "running before"
    @driver = Selenium::WebDriver.for :firefox
#    @base_url = "http://localhost:8021"
#    @base_url = "http://localhost"
    @base_url = "http://localhost:8080/agri-web"
    @accept_next_alert = true
    @driver.manage.timeouts.implicit_wait = 30
    @verification_errors = []
  end
  
  after(:each) do
    @driver.quit
    @verification_errors.should == []
  end
  
  it "show notice page data OK" ,:notice do
    puts "opening page c_login"
    @driver.get(@base_url + "/c_login")
    @driver.find_element(:name, "mobile").clear
    @driver.find_element(:name, "mobile").send_keys "13345678888"
    @driver.find_element(:name, "password").clear
    @driver.find_element(:name, "password").send_keys "a1b2c3d4"

    session = @driver.manage.cookie_named("USER_SESSION_agri-credit")
    puts "sessionid:#{session[:value]}"
    captcha = getSessionAttribute(session[:value],'login')
    puts "captcha code:#{captcha}"
    @driver.find_element(:name, "checkCode").click
    @driver.find_element(:name, "checkCode").clear
    @driver.find_element(:name, "checkCode").send_keys captcha
    @driver.find_element(:id, "submit_btn").click

    @driver.find_element(:css, "a.header_user > span > em")
  end

  it "nonghu feature OK" do
    puts "opening page login.html"
    @driver.get(@base_url + "/c_login")
    @driver.find_element(:name, "mobile").clear
    @driver.find_element(:name, "mobile").send_keys "18200376986"
    @driver.find_element(:name, "password").clear
    @driver.find_element(:name, "password").send_keys "12345678"
    @driver.find_element(:name, "captcha").click
    @driver.find_element(:name, "captcha").clear
    @driver.find_element(:name, "captcha").send_keys "5420"
    @driver.find_element(:id, "submit_btn").click
  end
  
  it "zhanyerenyuan feature OK" do
    puts "opening page login.html"
    @driver.get(@base_url + "/login")
    @driver.find_element(:name, "mobile").clear
    @driver.find_element(:name, "mobile").send_keys "18200376981"
    @driver.find_element(:name, "password").clear
    @driver.find_element(:name, "password").send_keys "12345678"
#    @driver.find_element(:name, "captcha").click
#    @driver.find_element(:name, "captcha").clear
#    @driver.find_element(:name, "captcha").send_keys "5420"
    @driver.find_element(:id, "submit_btn").click
  end

  it "qiye feature OK" do
    puts "opening page login.html"
    @driver.get(@base_url + "/login")
    @driver.find_element(:name, "mobile").clear
    @driver.find_element(:name, "mobile").send_keys "18200376980"
    @driver.find_element(:name, "password").clear
    @driver.find_element(:name, "password").send_keys "12345678"
    @driver.find_element(:id, "submit_btn").click
  end

  def verify(&blk)
    yield
  rescue ExpectationNotMetError => ex
    @verification_errors << ex
  end
  
=begin
  def element_present?(how, what)
    ${receiver}.find_element(how, what)
    true
  rescue Selenium::WebDriver::Error::NoSuchElementError
    false
  end
  
  def alert_present?()
    ${receiver}.switch_to.alert
    true
  rescue Selenium::WebDriver::Error::NoAlertPresentError
    false
  end
  
  def close_alert_and_get_its_text(how, what)
    alert = ${receiver}.switch_to().alert()
    alert_text = alert.text
    if (@accept_next_alert) then
      alert.accept()
    else
      alert.dismiss()
    end
    alert_text
  ensure
    @accept_next_alert = true
  end
=end
end
