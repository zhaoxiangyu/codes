select avg(speed) from gps_trace_20120912;

select v_provider,state,avg(speed) from gps_trace_20120912
group by cube(v_provider,state)
order by v_provider asc, state asc