#from fabric.api import local,cd,run
from fabric.api import *

env.hosts = ['192.168.21.245']
env.user = 'root'
env.password = 'sofn@123'

def deploy():
    #tomcat_dir = '/usr/local/tomcat_dgap_web/'
    tomcat_dir = '/usr/local/'
    with cd(tomcat_dir):
        run("ls -l")

def hi():
    print "hi"

def hiSome(name="world"):
    local("ls -l")
    print("hi %s!" % name)

