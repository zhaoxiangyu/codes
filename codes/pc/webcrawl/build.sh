#!/bin/bash

status(){
  mvn dependency:tree -Dverbose|grep conflict
}

run(){
  mvn clean package
  mvn exec:java -Dexec.mainClass="org.sharpx.crawler.Controller"
}

update-ide(){
  mvn eclipse:eclipse
}

scm-commit(){
  mvn clean
  git add .
  git commit -m "added"
  git pull
  git push
}

if test 0 -eq $#; then
  echo command options: status run update-ide scm-commit
else
  eval "$1"
fi
