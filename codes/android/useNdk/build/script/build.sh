echo ----------bash---------------------------
##############################################
echo NDK_ROOT: $NDK_ROOT
echo `cygpath $PRJ_LOC`
echo `cygpath $NDK_ROOT`/build

NDK_PROJECT_PATH=`cygpath $PRJ_LOC`
echo NDK_PROJECT_PATH: $NDK_PROJECT_PATH
echo $1 $2 $3 $* $#
echo ----------ndk-build $* ------------------
if [ $1 = "zinnia" ]
then
	echo -------------------build zinnia0.06-----------
	cd $NDK_PROJECT_PATH/jni/zinnia-0.06 \
	; ./configure
else
	echo -------------------ndk-build-------------------
	#cd `cygpath $PRJ_LOC` && `cygpath $NDK_ROOT`/ndk-build $*
fi
