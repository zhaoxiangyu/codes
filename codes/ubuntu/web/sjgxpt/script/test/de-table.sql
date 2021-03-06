  CREATE TABLE "DGAP_DW"."T_COUNTRY" 
   (	"ID" VARCHAR2(64 BYTE), 
	"ALERT_TYPE" VARCHAR2(20 BYTE) DEFAULT NULL, 
	"TARGET_ID" VARCHAR2(64 BYTE), 
	"DESCRIPTION" VARCHAR2(100 BYTE), 
	"THRESHOLD" NUMBER, 
	"NEED_SEND_ALERT" VARCHAR2(1 BYTE) DEFAULT 'Y', 
	"CREATE_BY" VARCHAR2(64 BYTE), 
	"CREATE_TIME" DATE DEFAULT SYSDATE, 
	"UPDATE_BY" VARCHAR2(64 BYTE), 
	"UPDATE_TIME" DATE DEFAULT SYSDATE, 
	"DEL_FLAG" VARCHAR2(10 BYTE) DEFAULT 'N' 
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
 

   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."ID" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."ALERT_TYPE" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."TARGET_ID" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."DESCRIPTION" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."THRESHOLD" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."NEED_SEND_ALERT" IS '非空，默认值为Y （可填的Y或者N）';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."CREATE_BY" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."CREATE_TIME" IS '非空，默认值当前时间';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."UPDATE_BY" IS '非空，无默认值';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."UPDATE_TIME" IS '非空，默认值当前时间';
 
   COMMENT ON COLUMN "DGAP_DW"."T_COUNTRY"."DEL_FLAG" IS '非空，默认值为N
N: 未删除
Y: 已删除
';
 
   COMMENT ON TABLE "DGAP_DW"."T_COUNTRY"  IS '配置需要监控的预警项，包括预警目标、类型、状态等内容'
