#!/usr/bin/env ruby
require 'rubygems'
require 'mysql'
require 'yaml'

class Db
    def Db.with_db
        dbh = Mysql.real_connect('172.21.0.76', 'admin', 'admin','tcnx_web')
        puts "connecting to host: 127.21.0.76, databasename: tcnx_web"
        begin
            yield dbh
        ensure
            dbh.close
        end 
    end

    def Db.db_select(sql)
        with_db do |db|
            puts sql
            res = db.query(sql)
            if block_given?
                res.each_hash { |row| yield row }
                res.free
            end
        end
    end

    def Db.db_update(sql)
        with_db do |db|
            puts sql
            db.query(sql)
            puts "affected rows:#{db.affected_rows}" 
        end
    end

    def Db.select_users()
        db_select("SELECT * FROM user") { |row| puts row }
    end

    #select_users
    #list_views
end
