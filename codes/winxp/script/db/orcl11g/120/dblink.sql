drop database link orcl120;
create public database link orcl120
  connect to wlw116120 identified by Wlw116120
  using 'orcl120';
exit