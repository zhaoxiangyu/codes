#!/bin/bash

set -x

update(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn update sofn-common sofn-generator sofn-sso-api
	svn update sofn-dgap-pre sofn-dgap-web sofn-dgap-api sofn-dgap-service
	popd
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes
	svn update soft-webfont
	popd
}

status(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn status sofn-common sofn-generator sofn-sso-api
	svn status sofn-dgap-pre sofn-dgap-web sofn-dgap-api sofn-dgap-service
	popd
}

log(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn log -l3 sofn-common
	svn log -l3 sofn-generator
	svn log -l3 sofn-sso-api
	svn log -l3 sofn-dgap-pre
	svn log -l3 sofn-dgap-web
	svn log -l3 sofn-dgap-api
	svn log -l3 sofn-dgap-service
	popd
}

commit(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn commit -m "$1" sofn-common sofn-generator sofn-sso-api
	svn commit -m "$1" sofn-dgap-pre sofn-dgap-web sofn-dgap-api sofn-dgap-service
	popd
}


eval $1 $2
