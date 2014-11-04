#!/bin/bash

IOS_CMAKE_DIR=/Users/he/Data/opensource/ios-cmake

genprj(){
	echo "using cmake"
	test -d ios || (echo creating folder ios ; mkdir ios)
	if test -f ${IOS_CMAKE_DIR}/toolchain/iOS.cmake; then
		pushd ios
		cmake -DCMAKE_TOOLCHAIN_FILE=${IOS_CMAKE_DIR}/toolchain/iOS.cmake -GXcode ..
		popd
	else
		echo iOS.cmake not found!
	fi
}

build(){
	pushd ios
	echo "building use xcode"
	xcodebuild -target install -configuration Debug
	popd
}

if test 0 -eq $#; then
	echo usage: genprj build
else
	eval $1
fi

