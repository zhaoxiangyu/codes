#!/bin/bash

#debug=echo
#set -ex
set -e
#debug="tmux new-window "

gen_wsdl(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-dgap-pre
	$debug mvn org.apache.cxf:cxf-java2ws-plugin:java2ws
	$debug popd
}

gen_java(){
	$debug pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap/sofn-dgap-web
	#$debug mvn generate-sources
	$debug mvn org.apache.cxf:cxf-codegen-plugin:wsdl2java
	$debug popd
}

$*
