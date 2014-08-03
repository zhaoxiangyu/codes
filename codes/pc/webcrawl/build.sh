#!/bin/bash

status(){
  mvn dependency:tree -Dverbose
}

run(){
  mvn exec:java -Dexec.mainClass="org.sharpx.oxford.Controller"
}

updateide(){
  mvn eclipse:eclipse
}

echo $0 $1
eval "$1"
