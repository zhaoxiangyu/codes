#!/bin/bash

service_url=http://localhost:10080/sofn-dgap-web/wc/dailyStat
day=$(date +%F)

fo="recode xml..utf8"

#http --verbose get $service_url | $fo
http --verbose post $service_url day=$day | $fo