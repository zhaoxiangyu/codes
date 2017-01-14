#/usr/bin/env bash

set -ex
PREFIX="https://raw.githubusercontent.com/blueocci/codes/master/codes"

ginstall(){
	local file=${1:?"you must set file path."}
	local url="$PREFIX"/"$file"
	curl -o $(basename $url) $url
}

ginstall 'ruby/rlwr.rb'
