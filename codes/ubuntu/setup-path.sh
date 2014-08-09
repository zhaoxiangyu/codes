#!/bin/bash

#sed -i /PATH=/a\ \ \ \ PATH="~/work/code-repo/github/codes/ubuntu/user-home/bin:$PATH" /home/he/.profile
grep 'user-home' ~/.profile || sed -i $a\\nPATH="$HOME/work/code-repo/github/codes/ubuntu/user-home/bin:$PATH" /home/he/.profile
