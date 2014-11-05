#!/bin/bash

genprj(){
        test -d /Developer || sudo ln -s /Applications/Xcode.app/Contents/Developer/ /Developer
        python platforms/ios/build_framework.py ios
}

if test 0 -eq $1; then
        echo usage: genprj
else
        eval $1
fi
