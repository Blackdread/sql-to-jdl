CREATE TABLE `invoices`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT,
    `name`      varchar(100) NOT NULL,
    `address`   varchar(100) NOT NULL,
    `parent_id` int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `parent_id` (`parent_id`),
    CONSTRAINT `invoices_FK` FOREIGN KEY (`parent_id`) REFERENCES `invoices` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
