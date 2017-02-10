from fabric.api import *
from sqlalchemy import create_engine
from sqlalchemy.sql import text

def q():
    conn_str='oracle+cx_oracle://gjzspt:12345678@192.168.21.249:1521/gjzs'
    engine = create_engine(conn_str, echo=True)
    conn = engine.connect()
    s=text('select * from dual')
    conn.execute(s).fetchall()
    conn.close()
