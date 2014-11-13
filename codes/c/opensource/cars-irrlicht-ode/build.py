#!/usr/bin/pytho
import sys,os,string,shutil
from getopt import getopt

def build(target):
	print "building on system:",sys.platform
	if "win32" == sys.platform :
		if "cbp" == target:
			cmake("build/win32/cbp","CodeBlocks - MinGW Makefiles")
		elif "vs" == target:
			cmake("build/win32/vs","Visual Studio 8 2005")

	else:
		print "unix"

def cmake(target_dir,generator):
	if not os.path.isdir(target_dir):
		os.makedirs(target_dir)

	currdir = os.getcwd()
	os.chdir(target_dir)
	os.system('cmake -G"'+generator+'" '+currdir+'')
	os.chdir(currdir)


def clean(target):
	print "cleaning on system:",sys.platform
	if "win32" == sys.platform :
		if "cbp" == target:
			shutil.rmtree("build/win32/cbp")
		elif "vs" == target:
			shutil.rmtree("build/win32/vs")
	else:
		print "clean on unix"

if __name__ == "__main__":
	options,remainder = getopt(sys.argv[1:],'bcg:',['build','clean','generate='])

	if len(sys.argv) == 1:
		print """command options:
	-b --build
	-g --generate=vs,cbp
	-c --clean
	"""

	target=""
	for opt,arg in options:
		if opt in ('-b','--build'):
			build(target)
		elif opt in ('-c','--clean'):
			clean(target)
		elif opt in ('-g','--generate'):
			target=arg

