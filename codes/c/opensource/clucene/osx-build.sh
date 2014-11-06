#!/bin/bash

genprj(){
	echo "using cmake"
	test -d osx || (echo creating folder osx ; mkdir osx)
    pushd osx
    cmake -GXcode ..
    popd
}

build(){
	pushd osx
	echo "building use xcode"
	xcodebuild -target install -configuration Debug
	popd
}

if test 0 -eq $#; then
	echo usage: genprj build
else
	eval $1
fi

