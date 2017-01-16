#!/bin/bash

#service_url=http://192.168.21.246:8062/sofn-dgap-pre/ws/dataExport
service_url=http://localhost:11080/sofn-dgap-pre/ws/dataExport

#fo="xmlstarlet fo -e 'utf-8'"
#fo="xmlstarlet unesc"
fo="recode xml..utf8"

curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\"" --data-binary @hb.xml $service_url | $fo

curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\"" --data-binary @de.xml $service_url | $fo
#curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\"" --data-binary @hb.xml $service_url | $fo

#http --verbose post $service_url @de.xml | $fo
#http --verbose post $service_url @de-error.xml | $fo
