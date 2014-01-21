-- Create the user 
create user QB
  identified by ""
  default tablespace USERS
  temporary tablespace TEMP
  profile DEFAULT;
-- Grant/Revoke role privileges 
grant dba to QB with admin option;
-- Grant/Revoke system privileges 
grant create session to QB with admin option;
grant unlimited tablespace to QB with admin option;