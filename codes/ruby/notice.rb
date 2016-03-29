#!/usr/bin/env ruby -w
require 'rubygems'
require './db'
# include db.rb

def addData
    (1001..1030).each do |noticeId|
        currentTime=Time.now.strftime('%Y-%m-%d %H:%M:%S')
        Db.db_update("INSERT INTO `notice`
        (`id`, `type`, `title`, `content`, `time`, `is_del`, `update_time`)
        VALUES 
        (#{noticeId}, 1, '系统公告', '系统#{noticeId}将于2015.12.1晚23：00-12.2早5：00  进行维护，请周知', '#{currentTime}', 0, '#{currentTime}');")

        Db.db_update("INSERT INTO `notice_target`
        (`notice_id`, `user_id`, `is_read`, `is_del`, `create_time`, `update_time`)
        VALUES
        (#{noticeId}, 155, 0, 0, '#{currentTime}', '#{currentTime}');")
        sleep(30)
    end
end

def rmData
    (1001..1030).each do |noticeId|
        Db.db_update("DELETE FROM `notice` WHERE id=#{noticeId};")
        Db.db_update("DELETE FROM `notice_target` WHERE notice_id=#{noticeId};")
    end
end

def noticeQuery
    Db.db_select("SELECT n.id,n.type,t.user_id
FROM notice n left join notice_target t 
ON ((n.type=1 AND n.is_del=0 AND FALSE) OR (n.type=2 AND n.id=t.notice_id ))
WHERE n.type =1 OR (n.type =2 AND t.user_id=155 AND t.is_del=0)
ORDER BY n.time desc ") do |row|
        puts row
    end
end

#addData
#rmData
#noticeQuery
