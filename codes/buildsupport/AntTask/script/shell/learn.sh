#!/bin/bash
# command 'set' to echo all function definitions
echo $*,$@,$#,$0,$1,$2
echo $!,$?
#echo '${@:1}='${@:1}
#echo '${@:2}='${@:2}
basename $0

#echo text in color and style
echo -e "\e[attribute code;text color code;background color codemYOUR TEXT HERE\e[0m"
#Attribute codes:
#00=none 01=bold 04=underscore 05=blink 07=reverse 08=concealed
#Text color codes:
#30=black 31=red 32=green 33=yellow 34=blue 35=magenta 36=cyan 37=white
#Background color codes:
#40=black 41=red 42=green 43=yellow 44=blue 45=magenta 46=cyan 47=white
echo -e "\e[34mText in blue color\e[0m"
echo -e "\e[04;37;40mText reversed color and underscored\e[0m"

echo '########'
install(){
	if [ $# -eq 0 ] || [ $1 = help ]
	then echo install: url=$1 ldir=$2
	else
	echo wget $1
	echo sudo apt-get install
	fi
}

myfunc1(){
    myresult1='some value1'
}
myfunc1
echo myresult1=$myresult1

myfunc2()
{
    local  myresult='some value2'
    echo myfunc2 running
    echo "$myresult"
}

result2=$(myfunc2)
echo result2=$result2
myfunc2
echo myfunc2:`myfunc2`

myfunc3(){
    local  __resultvar=$1
    local  myresult='some value3'
    eval $__resultvar="'$myresult'"
}

myfunc3 result3
echo result3=$result3

var_s=s
echo $(ls)
echo `ls`
echo '$(l$var_s)='$(l$var_s)
echo '`l$var_s`='`l$var_s`

var1=2
var2="var2 value"
echo '$var1='$var1
echo '$var2='$var2
echo '${var2}='${var2}
echo '$(var$var1)='$(var$var1)
echo '${var$var1}='${var$var1}
echo '${var${var1}}='${var${var1}}

av=e1
av[0]=ee1
av[1]=e2
echo '${av[0]}='${av[0]}

av2[0]=e1
av2[1]=e2
echo '${av2[0]}='${av2[0]}

av3=(e1 e2 e3)
echo '${av3[0]}='${av3[0]}
set -A av4 e1 e2 e3
echo '${av4[0]}='${av4[0]}


type ls
type myfunc3

echo '2+3*5'=$(( 2+3*5 ))
echo -e "haha\nnihao"
echo -e "haha\c"
printf "haha\n"
#{ read ha;echo $ha;} <<p;

var91=aaa
echo '${var91:-66}'=${var91:-66}
echo '${var92:-var92 default}'=${var92:-var92 default}
echo '${var91:+77}'=${var91:+77}
echo '${var93}='${var93}
echo '${var93:+xx}='${var93:+xx}

exit 1
