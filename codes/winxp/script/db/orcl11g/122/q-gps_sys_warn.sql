select * from gps_sys_warn 
       where v_provider is null 
       and content like '%ͨѶ%'
       order by warntime desc;

select * from gps_sys_warn
       where content like '%����5����%'
       order by warntime desc;
       
/*       
update gps_sys_warn set state=0, state_reg=0 
where content like '%����5����%' and state is null and state_reg is null;
*/

/*
update gps_sys_warn set regain_time='2012-11-13 10:49:00', state_reg=1 
where content like '%ͨѶ%' and state is null and state_reg is null;
*/