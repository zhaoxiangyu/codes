#!/bin/bash

service_url=http://192.168.21.246:8062/sofn-dgap-pre/ws/dataImport

curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\"" --data-binary @data-import.xml $service_url

#http --verbose post $service_url @data-import.xml
