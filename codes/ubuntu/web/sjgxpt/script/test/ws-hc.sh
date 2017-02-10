#!/bin/bash

service_url=http://localhost:10080/sofn-dgap-web/wc/wsHealthCheck

fo="recode xml..utf8"

http --verbose get $service_url | $fo
