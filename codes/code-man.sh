#!/bin/bash
#set -x

# github user account: blueocci
fullpath=$(readlink -f $0)
basedir=$(dirname $fullpath)

help(){
	echo running help ...
}

install(){
	echo running install ...
	git config --global user.name "blueocci_ubuntu"
	git config --global user.email "blueocci@hotmail.com"
	cd ../..
	mkdir "github-`date --rfc-3339=date`"
	#cd "github-`date --rfc-3339=date`"
	git clone https://github.com/blueocci/codes.git "github-`date --rfc-3339=date`"
	#cd codes
	#git remote add upstream https://github.com/blueocci/codes.git
	#git fetch upstream
}

pull(){
	#echo $1
	echo pulling code from github ...
  pushd $basedir
	git pull
  popd
}

push(){
	#echo $1
	echo publish to github ...
  pushd $basedir
	git add -A .
	system_name=$(uname)
	git commit -m "publish on $system_name"
	git push
  popd
}

status(){
	#echo $1
	echo publish to github ...
  pushd $basedir
	git status
  popd
}

case "$1" in
help)
	git config -l
	;;
status)
	status
	;;
install)
	install
	;;
gui)
	echo running gui ...
	;;
pull)
	pull $2
	;;
push)
	push $2
	;;
*)
	echo "Usage: $0 {help|status|install|gui|push|pull}"
	exit 1
esac

exit
