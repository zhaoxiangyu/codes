XCODE_BASE=/Applications/Xcode.app/Contents
SIMULATOR_BASE=$(XCODE_BASE)/Developer/Platforms/iPhoneSimulator.platform

FRAMEWORKS=$(SIMULATOR_BASE)/Developer/SDKs/iPhoneSimulator7.0.sdk/System/Library/Frameworks/
INCLUDES=$(SIMULATOR_BASE)/Developer/SDKs/iPhoneSimulator7.0.sdk/usr/include

SRCS=./jl/core/data/AudioInfo.cpp ./jl/core/data/Course.cpp ./jl/core/data/support/AudioInfoUtils.cpp ./jl/core/data/support/CourseUtils.cpp ./jl/core/data/support/UnitUtils.cpp ./jl/core/JpwordReader.cpp ./jl/core/runtime/LevelsInfo.cpp ./jl/core/runtime/State.cpp ./jl/core/utils/DataUtils.cpp ./jl/IOUtils.mm ./jl/JlAppDelegate.mm ./jl/JlViewController.mm ./jl/main.m ./jl/OsSupport.mm ./jl/ReaderEventListener.m

jl: $(SRCS)
	clang -arch i386 \
	    -mios-simulator-version-min=7.0 \
	    -fobjc-abi-version=2 \
	    -isysroot /Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/SDKs/iPhoneSimulator7.0.sdk \
	    -o jl $(SRCS) -framework Foundation -framework UIKit

run:
	"/Applications/Xcode.app/Contents/Developer/Platforms/iPhoneSimulator.platform/Developer/Applications/iPhone Simulator.app/Contents/MacOS/iPhone Simulator" -SimulateApplication ./jl
