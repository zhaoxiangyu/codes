#!/bin/bash

jl(){
  echo "switches to project jl"
  pushd /Users/he/data/code_repo/github/codes/os-x/prjs/jl
  #exec
}

if test $# -eq 0; then
  echo "ws: jl el"
else
  eval $1
fi
