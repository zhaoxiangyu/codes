#!/bin/bash

genprj(){
    test -d /Developer || sudo ln -s /Applications/Xcode.app/Contents/Developer/ /Developer
    test -d build/ios || mkdir -p build/ios
    python platforms/ios/build_framework.py build/ios
}

clean(){
    test -d build/ios && rm -rf build/ios
}

if test 0 -eq $#; then
        echo usage: genprj clean
else
        eval $1
fi
