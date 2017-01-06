#!/bin/bash

#service_url=http://192.168.21.246:8062/sofn-dgap-pre/ws/dataImport
service_url=http://localhost:11080/sofn-dgap-pre/ws/dataImport

#fo="xmlstarlet fo -e 'utf-8'"
#fo="xmlstarlet unesc"
fo="recode xml..utf8"

curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\"" --data-binary @data-import.xml $service_url | $fo
#http --verbose post $service_url @data-import.xml
