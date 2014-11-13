#!/usr/bin/pytho
import sys,os,string
from getopt import getopt

def build():
	print "building on system:",sys.platform
	if "win32" == sys.platform :
			print "win32"
	else:
			print "unix"


if __name__ == "__main__":
	options,remainder = getopt(sys.argv[1:],'b',['build'])

	if len(sys.argv) == 1:
		print """command options:
	-b --build
	"""

	for opt,arg in options:
		if opt in ('-b','--build'):
			build()

