#!/usr/bin/env python
import sys,pexpect

user = "guohai.tang"
host = "192.168.123.13" 
#password = sys.argv[1]
#print "password:",password
password = raw_input("password:")

ssh = pexpect.spawn('ssh -l %s %s'%(user, host))
r = ssh.expect([pexpect.TIMEOUT, "password:"])
if r == 0:
	print 'ERROR! could not login with SSH. Here is what SSH said:'
	print ssh.before,ssh.after
	print str(ssh)
	sys.exit(1)
if r == 1:
	ssh.sendline('yes')
	r = ssh.expect("password:")
if r == 3:
	ssh.sendline(password)
	print "logining %s"%host
print "result:",r
