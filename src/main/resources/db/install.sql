DROP TABLE IF EXISTS driver_config;

CREATE TABLE driver_config
(
    id BIGINT(20) NOT NULL,
    enable BIT(1) NULL DEFAULT NULL,
    name VARCHAR(50) NULL DEFAULT NULL,
    enable_cache BIT(1) NULL DEFAULT NULL,
    auto_refresh_cache BIT(1) NULL DEFAULT NULL,
    type VARCHAR(50) NULL DEFAULT NULL,
    search_enable BIT(1) NULL DEFAULT NULL,
    search_ignore_case BIT(1) NULL DEFAULT NULL,
    search_contain_encrypted_file BIT(1) NULL DEFAULT NULL,
    order_num INTEGER(5) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);



DROP TABLE IF EXISTS filter_config;

CREATE TABLE filter_config
(
    id BIGINT(20) NOT NULL,
    drive_id BIGINT(20) NOT NULL,
    expression VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);



DROP TABLE IF EXISTS storage_config;

CREATE TABLE storage_config
(
    id BIGINT(20) NOT NULL,
    type VARCHAR(50) NULL DEFAULT NULL,
    k VARCHAR(50) NULL DEFAULT NULL,
    title VARCHAR(50) NULL DEFAULT NULL,
    value VARCHAR(50) NULL DEFAULT NULL,
    driveId INTEGER(20) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);



DROP TABLE IF EXISTS system_config;

CREATE TABLE system_config
(
    id BIGINT(20) NOT NULL,
    k VARCHAR(50) NULL DEFAULT NULL,
    value VARCHAR(50) NULL DEFAULT NULL,
    remark VARCHAR(50) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);
