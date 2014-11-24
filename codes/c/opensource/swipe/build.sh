#!/bin/bash

install(){
	sudo apt-get install libblas-dev liblapack-dev libfftw3-dev libsndfile1-dev swig
}

run(){
	echo running
	test -f king.wav || curl -O http://facstaff.bloomu.edu/jtomlins/Sounds/king.wav
	play king.wav
	./swipe -r 50:400 -n -i king.wav
}

testmn(){
	echo testing music notes
	play 1C.wav
	./swipe -r 50:400 -n -i 1C.wav
}

prepare(){
	echo preparing wave files
	for aif in music-note-audios/*.aif; do
		sox $aif $(basename $aif .aif).wav
	done
}

clean(){
	echo cleaning wave files
	rm [0-9]*.wav
}

if test 0 -eq $#; then
	echo "usage: install run prepare testmn clean"
else
	eval $1
fi
