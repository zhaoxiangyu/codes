#!/bin/bash

# github user account: blueocci

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

pub(){
	#echo $1
	echo publish to github ...
	git add -A .
	system_name=$(uname)
	git commit -m "publish on $system_name"
	git push
}

case "$1" in
help)
	git config -l
	;;
status)
	help
	;;
install)
	install
	;;
gui)
	echo running gui ...
	;;
pub)
	pub $2
	;;
*)
	echo "Usage: $0 {help|status|install|gui|pub}"
	exit 1
esac

exit
