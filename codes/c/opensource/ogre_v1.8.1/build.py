#!/usr/bin/python
import sys,os,string,shutil
from getopt import getopt

def genprj(target):
    print "cmaking on system:",sys.platform
    build_dir,generator,options=parse_target(target)
    cmake(build_dir,generator,options)


def build(target):
    print "building on system:",sys.platform
    build_dir,generator,options=parse_target(target)

    currdir = os.getcwd()
    os.chdir(build_dir)

    if "darwin" == sys.platform :
        os.system('cmake -b .')

    os.chdir(currdir)

def install(target):
    build_dir,generator,options=parse_target(target)

    currdir = os.getcwd()
    os.chdir(build_dir)

    if "darwin" == sys.platform :
        os.system('sudo make install')
    else:
        print "not implemenetd!"

    os.chdir(currdir)

def clean(target):
    print "cleaning on system:",sys.platform
    build_dir,generator,options=parse_target(target)
    shutil.rmtree(build_dir)

def parse_target(target):
    build_dir=""
    generator=""
    options=""
    if "darwin" == sys.platform :
        options="-DOGRE_BUILD_PLATFORM_APPLE_IOS=1"
        if "xcode" == target:
            build_dir="build/darwin/xcode"
            generator="Xcode"
        elif "makefile" == target:
            build_dir="build/darwin/makefile"
            generator="Unix Makefiles"
    elif "linux2" == sys.platform :
        if "cbp" == target:
            build_dir="build/linux/cbp"
            generator="CodeBlocks - Unix Makefiles"

    return (build_dir,generator,options)

def cmake(target_dir,generator,options):
    if not os.path.isdir(target_dir):
        os.makedirs(target_dir)

        currdir = os.getcwd()
        os.chdir(target_dir)
        os.system('cmake -G"'+generator+'" '+currdir+' '+options)
        os.chdir(currdir)

if __name__ == "__main__":
    options,remainder = getopt(sys.argv[1:],'t:bcg',['target=','build','clean','generate','install'])

    if len(sys.argv) == 1:
        print """command options:
    -t --target=target
    -b --build
    -g --generate
    -c --clean
    -i --install
    target can be one of xcode,cbp or makefile """
        print "sys.platform:",sys.platform

    target=""
    for opt,arg in options:
        if opt in ('-t','--target'):
            target=arg
        elif opt in ('-b','--build'):
            build(target)
        elif opt in ('-c','--clean'):
            clean(target)
        elif opt in ('-g','--generate'):
            genprj(target)
        elif opt in ('-i','--install'):
            install(target)

