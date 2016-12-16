#!/bin/bash
#debug=echo
#set -ex
set -e
debug=$1

db_password="Oe123qwe###"

exp(){
	ip=$1
	schema=$2
	local_dir=$3
	dmpfile_name=$schema-$(date "+%F_%T")'.dmp'
	localfile=$local_dir/$dmpfile_name

	#exp userid=system/Oe123qwe### owner=gjzspt file=gjzspt.dmp
	exp_command='"exp userid=system/'$db_password' owner='$schema' file=/home/oracle/'$dmpfile_name'"'
	#exp_command='which exp'
	#exp_command='bash -ic '"'"'which exp'"'"
	exp_command='bash -ic '$exp_command

	#$debug echo $exp_command | sshpass -p oracle ssh -T oracle@$ip
	$debug sshpass -p oracle ssh -t oracle@$ip $exp_command
	cp_command=scp oracle@$ip:/home/oracle/$dmpfile_name $localfile
	$debug sshpass -p oracle $cp_command
}

imp(){
	ip=$1
	schema=$2
	dmpfile_name=$3

	#exp userid=system/Oe123qwe### owner=gjzspt file=gjzspt.dmp
	imp_command='"imp userid=system/'$db_password' owner='$schema' file=/home/oracle/'$dmpfile_name'"'
	#exp_command='which exp'
	#exp_command='bash -ic '"'"'which exp'"'"
	imp_command='bash -ic '$imp_command

	#$debug echo $exp_command | sshpass -p oracle ssh -T oracle@$ip
	$debug sshpass -p oracle ssh -t oracle@$ip $imp_command
}

#exp 192.168.21.249 gjzspt ~/he/lky/input
#imp 192.168.21.249 gjzspt $dmpfile_name
shift
$*
