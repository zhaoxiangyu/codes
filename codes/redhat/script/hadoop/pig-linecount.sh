#!/bin/bash
set -x

fileinfo=$(hadoop fs -ls /writetest.txt|tail -n 1)
time pig -param input=/writetest.txt linecount.pig 
echo $fileinfo
