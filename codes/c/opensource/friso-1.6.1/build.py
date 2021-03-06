#!/usr/bin/python
import sys,os,string,shutil
from getopt import getopt

def genprj(target):
	print "cmaking on system:",sys.platform
	build_dir,generator=parse_target(target)
	cmake(build_dir,generator)

def parse_target(target):
	if "darwin" == sys.platform :
		if "xcode" == target:
			build_dir="build/darwin/xcode"
			generator="Xcode"
	elif "linux2" == sys.platform :
		if "cbp" == target:
			build_dir="build/linux/cbp"
			generator="CodeBlocks - Unix Makefiles"

	return (build_dir,generator)


def build(target):
	print "building on system:",sys.platform
	build_dir,generator=parse_target(target)

	currdir = os.getcwd()
	os.chdir(build_dir)
	os.system('cmake -b .')
	os.chdir(currdir)

def cmake(target_dir,generator):
	if not os.path.isdir(target_dir):
		os.makedirs(target_dir)

	currdir = os.getcwd()
	os.chdir(target_dir)
	os.system('cmake -G"'+generator+'" '+currdir+'')
	os.chdir(currdir)


def clean(target):
	print "cleaning on system:",sys.platform
	build_dir,generator=parse_target(target)
	shutil.rmtree(build_dir)

if __name__ == "__main__":
	options,remainder = getopt(sys.argv[1:],'b:c:g:',['build=','clean=','generate='])

	if len(sys.argv) == 1:
		print """command options:
	-b --build=target
	-g --generate=target
	-c --clean=target
	target can be one of xcode or cbp"""
	        print "sys.platform:",sys.platform

	for opt,arg in options:
		if opt in ('-b','--build'):
			build(arg)
		elif opt in ('-c','--clean'):
			clean(arg)
		elif opt in ('-g','--generate'):
			genprj(arg)

