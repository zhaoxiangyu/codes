#!/bin/bash

help(){
	echo running help ...
}

install(){
	echo running install ...
	echo 123qwe | sudo -S apt-get install build-essential libgtk2.0-dev libjpeg-dev libtiff4-dev libjasper-dev libopenexr-dev cmake python-dev python-numpy python-tk libtbb-dev libeigen2-dev yasm libfaac-dev libopencore-amrnb-dev libopencore-amrwb-dev libtheora-dev libvorbis-dev libxvidcore-dev libx264-dev libqt4-dev libqt4-opengl-dev sphinx-common texlive-latex-extra libv4l-dev libdc1394-22-dev libavcodec-dev libavformat-dev libswscale-dev
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

