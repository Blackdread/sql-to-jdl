CREATE TABLE IF NOT EXISTS `all_type`
(
    `my_id`              varchar(25)                                  NOT NULL,
    `my_int`             int(11)     default 55                       NOT NULL,
    `my_int_null`        int,
    `my_varchar_50`      varchar(50) default 'default of varchar 50'  NOT NULL,
    `my_varchar_50_null` varchar(50),
    `my_varchar_512`     varchar(512)                                 NOT NULL COMMENT 'super varchar 512',
    `my_tinytext`        tinytext                                     NOT NULL,
    `my_mediumtext`      mediumtext                                   NOT NULL,
    `my_longtext`        longtext                                     NOT NULL,
    `my_text`            text                                         NOT NULL,
    `my_char`            char(5)                                      NOT NULL,
    `my_date`            date                                         NOT NULL,
    `my_datetime`        datetime                                     NOT NULL,
    `my_timestamp`       timestamp                                    NOT NULL,
    `my_time`            time                                         NOT NULL,
    `my_year`            year(4)                                      NOT NULL,
    `my_bigint`          bigint(20)                                   NOT NULL,
    `my_tinyint`         tinyint(4)                                   NOT NULL,
    `my_smallint`        smallint(6)                                  NOT NULL COMMENT 'small int comment',
    `my_mediumint`       mediumint(9)                                 NOT NULL,
    `my_decimal`         decimal(19, 5)                               NOT NULL,
    `my_float`           float                                        NOT NULL,
    `my_double`          double                                       NOT NULL,
    `my_enum_sql`        enum ('value_one','value_two','value_three') NOT NULL,
    `my_set`             set ('set_one','set_two','set_three')        NOT NULL,
    `my_bit`             bit(1)                                       NOT NULL,
    `my_bool`            tinyint(1)                                   NOT NULL,
#     `my_binary`          binary(200)                                  NOT NULL, TODO support later if needed
#     `my_varbinary`       varbinary(800)                               NOT NULL, TODO support later if needed
    `my_json`            json                                         NOT NULL,
    `my_geometry`        geometry                                     NOT NULL,
    `my_tinyblob`        tinyblob                                     NOT NULL,
    `my_mediumblob`      mediumblob                                   NOT NULL,
    `my_blob`            blob                                         NOT NULL COMMENT 'blob comment',
    `my_longblob`        longblob                                     NOT NULL,
    `my_tinyint_8`       tinyint(8)                                   NOT NULL,
    PRIMARY KEY (`my_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='my table comment';


# relation of one to one
CREATE TABLE IF NOT EXISTS `one_to_one_child`
(
    `id`    VARCHAR(25) NOT NULL,
    `name`  VARCHAR(45) NOT NULL,
    `other` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `one_to_one_main`
(
    `id`                  VARCHAR(25) NOT NULL,
    `name`                VARCHAR(45) NOT NULL,
    `other`               VARCHAR(45) NOT NULL,
    `one_to_one_child_id` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_one_to_one_main_one_to_one_child1_idx` (`one_to_one_child_id` ASC),
    UNIQUE INDEX `one_to_one_child_id_UNIQUE` (`one_to_one_child_id` ASC),
    CONSTRAINT `fk_one_to_one_main_one_to_one_child1`
        FOREIGN KEY (`one_to_one_child_id`)
            REFERENCES `one_to_one_child` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB COMMENT 'one_to_one_main comment';


# relation of one to one with map id

CREATE TABLE IF NOT EXISTS `one_to_one_main_map`
(
    `id`    VARCHAR(25) NOT NULL,
    `name`  VARCHAR(45) NOT NULL,
    `other` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT 'one_to_one_main_map comment';

CREATE TABLE IF NOT EXISTS `one_to_one_child_map`
(
    `one_to_one_main_map_id` VARCHAR(25) NOT NULL,
    `name`                   VARCHAR(45) NOT NULL,
    PRIMARY KEY (`one_to_one_main_map_id`),
    INDEX `fk_one_to_one_child_map_one_to_one_main_map1_idx` (`one_to_one_main_map_id` ASC),
    CONSTRAINT `fk_one_to_one_child_map_one_to_one_main_map1`
        FOREIGN KEY (`one_to_one_main_map_id`)
            REFERENCES `one_to_one_main_map` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB;


# relation of many to one

CREATE TABLE IF NOT EXISTS `many_to_one_child`
(
    `id`    VARCHAR(25) NOT NULL,
    `name`  VARCHAR(45) NOT NULL,
    `other` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT 'many_to_one_child comment';

CREATE TABLE IF NOT EXISTS `many_to_one_main`
(
    `id`                   VARCHAR(25) NOT NULL,
    `name`                 VARCHAR(45) NOT NULL,
    `other`                VARCHAR(45) NOT NULL,
    `many_to_one_child_id` VARCHAR(25) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_many_to_one_main_many_to_one_child1_idx` (`many_to_one_child_id` ASC),
    CONSTRAINT `fk_many_to_one_main_many_to_one_child1`
        FOREIGN KEY (`many_to_one_child_id`)
            REFERENCES `many_to_one_child` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB COMMENT 'many_to_one_main comment';


# relation of many to many

CREATE TABLE IF NOT EXISTS `parent`
(
    `id`    VARCHAR(25) NOT NULL,
    `name`  VARCHAR(45) NOT NULL,
    `other` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `child`
(
    `id`    VARCHAR(25) NOT NULL,
    `name`  VARCHAR(45) NOT NULL,
    `other` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `parent_has_child`
(
    `parent_id` VARCHAR(25) NOT NULL,
    `child_id`  VARCHAR(25) NOT NULL,
    PRIMARY KEY (`parent_id`, `child_id`),
    INDEX `fk_parent_has_child_child1_idx` (`child_id` ASC),
    INDEX `fk_parent_has_child_parent1_idx` (`parent_id` ASC),
    CONSTRAINT `fk_parent_has_child_parent1`
        FOREIGN KEY (`parent_id`)
            REFERENCES `parent` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_parent_has_child_child1`
        FOREIGN KEY (`child_id`)
            REFERENCES `child` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB COMMENT 'parent_has_child comment';


# relation to an enum table

CREATE TABLE IF NOT EXISTS `city_status`
(
    `id`   VARCHAR(25)  NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `na_UNIQUE` (`name` ASC)
) ENGINE = InnoDB COMMENT 'city_status comment';

INSERT INTO `city_status` (`id`, `name`)
VALUES ('1', 'MY_CITY_ONE'),
       ('2', 'MY_city_two_lower');

CREATE TABLE IF NOT EXISTS `city`
(
    `id`             VARCHAR(25)                   NOT NULL,
    `name`           VARCHAR(45)                   NULL,
    `city_type`      ENUM ('small', 'big', 'mega') NULL comment 'native enum comment',
    `city_status_id` VARCHAR(25)                   NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_city_city_status1_idx` (`city_status_id` ASC),
    CONSTRAINT `fk_city_city_status1`
        FOREIGN KEY (`city_status_id`)
            REFERENCES `city_status` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
) ENGINE = InnoDB;


# Logic to handle views

CREATE TABLE IF NOT EXISTS `order_details`
(
    `order_number`     varchar(255)   NOT NULL,
    `product_code`     varchar(50)    NOT NULL,
    `quantity_ordered` int            NOT NULL,
    `price_each`       decimal(19, 5) NOT NULL,
    PRIMARY KEY (`order_number`, `product_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE VIEW sale_per_order AS
SELECT order_number,
       SUM(quantity_ordered * price_each) total
FROM order_details
GROUP by order_number
ORDER BY total DESC;
