#!/usr/bin/pytho
import sys,os,string,shutil
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

def clean():
	print "cleaning on system:",sys.platform
	if "win32" == sys.platform :
			build_dir = "build/win32"
			shutil.rmtree(build_dir)
	else:
			print "clean on unix"

if __name__ == "__main__":
	options,remainder = getopt(sys.argv[1:],'bc',['build','clean'])

	if len(sys.argv) == 1:
		print """command options:
	-b --build
	-c --clean
	"""

	for opt,arg in options:
		if opt in ('-b','--build'):
			build()
		elif opt in ('-c','--clean'):
			clean()

