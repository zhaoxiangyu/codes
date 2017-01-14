#/usr/bin/env bash

#set -ex
set -e
PREFIX="https://raw.githubusercontent.com/blueocci/codes/master/codes"

ginstall(){
	local file=${1:?"you must set file path."}
	local install_dir=${2:-"/usr/local/bin"}
	local url="$PREFIX"/"$file"
	local filename=$(basename $url)
	local local_file=/tmp/$(basename $url)
	local target_filename=${filename%%.*}

	curl -o $local_file $url
	sudo install $local_file $install_dir/$target_filename
}

confirm(){
	local cmd="$*"
	echo "command:$cmd"
	read -p "Are you sure to run above command?" ans
	case "$ans" in
		y|Y)
			$cmd ;;
		n|N)
			echo "quitting..." ;;
	esac
}

ginstall 'ruby/yd.rb'
