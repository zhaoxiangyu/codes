#hive -f script.q
#hive -e 'select * from dummy'
#hive -S -e 'select * from dummy'

#hive -f gpstrace-schema.q
#hive -f gpstrace-data.q
time hive -f gpstrace-count.q
