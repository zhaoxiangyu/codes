find -L . -path '*/src/*/*.xml'|xargs -I xx ack 'insert into (\w+)' xx --output='$1'|sort -u
