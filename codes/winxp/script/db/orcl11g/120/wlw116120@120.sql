------------------------------------------------------
-- Export file for user WLW116120                   --
-- Created by Administrator on 2012-10-24, 14:49:42 --
------------------------------------------------------

spool wlw116120@120.log

prompt
prompt Creating table BASE_DIC_TYPE
prompt ============================
prompt
create table WLW116120.BASE_DIC_TYPE
(
  DIC_TYPE_ID    VARCHAR2(32) not null,
  DIC_TYPE_CODE  VARCHAR2(50),
  DIC_TYPE_NAME  VARCHAR2(255),
  DIC_TYPE_DESC  VARCHAR2(255),
  IS_EFFECTIVE   VARCHAR2(50) default '1',
  IS_COMMON      VARCHAR2(50) default '1',
  CREATE_TIME    DATE default sysdate,
  LAST_EDIT_TIME DATE default sysdate
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_DIC_TYPE.DIC_TYPE_CODE
  is '字典类型编码';
comment on column WLW116120.BASE_DIC_TYPE.DIC_TYPE_NAME
  is '字典类型名称';
comment on column WLW116120.BASE_DIC_TYPE.DIC_TYPE_DESC
  is '字典类型描述';
comment on column WLW116120.BASE_DIC_TYPE.IS_EFFECTIVE
  is '是否有效(字典)';
comment on column WLW116120.BASE_DIC_TYPE.IS_COMMON
  is '是否通用字典';
comment on column WLW116120.BASE_DIC_TYPE.CREATE_TIME
  is '创建时间';
comment on column WLW116120.BASE_DIC_TYPE.LAST_EDIT_TIME
  is '最后修改时间';
alter table WLW116120.BASE_DIC_TYPE
  add constraint PK_DIC_TYPE primary key (DIC_TYPE_ID)
  using index 
  tablespace WLW120
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_DIC_VALUE
prompt =============================
prompt
create table WLW116120.BASE_DIC_VALUE
(
  DIC_VALUE_ID        VARCHAR2(32) not null,
  DIC_TYPE_ID         VARCHAR2(32),
  DIC_VALUE_NAME      VARCHAR2(200),
  DIC_VALUE_ALIAS     VARCHAR2(200),
  DIC_VALUE_CODE      VARCHAR2(50),
  DIC_VALUE_TREE_CODE VARCHAR2(32),
  IS_EFFECTIVE        VARCHAR2(50),
  DISPLAY_ORDER       NUMBER,
  CREATE_TIME         DATE default sysdate,
  LAST_EDIT_TIME      DATE default sysdate,
  CH_SPELL            VARCHAR2(200)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_DIC_VALUE.DIC_TYPE_ID
  is '类型ID';
comment on column WLW116120.BASE_DIC_VALUE.DIC_VALUE_NAME
  is '名称';
comment on column WLW116120.BASE_DIC_VALUE.DIC_VALUE_ALIAS
  is '别名或简称';
comment on column WLW116120.BASE_DIC_VALUE.DIC_VALUE_CODE
  is '业务编码';
comment on column WLW116120.BASE_DIC_VALUE.DIC_VALUE_TREE_CODE
  is '父节点ID';
comment on column WLW116120.BASE_DIC_VALUE.IS_EFFECTIVE
  is '是否有效（字典）';
comment on column WLW116120.BASE_DIC_VALUE.DISPLAY_ORDER
  is '显示顺序';
comment on column WLW116120.BASE_DIC_VALUE.CREATE_TIME
  is '创建时间';
comment on column WLW116120.BASE_DIC_VALUE.LAST_EDIT_TIME
  is '最后修改时间';
comment on column WLW116120.BASE_DIC_VALUE.CH_SPELL
  is '名称的汉字拼音';
alter table WLW116120.BASE_DIC_VALUE
  add constraint PC_DIC_VALUE primary key (DIC_VALUE_ID)
  using index 
  tablespace WLW120
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_MENU
prompt ========================
prompt
create table WLW116120.BASE_MENU
(
  MENU_ID        VARCHAR2(32) not null,
  MENU_NAME      VARCHAR2(200),
  MENU_DESC      VARCHAR2(500),
  MENU_URL       VARCHAR2(200),
  DISPLAY_ORDER  NUMBER,
  PARENT_ID      VARCHAR2(32),
  IS_EFFECTIVE   VARCHAR2(50) default 1,
  CREATE_TIME    DATE default sysdate,
  LAST_EDIT_TIME DATE default sysdate,
  IMAGE_NAME     VARCHAR2(200),
  IMAGE_PATH     VARCHAR2(200)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_MENU.MENU_NAME
  is '资源名称';
comment on column WLW116120.BASE_MENU.MENU_DESC
  is '资源描述';
comment on column WLW116120.BASE_MENU.MENU_URL
  is 'URl（唯一）';
comment on column WLW116120.BASE_MENU.DISPLAY_ORDER
  is '显示顺序';
comment on column WLW116120.BASE_MENU.PARENT_ID
  is '父节点ID';
comment on column WLW116120.BASE_MENU.IS_EFFECTIVE
  is '是否有效（字典）';
comment on column WLW116120.BASE_MENU.CREATE_TIME
  is '创建时间';
comment on column WLW116120.BASE_MENU.LAST_EDIT_TIME
  is '最后修改时间';
comment on column WLW116120.BASE_MENU.IMAGE_NAME
  is '菜单图表名称';
comment on column WLW116120.BASE_MENU.IMAGE_PATH
  is '菜单图表路径';
alter table WLW116120.BASE_MENU
  add constraint PK_BASE_RESOURCES primary key (MENU_ID)
  using index 
  tablespace WLW120
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_ORGANIZES
prompt =============================
prompt
create table WLW116120.BASE_ORGANIZES
(
  ORG_ID         VARCHAR2(32) not null,
  ORG_NAME       VARCHAR2(100),
  ORG_CODE       VARCHAR2(50),
  ORG_TYPE       VARCHAR2(50),
  PARENT_ID      VARCHAR2(32),
  OTHER_TYPE     VARCHAR2(200),
  DISPLAY_ORDER  NUMBER,
  IS_EFFECTIVE   VARCHAR2(50),
  CREATE_TIME    DATE default sysdate,
  LAST_EDIT_TIME DATE default sysdate,
  CH_SPELL       VARCHAR2(200)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_ORGANIZES.ORG_NAME
  is '组织机构名称';
comment on column WLW116120.BASE_ORGANIZES.ORG_CODE
  is '业务编码';
comment on column WLW116120.BASE_ORGANIZES.ORG_TYPE
  is '组织机构类型（代码）';
comment on column WLW116120.BASE_ORGANIZES.PARENT_ID
  is '父节点ID';
comment on column WLW116120.BASE_ORGANIZES.OTHER_TYPE
  is '其他类型（预留）';
comment on column WLW116120.BASE_ORGANIZES.DISPLAY_ORDER
  is '显示顺序';
comment on column WLW116120.BASE_ORGANIZES.IS_EFFECTIVE
  is '是否有效';
comment on column WLW116120.BASE_ORGANIZES.CREATE_TIME
  is '创建时间';
comment on column WLW116120.BASE_ORGANIZES.LAST_EDIT_TIME
  is '最后修改时间';
comment on column WLW116120.BASE_ORGANIZES.CH_SPELL
  is '组织机构名称的汉字拼音';

prompt
prompt Creating table BASE_ROLES
prompt =========================
prompt
create table WLW116120.BASE_ROLES
(
  ROLE_ID        VARCHAR2(32) not null,
  ROLE_NAME      VARCHAR2(200),
  ROLE_DESC      VARCHAR2(500),
  IS_EFFECTIVE   VARCHAR2(50),
  CREATE_TIME    DATE default sysdate,
  LAST_EDIT_TIME DATE default sysdate
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_ROLES.ROLE_NAME
  is '角色名称';
comment on column WLW116120.BASE_ROLES.ROLE_DESC
  is '角色描述';
comment on column WLW116120.BASE_ROLES.IS_EFFECTIVE
  is '是否有效（字典）';
comment on column WLW116120.BASE_ROLES.CREATE_TIME
  is '创建时间（时间戳）';
comment on column WLW116120.BASE_ROLES.LAST_EDIT_TIME
  is '最后修改时间（时间戳）';

prompt
prompt Creating table BASE_ROLES_MENU
prompt ==============================
prompt
create table WLW116120.BASE_ROLES_MENU
(
  ROLE_ID VARCHAR2(32),
  MENU_ID VARCHAR2(32)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_ROLES_USERS
prompt ===============================
prompt
create table WLW116120.BASE_ROLES_USERS
(
  ROLE_ID VARCHAR2(32) not null,
  USER_ID VARCHAR2(32) not null
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table BASE_SYS_LOG
prompt ===========================
prompt
create table WLW116120.BASE_SYS_LOG
(
  ID                   VARCHAR2(32) not null,
  OPERATE_MODULE       VARCHAR2(50),
  OPERATE_TYPE         VARCHAR2(50),
  PARAMETER            VARCHAR2(4000),
  IP                   VARCHAR2(30),
  OPERATE_USER_ID      VARCHAR2(32),
  OPERATE_USER_ACCOUNT VARCHAR2(50),
  OPERATE_TIME         DATE default sysdate,
  CLASS_METHOD         VARCHAR2(200),
  EFFECT_ID            VARCHAR2(32)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table WLW116120.BASE_SYS_LOG
  is '情报平台操作日志表';
comment on column WLW116120.BASE_SYS_LOG.ID
  is '日志ID';
comment on column WLW116120.BASE_SYS_LOG.OPERATE_MODULE
  is '操作模块';
comment on column WLW116120.BASE_SYS_LOG.OPERATE_TYPE
  is '操作类型';
comment on column WLW116120.BASE_SYS_LOG.PARAMETER
  is '参数信息';
comment on column WLW116120.BASE_SYS_LOG.IP
  is 'IP';
comment on column WLW116120.BASE_SYS_LOG.OPERATE_USER_ID
  is '操作人ID';
comment on column WLW116120.BASE_SYS_LOG.OPERATE_USER_ACCOUNT
  is '操作人账号';
comment on column WLW116120.BASE_SYS_LOG.OPERATE_TIME
  is '操作时间';
comment on column WLW116120.BASE_SYS_LOG.CLASS_METHOD
  is '操作的类名+方法名';
comment on column WLW116120.BASE_SYS_LOG.EFFECT_ID
  is '有效id';

prompt
prompt Creating table BASE_USERS
prompt =========================
prompt
create table WLW116120.BASE_USERS
(
  USER_ID        VARCHAR2(32) not null,
  USER_ACCOUNT   VARCHAR2(20),
  USER_NAME      VARCHAR2(100),
  USER_PASSWORD  VARCHAR2(64),
  USER_IDENTITY  VARCHAR2(100),
  USER_ID_CARD   VARCHAR2(18),
  USER_PHONE     VARCHAR2(50),
  IS_EFFECTIVE   VARCHAR2(50),
  CREATE_TIME    DATE default sysdate,
  LAST_EDIT_TIME DATE default sysdate,
  ORG_ID         VARCHAR2(32),
  CH_SPELL       VARCHAR2(200)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.BASE_USERS.USER_ACCOUNT
  is '账号';
comment on column WLW116120.BASE_USERS.USER_NAME
  is '用户名';
comment on column WLW116120.BASE_USERS.USER_PASSWORD
  is '密码';
comment on column WLW116120.BASE_USERS.USER_IDENTITY
  is '用户标识（如：警号）';
comment on column WLW116120.BASE_USERS.USER_ID_CARD
  is '身份证号';
comment on column WLW116120.BASE_USERS.USER_PHONE
  is '联系电话';
comment on column WLW116120.BASE_USERS.IS_EFFECTIVE
  is '是否有效(1:是;0:否)';
comment on column WLW116120.BASE_USERS.CREATE_TIME
  is '创建时间';
comment on column WLW116120.BASE_USERS.LAST_EDIT_TIME
  is '最后修改时间';
comment on column WLW116120.BASE_USERS.ORG_ID
  is '组织机构ID';
comment on column WLW116120.BASE_USERS.CH_SPELL
  is '用户名称的汉字拼音';

prompt
prompt Creating table GPS_DAY_COUNTS
prompt =============================
prompt
create table WLW116120.GPS_DAY_COUNTS
(
  PROVIDER       INTEGER,
  COUNTDATE      DATE,
  CHUZU          INTEGER default 0,
  CHUZUCOUNT     INTEGER default 0,
  ZULIN          INTEGER default 0,
  ZULINCOUNT     INTEGER default 0,
  GONGJIAO       INTEGER default 0,
  GONGJIAOCOUNT  INTEGER default 0,
  LVYOU          INTEGER default 0,
  LVYOUCOUNT     INTEGER default 0,
  KEYUN          INTEGER default 0,
  KEYUNCOUNT     INTEGER default 0,
  HUAWEICHE      INTEGER default 0,
  HUAWEICHECOUNT INTEGER default 0,
  XIAOCHE        INTEGER default 0,
  XIAOCHECOUNT   INTEGER default 0,
  QITA           INTEGER default 0,
  QITACOUNT      INTEGER default 0
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table GPS_GROUP
prompt ========================
prompt
create table WLW116120.GPS_GROUP
(
  GROUPID   VARCHAR2(32),
  GNAME     VARCHAR2(64),
  GCOMMENT  VARCHAR2(512),
  ENTERTIME DATE default sysdate,
  ENTERUSR  VARCHAR2(32),
  UPTIME    DATE,
  UPUSR     VARCHAR2(32)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.GPS_GROUP.GROUPID
  is '组号';
comment on column WLW116120.GPS_GROUP.GNAME
  is '组名称';
comment on column WLW116120.GPS_GROUP.GCOMMENT
  is '组的描述';
comment on column WLW116120.GPS_GROUP.ENTERTIME
  is '组创建时间';
comment on column WLW116120.GPS_GROUP.ENTERUSR
  is '创建人ID';
comment on column WLW116120.GPS_GROUP.UPTIME
  is '更新人';
comment on column WLW116120.GPS_GROUP.UPUSR
  is '更新人ID';

prompt
prompt Creating table GPS_GROUP_VHL
prompt ============================
prompt
create table WLW116120.GPS_GROUP_VHL
(
  GVID       VARCHAR2(32),
  GROUPID    VARCHAR2(32),
  REGCODE    VARCHAR2(32),
  V_PROVIDER VARCHAR2(32)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.GPS_GROUP_VHL.GROUPID
  is '组号';
comment on column WLW116120.GPS_GROUP_VHL.REGCODE
  is '车牌号码';
comment on column WLW116120.GPS_GROUP_VHL.V_PROVIDER
  is '运营商';

prompt
prompt Creating table GPS_ICONS
prompt ========================
prompt
create table WLW116120.GPS_ICONS
(
  ICONID      VARCHAR2(32),
  ICONNM      VARCHAR2(64),
  SIZE_WIDTH  INTEGER,
  SIZE_HEIGHT INTEGER,
  ISVALID     INTEGER default 1,
  ENTERUSR    VARCHAR2(32),
  ENTERTIME   DATE default sysdate,
  UPUSR       VARCHAR2(32),
  UPTIME      DATE,
  EXT         VARCHAR2(16),
  ICON_SIZE   INTEGER
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table WLW116120.GPS_ICONS
  is '点图标信息库';
comment on column WLW116120.GPS_ICONS.ICONNM
  is '图标名称';
comment on column WLW116120.GPS_ICONS.SIZE_WIDTH
  is '图标宽度';
comment on column WLW116120.GPS_ICONS.SIZE_HEIGHT
  is '图标高度';
comment on column WLW116120.GPS_ICONS.ISVALID
  is '是否有效，1有效，其他无效';
comment on column WLW116120.GPS_ICONS.EXT
  is '图标扩展名';
comment on column WLW116120.GPS_ICONS.ICON_SIZE
  is '图标大小';

prompt
prompt Creating table GPS_MSLAYER
prompt ==========================
prompt
create table WLW116120.GPS_MSLAYER
(
  SHAPEID   VARCHAR2(32),
  SHAPE     INTEGER,
  THETITLE  VARCHAR2(128),
  THEDESC   VARCHAR2(1024),
  POINTS    CLOB,
  ENTERUSR  VARCHAR2(32),
  ENTERTIME DATE default sysdate,
  UPUSR     VARCHAR2(32),
  UPTIME    DATE,
  ISVALID   INTEGER default 1,
  COLOR     VARCHAR2(16),
  WEIGHT    INTEGER,
  OPACITY   INTEGER,
  ICON      VARCHAR2(32)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table WLW116120.GPS_MSLAYER
  is '??????????';
comment on column WLW116120.GPS_MSLAYER.SHAPEID
  is '????????ID';
comment on column WLW116120.GPS_MSLAYER.SHAPE
  is '???????? 1????2????4????8??????16??????';
comment on column WLW116120.GPS_MSLAYER.THETITLE
  is '????????';
comment on column WLW116120.GPS_MSLAYER.THEDESC
  is '????????';
comment on column WLW116120.GPS_MSLAYER.POINTS
  is '??????????????';
comment on column WLW116120.GPS_MSLAYER.ENTERUSR
  is '????????';
comment on column WLW116120.GPS_MSLAYER.ENTERTIME
  is '????????';
comment on column WLW116120.GPS_MSLAYER.UPUSR
  is '????????????';
comment on column WLW116120.GPS_MSLAYER.UPTIME
  is '????????????';
comment on column WLW116120.GPS_MSLAYER.ISVALID
  is '???????? , 1??????0????';
comment on column WLW116120.GPS_MSLAYER.COLOR
  is '????,??????????';
comment on column WLW116120.GPS_MSLAYER.WEIGHT
  is '????????????????';
comment on column WLW116120.GPS_MSLAYER.OPACITY
  is '??????';
comment on column WLW116120.GPS_MSLAYER.ICON
  is '????????';

prompt
prompt Creating table GPS_NOTICE
prompt =========================
prompt
create table WLW116120.GPS_NOTICE
(
  NOTICE_ID VARCHAR2(32),
  USER_ID   VARCHAR2(32),
  CTR_TIME  DATE,
  REGCODE   VARCHAR2(255),
  NOTICE    VARCHAR2(255)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.GPS_NOTICE.USER_ID
  is '操作用户id';
comment on column WLW116120.GPS_NOTICE.CTR_TIME
  is '创建时间';
comment on column WLW116120.GPS_NOTICE.REGCODE
  is '车牌照，这里可以存多个车牌照用 “,”逗号隔开';
comment on column WLW116120.GPS_NOTICE.NOTICE
  is '通知内容';

prompt
prompt Creating table GPS_RECEIVEDATA
prompt ==============================
prompt
create table WLW116120.GPS_RECEIVEDATA
(
  ID          VARCHAR2(36) not null,
  PROVIDER    INTEGER,
  NORMAL      INTEGER,
  BAD         INTEGER,
  STATE_IS_0  INTEGER,
  INSERT_TIME DATE
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table GPS_REGION
prompt =========================
prompt
create table WLW116120.GPS_REGION
(
  REGIONID   VARCHAR2(32) not null,
  RG_TITLE   VARCHAR2(128),
  RG_DESC    VARCHAR2(512),
  REGION     CLOB,
  ENTERTIME  DATE default sysdate,
  UPTIME     DATE default sysdate,
  ENTERUSR   VARCHAR2(32),
  UPUSR      VARCHAR2(32),
  SHAPE      CHAR(1),
  VALID      CHAR(1) default 1,
  BEGIN_TIME DATE,
  END_TIME   DATE
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.GPS_REGION.REGIONID
  is '????????????';
comment on column WLW116120.GPS_REGION.RG_TITLE
  is '????????-????';
comment on column WLW116120.GPS_REGION.RG_DESC
  is '????????????';
comment on column WLW116120.GPS_REGION.REGION
  is '????????????';
comment on column WLW116120.GPS_REGION.ENTERTIME
  is '????????';
comment on column WLW116120.GPS_REGION.UPTIME
  is '????????????';
comment on column WLW116120.GPS_REGION.ENTERUSR
  is '??????';
comment on column WLW116120.GPS_REGION.UPUSR
  is '??????';
comment on column WLW116120.GPS_REGION.SHAPE
  is '1??????2????';
comment on column WLW116120.GPS_REGION.VALID
  is '1??????2????';
comment on column WLW116120.GPS_REGION.BEGIN_TIME
  is '????????????';
comment on column WLW116120.GPS_REGION.END_TIME
  is '????????????';

prompt
prompt Creating table GPS_SHOOT
prompt ========================
prompt
create table WLW116120.GPS_SHOOT
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        CHAR(1),
  STATE      INTEGER,
  INTIME     DATE
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
create index WLW116120.GPS_SHOOT_REGCODE_INDEX on WLW116120.GPS_SHOOT (REGCODE)
  tablespace WLW120
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table GPS_SYS_WARN
prompt ===========================
prompt
create table WLW116120.GPS_SYS_WARN
(
  WARNID     VARCHAR2(36) not null,
  WARNTIME   DATE default sysdate,
  REGAINTIME DATE,
  V_PROVIDER VARCHAR2(32),
  CONTENT    VARCHAR2(500),
  READ_TIME  DATE,
  STATE      VARCHAR2(2) default '0',
  READ_IP    VARCHAR2(32),
  STATE_REG  VARCHAR2(2) default '0'
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column WLW116120.GPS_SYS_WARN.STATE_REG
  is '数据是否恢复传输(0未恢复,1已恢复,2程序重启)';

prompt
prompt Creating table GPS_TRACE_20121016
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121016
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-16 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-16 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-16 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-16 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-16 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-16 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-16 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-16 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-16 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-16 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-16 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-16 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-16 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-16 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-16 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-16 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-16 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-16 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-16 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-16 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-16 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-16 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-16 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-17 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121016_REG on WLW116120.GPS_TRACE_20121016 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121017
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121017
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-17 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-17 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-17 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-17 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-17 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-17 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-17 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-17 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-17 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-17 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-17 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-17 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-17 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-17 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-17 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-17 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-17 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-17 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-17 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-17 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-17 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-17 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-17 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-18 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121017_REG on WLW116120.GPS_TRACE_20121017 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121018
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121018
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-18 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-18 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-18 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-18 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-18 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-18 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-18 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-18 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-18 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-18 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-18 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-18 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-18 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-18 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-18 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-18 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-18 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-18 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-18 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-18 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-18 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-18 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-18 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-19 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121018_REG on WLW116120.GPS_TRACE_20121018 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121019
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121019
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-19 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-19 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-19 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-19 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-19 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-19 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-19 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-19 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-19 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-19 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-19 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-19 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-19 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-19 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-19 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-19 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-19 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-19 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-19 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-19 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-19 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-19 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-19 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-20 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121019_REG on WLW116120.GPS_TRACE_20121019 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121020
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121020
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-20 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-20 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-20 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-20 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-20 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-20 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-20 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-20 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-20 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-20 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-20 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-20 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-20 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-20 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-20 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-20 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-20 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-20 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-20 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-20 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-20 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-20 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-20 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-21 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121020_REG on WLW116120.GPS_TRACE_20121020 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121021
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121021
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-21 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-21 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-21 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-21 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-21 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-21 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-21 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-21 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-21 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-21 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-21 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-21 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-21 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-21 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-21 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-21 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-21 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-21 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-21 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-21 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-21 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-21 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-21 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-22 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121021_REG on WLW116120.GPS_TRACE_20121021 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121022
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121022
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-22 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-22 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-22 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-22 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-22 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-22 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-22 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-22 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-22 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-22 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-22 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-22 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-22 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-22 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-22 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-22 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-22 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-22 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-22 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-22 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-22 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-22 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-22 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-23 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121022_REG on WLW116120.GPS_TRACE_20121022 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121023
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121023
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-23 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-23 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-23 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-23 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-23 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-23 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-23 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-23 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-23 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-23 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-23 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-23 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-23 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-23 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-23 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-23 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-23 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-23 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-23 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-23 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-23 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-23 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-23 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-24 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121023_REG on WLW116120.GPS_TRACE_20121023 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121024
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121024
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-24 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-24 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-24 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-24 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-24 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-24 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-24 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-24 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-24 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-24 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-24 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-24 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-24 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-24 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-24 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-24 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-24 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-24 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-24 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-24 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-24 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-24 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-24 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-25 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121024_REG on WLW116120.GPS_TRACE_20121024 (REGCODE);

prompt
prompt Creating table GPS_TRACE_20121025
prompt =================================
prompt
create table WLW116120.GPS_TRACE_20121025
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)
partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' 2012-10-25 01:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_01
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_02 values less than (TO_DATE(' 2012-10-25 02:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_02
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_03 values less than (TO_DATE(' 2012-10-25 03:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_03
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_04 values less than (TO_DATE(' 2012-10-25 04:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_04
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_05 values less than (TO_DATE(' 2012-10-25 05:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_05
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_06 values less than (TO_DATE(' 2012-10-25 06:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_06
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_07 values less than (TO_DATE(' 2012-10-25 07:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_07
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_08 values less than (TO_DATE(' 2012-10-25 08:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_08
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_09 values less than (TO_DATE(' 2012-10-25 09:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_09
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_10 values less than (TO_DATE(' 2012-10-25 10:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_10
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_11 values less than (TO_DATE(' 2012-10-25 11:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_11
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_12 values less than (TO_DATE(' 2012-10-25 12:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_12
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_13 values less than (TO_DATE(' 2012-10-25 13:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_13
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_14 values less than (TO_DATE(' 2012-10-25 14:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_14
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_15 values less than (TO_DATE(' 2012-10-25 15:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_15
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_16 values less than (TO_DATE(' 2012-10-25 16:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_16
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_17 values less than (TO_DATE(' 2012-10-25 17:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_17
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_18 values less than (TO_DATE(' 2012-10-25 18:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_18
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_19 values less than (TO_DATE(' 2012-10-25 19:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_19
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_20 values less than (TO_DATE(' 2012-10-25 20:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_20
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_21 values less than (TO_DATE(' 2012-10-25 21:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_21
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_22 values less than (TO_DATE(' 2012-10-25 22:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_22
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_23 values less than (TO_DATE(' 2012-10-25 23:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_23
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_24 values less than (TO_DATE(' 2012-10-26 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
    tablespace WLW120_24
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    ),
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
    pctfree 10
    initrans 1
    maxtrans 255
    storage
    (
      initial 64K
      next 1M
      minextents 1
      maxextents unlimited
    )
)
;
create index WLW116120.IDX_GPS_20121025_REG on WLW116120.GPS_TRACE_20121025 (REGCODE);

prompt
prompt Creating table GPS_VHLCTRL
prompt ==========================
prompt
create table WLW116120.GPS_VHLCTRL
(
  CTRLID       VARCHAR2(32),
  USERID       VARCHAR2(32),
  CTR_TIME     DATE,
  LONGTITUDE   NUMBER,
  LATITUDE     NUMBER,
  GPSTIME      DATE,
  SPEED        INTEGER,
  DIRECTION    INTEGER,
  REGCODE      VARCHAR2(16),
  V_PROVIDER   INTEGER,
  MARGIN_VALUE VARCHAR2(1),
  MONITORTEL   VARCHAR2(20),
  MARGIN_TYPE  VARCHAR2(1)
)
tablespace WLW120
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on table WLW116120.GPS_VHLCTRL
  is 'GPS车辆管控信息';
comment on column WLW116120.GPS_VHLCTRL.CTRLID
  is '管控信息ID';
comment on column WLW116120.GPS_VHLCTRL.USERID
  is '操作用户ID ';
comment on column WLW116120.GPS_VHLCTRL.CTR_TIME
  is '操作时间';
comment on column WLW116120.GPS_VHLCTRL.LONGTITUDE
  is '经度';
comment on column WLW116120.GPS_VHLCTRL.LATITUDE
  is '纬度';
comment on column WLW116120.GPS_VHLCTRL.GPSTIME
  is 'GPS时间';
comment on column WLW116120.GPS_VHLCTRL.SPEED
  is '速度';
comment on column WLW116120.GPS_VHLCTRL.DIRECTION
  is '方向';
comment on column WLW116120.GPS_VHLCTRL.REGCODE
  is '车辆';
comment on column WLW116120.GPS_VHLCTRL.V_PROVIDER
  is '车辆运营商';
comment on column WLW116120.GPS_VHLCTRL.MARGIN_VALUE
  is '标志位数值：(2，监听，1断，0开)';
comment on column WLW116120.GPS_VHLCTRL.MONITORTEL
  is '监听号码';
comment on column WLW116120.GPS_VHLCTRL.MARGIN_TYPE
  is '标志位类型： 0监听，1断油，2断电';

prompt
prompt Creating sequence SEQ_GPS_TRACEID
prompt =================================
prompt
create sequence WLW116120.SEQ_GPS_TRACEID
minvalue 1
maxvalue 999999999999999999999999999
start with 103962001
increment by 1
cache 20
cycle;

prompt
prompt Creating synonym GPS_COMPANYS
prompt =============================
prompt
create or replace synonym WLW116120.GPS_COMPANYS
  for JCSJ116.GPS_COMPANYS;

prompt
prompt Creating synonym GPS_DRIVERS
prompt ============================
prompt
create or replace synonym WLW116120.GPS_DRIVERS
  for JCSJ116.GPS_DRIVERS;

prompt
prompt Creating synonym GPS_VEHICLE
prompt ============================
prompt
create or replace synonym WLW116120.GPS_VEHICLE
  for JCSJ116.GPS_VEHICLE;

prompt
prompt Creating procedure PRC_COUNT_DAY
prompt ================================
prompt
create or replace procedure wlw116120.PRC_COUNT_DAY is
  insert_data    varchar2(4096);
  delete_data    varchar2(1024);
  table_name varchar2(50);
  COUNTDATE  varchar2(50);
  num integer;

begin
  COUNTDATE  := to_char(sysdate - 2, 'yyyy-MM-dd');
  table_name := concat('GPS_TRACE_', to_char(sysdate - 2, 'yyyyMMdd'));

  ---删除前天的数据
  delete_data := 'delete from gps_day_counts where COUNTDATE= to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || ') ';
  execute immediate delete_data;
  commit;

  ---统计前天的数据
  insert_data := '
  insert into gps_day_counts(PROVIDER,COUNTDATE,CHUZU,CHUZUCOUNT,ZULIN,ZULINCOUNT,GONGJIAO,GONGJIAOCOUNT,LVYOU,LVYOUCOUNT,KEYUN,KEYUNCOUNT,XIAOCHE,XIAOCHECOUNT,HUAWEICHE,HUAWEICHECOUNT,QITA,QITACOUNT)
select t.v_provider,to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || '),
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.num,0)) as chuzu,
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.allnum,0)) as chuzucount,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.num,0)) as zulin,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.allnum,0)) as zulincount,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.num,0)) as gongjiao,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.allnum,0)) as gongjiaocount,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.num,0)) as lvyou,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.allnum,0)) as lvyoucount,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.num,0)) as keyun,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.allnum,0)) as keyuncount,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.num,0)) as xiaoche,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.allnum,0)) as xiaochecount,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.num,0)) as huawei,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.allnum,0)) as huaweicount,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.num,0)) as qita,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.allnum,0)) as qitacount
from (select t1.v_provider,t1.style,t1.num as allnum,t2.num as num from (SELECT count(*) num, b.v_provider,b.v_style style
      FROM gps_vehicle b group by b.v_provider,b.v_style) t1 left join (SELECT count(*) num,v_provider,style
   from (select distinct g.regcode,v.v_provider,v.v_style style
           from ' || table_name || ' g
           left join gps_vehicle v on g.regcode = v.regcode)
group by v_provider,style) t2 on (t1.v_provider=t2.v_provider and t1.style=t2.style)) t group by t.v_provider';
  execute immediate insert_data;
  commit;

   ---统计昨天的数据
  COUNTDATE  := to_char(sysdate - 1, 'yyyy-MM-dd');
  table_name := concat('GPS_TRACE_', to_char(sysdate - 1, 'yyyyMMdd'));
  insert_data := '
  insert into gps_day_counts(PROVIDER,COUNTDATE,CHUZU,CHUZUCOUNT,ZULIN,ZULINCOUNT,GONGJIAO,GONGJIAOCOUNT,LVYOU,LVYOUCOUNT,KEYUN,KEYUNCOUNT,XIAOCHE,XIAOCHECOUNT,HUAWEICHE,HUAWEICHECOUNT,QITA,QITACOUNT)
select t.v_provider,to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || '),
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.num,0)) as chuzu,
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.allnum,0)) as chuzucount,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.num,0)) as zulin,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.allnum,0)) as zulincount,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.num,0)) as gongjiao,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.allnum,0)) as gongjiaocount,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.num,0)) as lvyou,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.allnum,0)) as lvyoucount,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.num,0)) as keyun,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.allnum,0)) as keyuncount,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.num,0)) as xiaoche,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.allnum,0)) as xiaochecount,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.num,0)) as huawei,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.allnum,0)) as huaweicount,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.num,0)) as qita,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.allnum,0)) as qitacount
from (select t1.v_provider,t1.style,t1.num as allnum,t2.num as num from (SELECT count(*) num, b.v_provider,b.v_style style
      FROM gps_vehicle b group by b.v_provider,b.v_style) t1 left join (SELECT count(*) num,v_provider,style
   from (select distinct g.regcode,v.v_provider,v.v_style style
           from ' || table_name || ' g
           left join gps_vehicle v on g.regcode = v.regcode)
group by v_provider,style) t2 on (t1.v_provider=t2.v_provider and t1.style=t2.style)) t group by t.v_provider';
  execute immediate insert_data;
  commit;

    COUNTDATE := to_char(sysdate - 1, 'yyyy-MM-dd');
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=1;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_1' || '''' || ',1,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=2;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_2' || '''' || ',2,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=3;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_3' || '''' || ',3,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=4;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_4' || '''' || ',4,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=5;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_5' || '''' || ',5,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=6;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_6' || '''' || ',6,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;

    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=8;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_8' || '''' || ',8,' || '''' || COUNTDATE ||  '该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
       commit;
     end if;
