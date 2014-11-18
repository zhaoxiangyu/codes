#!/bin/bash

install(){
	sudo apt-get install libblas-dev liblapack-dev libfftw3-dev libsndfile1-dev swig
}

run(){
	echo running
	test -f king.wav || curl -O http://facstaff.bloomu.edu/jtomlins/Sounds/king.wav
	./swipe -r 50:400 -n -i king.wav
}

if test 0 -eq $#; then
	echo "usage: install run"
else
	eval $1
fi
