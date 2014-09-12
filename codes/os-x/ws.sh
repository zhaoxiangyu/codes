#!/bin/bash

jl(){
  echo "switches to project japanese learn"
  pushd /Users/he/data/code_repo/github/codes/os-x/prjs/jl
  #exec
}

cw(){
  echo "switches to project cross word"
  pushd /Users/he/data/code_repo/github/codes/os-x/prjs/cw
  #exec
}

if test $# -eq 0; then
  echo "ws: jl cw"
else
  eval $1
fi
