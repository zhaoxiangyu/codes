#!/bin/bash

help(){
	echo running help ...
}

install(){
	echo running install ...
}

case "$1" in
help|status)
	help
	;;
install)
	install
	;;
gui)
	echo running gui ...
	;;
pub)
	echo publish to github ...
	git push
	;;
*)
	echo "Usage: $0 {help|status|install|gui}"
	exit 1
esac

exit
