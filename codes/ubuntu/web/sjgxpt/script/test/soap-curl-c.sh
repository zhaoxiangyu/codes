#!/bin/bash

#curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\""  --data-binary @request.xml http://localhost:11080/sofn-dgap-pre/ws/welcome1

curl -X POST -H "Content-Type: text/xml" -H "SOAPAction: \"\""  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.sofn.com/">
   <soapenv:Header>
      <auth:authentication xmlns:auth="http://gd.chinamobile.com//authentication">  
        <auth:systemID>1</auth:systemID>  
        <auth:userID>1</auth:userID>  
        <auth:password>121</auth:password>  
    </auth:authentication>    
   </soapenv:Header>
   <soapenv:Body>
      <ws:getMessage>
         <!--Optional:-->
         <arg0>?</arg0>
         <!--Optional:-->
         <arg1>?</arg1>
      </ws:getMessage>
   </soapenv:Body>
</soapenv:Envelope>
' http://localhost:11080/sofn-dgap-pre/ws/welcome1
