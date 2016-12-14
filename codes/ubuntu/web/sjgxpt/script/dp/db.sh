#!/bin/bash
#debug=echo
#set -ex
set -e
debug=$1

db_password="Oe123qwe###"

exp(){
	ip=$1
	schema=$2

	#exp userid=system/Oe123qwe### owner=gjzspt file=gjzspt.dmp
	exp_command='"exp userid=system/'$db_password' owner='$schema' file=/home/oracle/'$schema-$(date "+%F_%T")'.dmp"'
	#exp_command='which exp'
	#exp_command='bash -ic '"'"'which exp'"'"
	exp_command='bash -ic '$exp_command

	#$debug echo $exp_command | sshpass -p oracle ssh -T oracle@$ip
	$debug sshpass -p oracle ssh -t oracle@$ip $exp_command
}

#exp 192.168.21.249 gjzspt
shift
$*
