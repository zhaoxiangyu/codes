#!/bin/bash

IOS_CMAKE_DIR=/Users/he/work/opensource/ios-cmake

genprj(){
	echo "using cmake"
	test -d build/ios || (echo creating folder build/ios ; mkdir -p build/ios)
	if test -f ${IOS_CMAKE_DIR}/toolchain/iOS.cmake; then
        curdir=$(pwd)
		pushd build/ios
		#cmake -DBUILD_STATIC_LIBRARIES=ON -DCMAKE_BUILD_TYPE=RelWithDebInfo -DCMAKE_IOS_DEVELOPER_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer -DCMAKE_OSX_ARCHITECTURES=armv7 -DCMAKE_IOS_SDK_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/SDKs/iPhoneOS7.0.sdk -DIOS_PLATFORM=OS -DCMAKE_TOOLCHAIN_FILE=${IOS_CMAKE_DIR}/toolchain/iOS.cmake -DCMAKE_C_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang -DCMAKE_CXX_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang++ -GXcode ../..
        #using ios-cmake-github instead of ios-cmake-googlecode
        if test -d /Developer/Platforms; then
                cmake -DBUILD_STATIC_LIBRARIES=ON -DCMAKE_BUILD_TYPE=RelWithDebInfo -DIOS_PLATFORM=SIMULATOR -DCMAKE_TOOLCHAIN_FILE=${IOS_CMAKE_DIR}/toolchain/iOS.cmake -DCMAKE_C_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang -DCMAKE_CXX_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang++ -GXcode ../.. 1>${curdir}/build.log 2>&1 
        else
                echo "directory /Developer/Platforms not exists"
        fi
		popd
	else
		echo iOS.cmake not found!
	fi
}

build(){
	pushd build/ios
	echo "building use xcode"
	xcodebuild -target install -configuration Debug
	popd
}

clean(){
	test -d build/ios && rm -rf build/ios
}

sysinfo(){
	pushd build/ios
	echo "show system info"
	cmake -DBUILD_STATIC_LIBRARIES=ON -DCMAKE_BUILD_TYPE=RelWithDebInfo -DCMAKE_IOS_DEVELOPER_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer -DCMAKE_OSX_ARCHITECTURES=armv7 -DCMAKE_IOS_SDK_ROOT=/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneOS.platform/Developer/SDKs/iPhoneOS7.0.sdk -DIOS_PLATFORM=OS -DCMAKE_TOOLCHAIN_FILE=${IOS_CMAKE_DIR}/toolchain/iOS.cmake -DCMAKE_C_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang -DCMAKE_CXX_COMPILER=/Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/clang++ --system-information system-info.txt
	popd
}

if test 0 -eq $#; then
	echo usage: genprj build sysinfo clean
else
	eval $1
fi

