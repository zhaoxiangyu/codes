#!/bin/bash

genprj(){
        test -d /Developer || sudo ln -s /Applications/Xcode.app/Contents/Developer/ /Developer
        test -d build/ios || mkdir -p build/ios
        python platforms/ios/build_framework.py build/ios
}

if test 0 -eq $#; then
        echo usage: genprj
else
        eval $1
fi
