#!/bin/bash
set -e
baseurl=http://localhost:10080/sofn-dgap-web

uuid=$(http --body get $baseurl/sso/getUUID|jq .uuid)
token=$(http --form --body post $baseurl/sso/login account=superadmin code=8888 password=00000000 uuid=$uuid | jq .token)
resourceId=$1

http --body post $baseurl/dataImport/fieldList resourceId=$resourceId
