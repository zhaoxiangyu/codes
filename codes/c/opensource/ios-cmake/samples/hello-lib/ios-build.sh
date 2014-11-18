#!/bin/bash

IOS_CMAKE_DIR=/Users/he/work/opensource/ios-cmake

genprj(){
	echo "using cmake"
	test -d xcode/ios || (echo creating folder xcode/ios ; mkdir -p xcode/ios)
	if test -f ${IOS_CMAKE_DIR}/toolchain/iOS.cmake; then
		pushd xcode/ios
		cmake -DBUILD_STATIC_LIBRARIES=ON -DCMAKE_BUILD_TYPE=RelWithDebInfo -DCMAKE_IOS_DEVELOPER_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer -DCMAKE_OSX_ARCHITECTURES=armv7 -DCMAKE_IOS_SDK_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/SDKs/iPhoneOS7.0.sdk -DIOS_PLATFORM=OS -DCMAKE_TOOLCHAIN_FILE=${IOS_CMAKE_DIR}/toolchain/iOS.cmake -DCMAKE_C_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang -DCMAKE_CXX_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang++ -GXcode ../..
		popd
	else
		echo iOS.cmake not found!
	fi
}

build(){
	pushd xcode/ios
	echo "building use xcode"
	xcodebuild -target hello-lib -configuration Debug
	popd
}

if test 0 -eq $#; then
	echo usage: genprj build
else
	eval $1
fi