end PRC_COUNT_DAY;
/

prompt
prompt Creating procedure PRC_CREATE_TB_DAY
prompt ====================================
prompt
create or replace procedure wlw116120.PRC_CREATE_TB_DAY
is
yes_b_date varchar2(256);
yes_date varchar2(256);
cur_date varchar2(256);
tom_date varchar2(256);
tom_a_date varchar2(256);

create_table varchar(4096);
create_index varchar(4096);

create_trig varchar2(256);
delete_trig varchar2(256);

insert_data varchar2(1024);
delete_data varchar2(1024);


begin
yes_b_date := to_char( sysdate - 2,'yyyymmdd');
yes_date := to_char( sysdate - 1,'yyyymmdd');
cur_date := to_char( sysdate ,'yyyymmdd');
tom_date := to_char( sysdate + 1,'yyyymmdd');
tom_a_date := to_char( sysdate + 2,'yyyymmdd');


---????????????
create_table := 'create table gps_trace_' || tom_date || '(
    REGCODE    VARCHAR2(16),
	GPSTIME    DATE,
  	LONGTITUDE NUMBER,
  	LATITUDE   NUMBER,
  	SPEED      INTEGER,
  	DIRECTION  INTEGER,
  	EFF        VARCHAR2(4),
  	STATE      INTEGER,
  	TRACEID    VARCHAR2(32),
  	ENTERTIME  DATE default sysdate,
  	V_PROVIDER VARCHAR2(2),
  	EMPTY      VARCHAR2(1),
  	EXIGENCY   VARCHAR2(1),
  	LARCENY    VARCHAR2(1)
)partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' || '''' || tom_date || '010000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_01,
  partition PART_02 values less than (TO_DATE(' || '''' || tom_date || '020000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_02,
  partition PART_03 values less than (TO_DATE(' || '''' || tom_date || '030000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_03,
  partition PART_04 values less than (TO_DATE(' || '''' || tom_date || '040000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_04,
  partition PART_05 values less than (TO_DATE(' || '''' || tom_date || '050000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_05,
  partition PART_06 values less than (TO_DATE(' || '''' || tom_date || '060000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_06,
  partition PART_07 values less than (TO_DATE(' || '''' || tom_date || '070000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_07,
  partition PART_08 values less than (TO_DATE(' || '''' || tom_date || '080000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_08,
  partition PART_09 values less than (TO_DATE(' || '''' || tom_date || '090000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_09,
  partition PART_10 values less than (TO_DATE(' || '''' || tom_date || '100000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_10,
  partition PART_11 values less than (TO_DATE(' || '''' || tom_date || '110000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_11,
  partition PART_12 values less than (TO_DATE(' || '''' || tom_date || '120000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_12,
  partition PART_13 values less than (TO_DATE(' || '''' || tom_date || '130000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_13,
  partition PART_14 values less than (TO_DATE(' || '''' || tom_date || '140000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_14,
  partition PART_15 values less than (TO_DATE(' || '''' || tom_date || '150000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_15,
  partition PART_16 values less than (TO_DATE(' || '''' || tom_date || '160000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_16,
  partition PART_17 values less than (TO_DATE(' || '''' || tom_date || '170000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_17,
  partition PART_18 values less than (TO_DATE(' || '''' || tom_date || '180000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_18,
  partition PART_19 values less than (TO_DATE(' || '''' || tom_date || '190000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_19,
  partition PART_20 values less than (TO_DATE(' || '''' || tom_date || '200000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_20,
  partition PART_21 values less than (TO_DATE(' || '''' || tom_date || '210000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_21,
  partition PART_22 values less than (TO_DATE(' || '''' || tom_date || '220000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_22,
  partition PART_23 values less than (TO_DATE(' || '''' || tom_date || '230000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_23,
  partition PART_24 values less than (TO_DATE(' || '''' || tom_a_date || '000000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW120_24,
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW120_25
)';
execute immediate create_table;

---??????????
create_index := 'create index idx_gps_' || tom_date || '_REG on gps_trace_'|| tom_date ||' (REGCODE) local
(
partition idx_01 tablespace WLW120_01 ,
partition idx_02 tablespace WLW120_02 ,
partition idx_03 tablespace WLW120_03 ,
partition idx_04 tablespace WLW120_04 ,
partition idx_05 tablespace WLW120_05 ,
partition idx_06 tablespace WLW120_06 ,
partition idx_07 tablespace WLW120_07 ,
partition idx_08 tablespace WLW120_08 ,
partition idx_09 tablespace WLW120_09 ,
partition idx_10 tablespace WLW120_10 ,
partition idx_11 tablespace WLW120_11 ,
partition idx_12 tablespace WLW120_12 ,
partition idx_13 tablespace WLW120_13 ,
partition idx_14 tablespace WLW120_14 ,
partition idx_15 tablespace WLW120_15 ,
partition idx_16 tablespace WLW120_16 ,
partition idx_17 tablespace WLW120_17 ,
partition idx_18 tablespace WLW120_18 ,
partition idx_19 tablespace WLW120_19 ,
partition idx_20 tablespace WLW120_20 ,
partition idx_21 tablespace WLW120_21 ,
partition idx_22 tablespace WLW120_22 ,
partition idx_23 tablespace WLW120_23 ,
partition idx_24 tablespace WLW120_24 ,
partition idx_25 tablespace WLW120_25
)';
execute immediate create_index;

---??????????????traceid??trigger
create_trig := 'create or replace trigger tri_bf_gps_trace_' || tom_date || '  before insert on  gps_trace_'|| tom_date || ' for  each row
begin
select   concat( ' || '''' || 'GPS_' || '''' || ',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;';
execute immediate create_trig;

---????????????trigger
delete_trig := 'drop trigger tri_bf_gps_trace_'|| yes_b_date ;
execute immediate delete_trig;

---????gpstime????????????????????????????
insert_data :='INSERT INTO gps_trace_' ||  cur_date  ||
		      ' SELECT * FROM  gps_trace_'|| yes_date  ||
		      ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || cur_date || '''';
execute immediate insert_data;
commit;

delete_data := 'DELETE FROM gps_trace_'|| yes_date ||
               ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || cur_date || '''';
execute immediate delete_data;
commit;

---????gpstime????????????????????????????

insert_data :='INSERT INTO gps_trace_' ||  yes_b_date  ||
		      ' SELECT * FROM  gps_trace_'|| yes_date  ||
		      ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || yes_b_date || '''';
execute immediate insert_data;
commit;

delete_data := 'DELETE FROM gps_trace_'|| yes_date ||
               ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || yes_b_date || '''';
execute immediate delete_data;
commit;

end PRC_CREATE_TB_DAY;
/

prompt
prompt Creating trigger TRI_BF_GPS_TRACE_20121023
prompt ==========================================
prompt
create or replace trigger WLW116120.tri_bf_gps_trace_20121023  before insert on  gps_trace_20121023 for  each row
begin
select   concat( 'GPS_',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;
/

prompt
prompt Creating trigger TRI_BF_GPS_TRACE_20121024
prompt ==========================================
prompt
create or replace trigger WLW116120.tri_bf_gps_trace_20121024  before insert on  gps_trace_20121024 for  each row
begin
select   concat( 'GPS_',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;
/

prompt
prompt Creating trigger TRI_BF_GPS_TRACE_20121025
prompt ==========================================
prompt
create or replace trigger WLW116120.tri_bf_gps_trace_20121025  before insert on  gps_trace_20121025 for  each row
begin
select   concat( 'GPS_',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;
/


spool off
