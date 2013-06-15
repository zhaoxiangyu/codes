#!/bin/bash

help(){
	echo running help ...
}

install(){
	echo running install ...
	echo 123qwe | sudo -S apt-get install libopencv-dev
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
*)
	echo "Usage: $0 {help|status|install|gui}"
	exit 1
esac

exit

