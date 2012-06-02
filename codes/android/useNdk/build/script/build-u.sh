echo ----------bash---------------------------
NDK_ROOT=/home/he/sw/android/android-ndk-r7
export PRJ_LOC=`pwd`
export SU_PW=123qwe
#HOST_AWK=/usr/bin/gawk
NDK_AWK_DIR=$NDK_ROOT/prebuilt/linux-x86/bin
##############################################
echo NDK_ROOT: $NDK_ROOT
echo NDK_AWK_DIR: $NDK_AWK_DIR
echo PRJ_LOC: $PRJ_LOC
echo $1 $2 $3 $* $#
echo HOST_AWK: $HOST_AWK
echo ----------ndk-build $* ------------------

if test -f build/$1
then
	cd $PRJ_LOC ; make -f build/$1 $2 $3
else
	test -f $NDK_AWK_DIR/awk && mv $NDK_AWK_DIR/awk $NDK_AWK_DIR/awk_
	cd $PRJ_LOC ; $NDK_ROOT/ndk-build $*
fi
