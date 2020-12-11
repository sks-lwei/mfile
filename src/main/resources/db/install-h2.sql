create table "system_config"
(
    "id"     BIGINT auto_increment,
    "k"      VARCHAR(50) default NULL,
    "value"  VARCHAR(50) default NULL,
    "remark" VARCHAR(50) default NULL,
    constraint SYSTEM_CONFIG_PK
        primary key ("id")
);

create table "driver_config"
(
    "id"                            BIGINT auto_increment,
    "enable"                        BIT         default TRUE,
    "name"                          VARCHAR(50) default NULL,
    "enable_cache"                  BIT         default NULL,
    "auto_refresh_cache"            BIT         default NULL,
    "type"                          VARCHAR(50) default NULL,
    "search_enable"                 BIT         default NULL,
    "search_ignore_case"            BIT         default NULL,
    "search_contain_encrypted_file" BIT         default NULL,
    "order_num"                     INTEGER     default NULL,
    constraint DRIVER_CONFIG_PK
        primary key ("id")
);

create table "storage_config"
(
    "id"      BIGINT auto_increment,
    "type"    VARCHAR(50) default NULL,
    "k"       VARCHAR(50) default NULL,
    "title"   VARCHAR(50) default NULL,
    "value"   VARCHAR(50) default NULL,
    "drive_id" INTEGER     default NULL,
    constraint STORAGE_CONFIG_PK
        primary key ("id")
);

create table "filter_config"
(
    "id"         BIGINT auto_increment,
    "drive_id"   BIGINT not null,
    "expression" VARCHAR(50) default NULL,
    constraint FILTER_CONFIG_PK
        primary key ("id")
);
