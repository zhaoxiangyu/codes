#!/bin/bash
set -x

hd=$(df|grep /home/hadoop/data2|awk '{print $1}')
iostat $hd -m 5
