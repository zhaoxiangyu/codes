drop database link ora135;
create database link ora135
  connect to wlw116135 identified by wlw_116
  using 'ORA@135';
exit