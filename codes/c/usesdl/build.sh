#!/bin/bash

pkg-config --list-all | grep -i sdl

pushd linux
cmake -G "CodeBlocks - Unix Makefiles" ..
popd
