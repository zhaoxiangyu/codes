wget http://fastdl.mongodb.org/win32/mongodb-win32-i386-2.6.3-signed.msi
rem install mongodb service
mongod --dbpath "e:\data\mongodb" --logpath "C:\Program Files\MongoDB 2.6 Standard\logs\mongod.log" --install 
