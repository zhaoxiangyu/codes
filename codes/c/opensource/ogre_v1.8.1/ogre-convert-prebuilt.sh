#! /bin/bash

#############
# CONSTANTS #
################################################################################

# Absolute path to the source code directory.
SRC=`pwd`

# Absolute path to the build directory.
BUILD=`pwd`/build/darwin/xcode

# The build configuration. Recommended values are:
#   Release        - optimized code that excludes debugging information.
#   RelWithDebInfo - optimized code that includes debugging information.
CONFIG=Release

################################################################################

# Clear the old build and recompile the new one.
rm -rf $BUILD
mkdir $BUILD
cd $BUILD
cmake -D OGRE_BUILD_PLATFORM_APPLE_IOS=1 -G Xcode $SRC
cd $BUILD
xcodebuild -sdk iphoneos -configuration $CONFIG
mkdir $BUILD/lib/Release-iphoneos
mv $BUILD/lib/Release/* $BUILD/lib/Release-iphoneos
xcodebuild -sdk iphonesimulator -configuration $CONFIG
mkdir $BUILD/lib/Release-iphonesimulator
mv $BUILD/lib/Release/* $BUILD/lib/Release-iphonesimulator
for FILE in `ls $BUILD/lib/Release-iphoneos`
do
    lipo $BUILD/lib/Release-iphoneos/$FILE \
	-arch i386 $BUILD/lib/Release-iphonesimulator/$FILE \
	-create -output $BUILD/lib/Release/$FILE
done
rm -rf $BUILD/lib/Release-iphoneos
rm -rf $BUILD/lib/Release-iphonesimulator

# Copy the dependencies.
cp -R $SRC/iOSDependencies $BUILD
ln -s $BUILD/iOSDependencies/include/OIS $BUILD/include/OIS
rm -rf $BUILD/iOSDependencies/lib/Debug

# Add necessary files to the lib directory.
mv $BUILD/pkgconfig $BUILD/lib

# Remove samples, which are not useful as libraries.
rm $BUILD/lib/Release/*Sample*

# Add necessary files to the include directory.
mkdir $BUILD/include/OGRE
mkdir $BUILD/include/OGRE/iOS
mkdir $BUILD/include/OGRE/Paging
mkdir $BUILD/include/OGRE/Plugins
mkdir $BUILD/include/OGRE/Plugins/BSPSceneManager
mkdir $BUILD/include/OGRE/Plugins/OctreeSceneManager
mkdir $BUILD/include/OGRE/Plugins/OctreeZone
mkdir $BUILD/include/OGRE/Plugins/ParticleFX
mkdir $BUILD/include/OGRE/Plugins/PCZSceneManager
mkdir $BUILD/include/OGRE/Property
mkdir $BUILD/include/OGRE/RenderSystems
mkdir $BUILD/include/OGRE/RenderSystems/GLES
mkdir $BUILD/include/OGRE/RenderSystems/GLES/EAGL
mkdir $BUILD/include/OGRE/RenderSystems/GLES2
mkdir $BUILD/include/OGRE/RenderSystems/GLES2/EAGL
mkdir $BUILD/include/OGRE/RTShaderSystem
mkdir $BUILD/include/OGRE/Terrain
mkdir $BUILD/include/OGRE/Threading
mv $BUILD/include/OgreBuildSettings.h $BUILD/include/OGRE
cp $SRC/OgreMain/include/*.h $BUILD/include/OGRE
cp $SRC/Samples/Common/include/* $BUILD/include/OGRE
cp $SRC/OgreMain/include/iOS/* $BUILD/include/OGRE/iOS
cp $SRC/Components/Paging/include/* $BUILD/include/OGRE/Paging
cp $SRC/Plugins/BSPSceneManager/include/* $BUILD/include/OGRE/Plugins/BSPSceneManager
cp $SRC/Plugins/OctreeSceneManager/include/* $BUILD/include/OGRE/Plugins/OctreeSceneManager
cp $SRC/Plugins/OctreeZone/include/* $BUILD/include/OGRE/Plugins/OctreeZone
cp $SRC/Plugins/ParticleFX/include/* $BUILD/include/OGRE/Plugins/ParticleFX
cp $SRC/Plugins/PCZSceneManager/include/* $BUILD/include/OGRE/Plugins/PCZSceneManager
cp $SRC/Components/Property/include/* $BUILD/include/OGRE/Property
cp $SRC/RenderSystems/GLES/include/*.h $BUILD/include/OGRE/RenderSystems/GLES
cp $SRC/RenderSystems/GLES/include/EAGL/* $BUILD/include/OGRE/RenderSystems/GLES/EAGL
cp $SRC/RenderSystems/GLES2/include/*.h $BUILD/include/OGRE/RenderSystems/GLES2
cp $SRC/RenderSystems/GLES2/include/EAGL/* $BUILD/include/OGRE/RenderSystems/GLES2/EAGL
cp $SRC/Components/RTShaderSystem/include/* $BUILD/include/OGRE/RTShaderSystem
cp $SRC/Components/Terrain/include/* $BUILD/include/OGRE/Terrain
cp $SRC/OgreMain/include/Threading/* $BUILD/include/OGRE/Threading

# Remove everything except headers and libraries.
for FILE in `ls $BUILD`
do
    if [ $FILE != "include" ] &&
	[ $FILE != "iOSDependencies" ] &&
	[ $FILE != "lib" ]
    then
	rm -rf $FILE
    fi
done
