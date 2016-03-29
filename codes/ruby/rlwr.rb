#!/usr/bin/env ruby
require 'colorize'

puts "starting center".green
puts `
LIB_JARS=$(find src/agricultural-credit-center/dist/lib -name "*.jar"|tr "\n" ":")
test -d src/agricultural-credit-center/dist/logs || mkdir -p src/agricultural-credit-center/dist/logs
ps -ef | grep '[a]gricultural-credit-center' && echo "center already started"
nohup java -classpath src/agricultural-credit-center/dist/conf:$LIB_JARS com.alibaba.dubbo.container.Main > src/agricultural-credit-center/dist/logs/agri-center.log 2>&1 &
`

puts "starting web".green
puts `
ps -ef | grep '[B]ootstrap' | awk '{print $2}' | xargs kill -9
test -d ~/he/sw/apache-tomcat-7.0.62/webapps-bak || mkdir -p ~/he/sw/apache-tomcat-7.0.62/webapps-bak
mv ~/he/sw/apache-tomcat-7.0.62/webapps/agri-web ~/he/sw/apache-tomcat-7.0.62/webapps-bak/agri-web-$(date +%Y%m%d)
cp -r src/agricultural-credit-web/target/agricultural-credit-web-0.0.1-SNAPSHOT ~/he/sw/apache-tomcat-7.0.62/webapps/agri-web
~/he/sw/apache-tomcat-7.0.62/bin/startup.sh
`
