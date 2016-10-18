#!/usr/bash

update(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn update sofn-common sofn-generator sofn-sso-api
	svn update dgap-pre dgap-web dgap-api dgap-service
	popd
}

commit(){
	pushd /home/helong/he/lky/share/sjgxpt/udev/codes/dgap
	svn commit -m "" sofn-common sofn-generator sofn-sso-api
	svn commit -m "" dgap-pre dgap-web dgap-api dgap-service
	popd
}
