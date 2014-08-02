#!/bin/bash

status(){
  mvn dependency:tree -Dverbose
}

run(){
  mvn exec:java -Dexec.mainClass="org.sharpx.oxford.Controller"
}

echo $0 $1
$1
