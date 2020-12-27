CREATE TABLE IF NOT EXISTS `all_type`
(
    `my_id`   varchar(25) NOT NULL,
    `my_json` json        NOT NULL,
    `my_date` datetime    NOT NULL,
    `my_text` text        NOT NULL,
    PRIMARY KEY (`my_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
