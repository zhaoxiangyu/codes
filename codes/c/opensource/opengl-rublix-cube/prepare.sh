install(){
	sudo apt-get install libglew1.6-dev
}

show-glversion(){
	sudo apt-get install mesa-utils
	glxinfo|grep OpenGL
}

if test 0 -eq $#; then
	echo "command options: install show-glversion"
else
	eval $1
fi
