CREATE TABLE IF NOT EXISTS `all_type`
(
    `my_id`          varchar(25)  NOT NULL,
    `my_varchar_50`  varchar(50)  NOT NULL comment 'super varchar 50',
    `my_varchar_512` varchar(512) NOT NULL comment 'super varchar 512',
    `my_int`         int          NOT NULL,
    `my_bigint`      bigint       NOT NULL comment 'super big int',
    `my_date`        datetime     NOT NULL,
    `my_text`        text         NOT NULL,
    PRIMARY KEY (`my_id`)
) comment 'my comment for table of all type'
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4;

# relation of one to one


# relation of one to one with map id


# relation of many to one


# relation of many to many


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
