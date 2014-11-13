#!/usr/bin/pytho
import sys,os,string
from getopt import getopt

def build():
	print "building on system:",sys.platform
	if "win32" == sys.platform :
			build_dir = "build/win32"
			if not os.path.isdir(build_dir):
					os.makedirs(build_dir)

			currdir = os.getcwd()
			os.chdir(build_dir)
			os.system('cmake -G"CodeBlocks - MinGW Makefiles" ../..')
			os.chdir(currdir)
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

