#!/bin/bash

genprj(){
	echo "using cmake"
	test -d build/osx || (echo creating folder build/osx ; mkdir -p build/osx)
    pushd build/osx
    cmake -GXcode ../..
    popd
}

build(){
	pushd build/osx
	echo "building use xcode"
	xcodebuild -target install -configuration Debug
	popd
}

if test 0 -eq $#; then
	echo usage: genprj build
else
	eval $1
fi

