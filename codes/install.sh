#!/bin/bash
ln -fs $(readlink -f code-man.sh) ~/
ln -fs $(readlink -f ubuntu/ci.sh) ~/
ln -fs $(readlink -f ubuntu/co.sh) ~/
ln -fs $(readlink -f gi.sh) ~/

sudo curl -o /usr/local/bin/desk https://raw.githubusercontent.com/jamesob/desk/master/desk; sudo chmod +x /usr/local/bin/desk
