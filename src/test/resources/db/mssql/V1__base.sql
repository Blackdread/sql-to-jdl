-- TODO add more types

CREATE TABLE all_type
(
    my_id               nvarchar(25)                                NOT NULL
        constraint all_type_pk
            primary key nonclustered,
    my_uniqueidentifier uniqueidentifier                            NOT NULL,
    my_int              int         default 55                      NOT NULL,
    my_int_null         int,
    my_varchar_50       varchar(50) default 'default of varchar 50' NOT NULL,
    my_varchar_50_null  nvarchar(50),
    my_varchar_512      nvarchar(512)                               NOT NULL,
--     my_tinytext        tinytext         NOT NULL,
--     my_mediumtext      mediumtext       NOT NULL,
--     my_longtext        longtext         NOT NULL,
    my_text             text                                        NOT NULL,
    my_char             char(5)                                     NOT NULL,
    my_date             date                                        NOT NULL,
    my_datetime         datetime                                    NOT NULL,
    my_datetimeoffset   datetimeoffset(6)                           NOT NULL,
    my_timestamp        timestamp                                   NOT NULL,
    my_time             time                                        NOT NULL,
--     my_year            year(4)          NOT NULL,
    my_bigint           bigint                                      NOT NULL,
    my_tinyint          tinyint                                     NOT NULL,
    my_smallint         smallint                                    NOT NULL,
--     my_mediumint       mediumint         NOT NULL,
    my_decimal          decimal(19, 5)                              NOT NULL,
    my_float            float                                       NOT NULL,
--     my_double          double            NOT NULL,
--     my_enum_sql        enum('value_one', 'value_two', 'value_three') NOT NULL,
--     my_set             set ('set_one','set_two','set_three')        NOT NULL,
    my_bit              bit                                         NOT NULL,
    my_bool             tinyint                                     NOT NULL,
--      my_binary          binary(200)                                  NOT NULL, TODO support later if needed
--      my_varbinary       varbinary(800)                               NOT NULL, TODO support later if needed
--     my_json            json              NOT NULL,
--     my_geometry        geometry          NOT NULL,
--     my_tinyblob        tinyblob          NOT NULL,
--     my_mediumblob      mediumblob        NOT NULL,
--     my_blob            blob              NOT NULL COMMENT 'blob comment',
--     my_longblob        longblob          NOT NULL,
--     my_tinyint_8       tinyint(8) NOT    NULL,
)

-- exec sp_addextendedproperty 'MS_Description', 'my_varchar_50 comment', 'SCHEMA', 'test', 'TABLE', 'all_type', 'COLUMN', 'my_varchar_50'
-- go

-- exec sp_addextendedproperty 'MS_Description', 'my_bigint comment', 'SCHEMA', 'test', 'TABLE', 'all_type', 'COLUMN', 'my_bigint'
-- go

-- relation of one to one

create table one_to_one_child
(
    id    nvarchar(25) not null
        constraint one_to_one_child_pk
            primary key nonclustered,
    name  nvarchar(45) not null,
    other nvarchar(45) not null,
)
go

create table one_to_one_main
(
    id                  nvarchar(25) not null
        constraint one_to_one_pk
            primary key nonclustered,
    name                nvarchar(45) not null,
    other               nvarchar(45) not null,
    one_to_one_child_id nvarchar(25)
        constraint one_to_one_main_one_to_one_child_id_fk
            references one_to_one_child
)
go

create unique index one_to_one_main_one_to_one_child_id_uindex
    on one_to_one_main (one_to_one_child_id)
go

-- exec sp_addextendedproperty 'MS_Description', 'one_to_one_main comment', 'SCHEMA', 'test', 'TABLE', 'one_to_one_main'
-- go

--  relation of one to one with map id

create table one_to_one_main_map
(
    id    nvarchar(25) not null
        constraint one_to_one_main_map_pk
            primary key nonclustered,
    name  nvarchar(45) not null,
    other nvarchar(45) not null
)
go

-- exec sp_addextendedproperty 'MS_Description', 'one_to_one_main_map comment', 'SCHEMA', 'test', 'TABLE', 'one_to_one_main_map'
-- go

create table one_to_one_child_map
(
    one_to_one_main_map_id nvarchar(25) not null
        constraint one_to_one_child_map_pk
            primary key nonclustered
        constraint one_to_one_child_map_one_to_one_main_map_id_fk
            references one_to_one_main_map,
    name                   nvarchar(45) not null,
    other                  nvarchar(45) not null
)
go

-- relation of many to one

create table many_to_one_child
(
    id    nvarchar(25) not null
        constraint many_to_one_child_pk
            primary key nonclustered,
    name  nvarchar(45) not null,
    other nvarchar(45) not null,
)
go

create table many_to_one_main
(
    id                   nvarchar(25) not null
        constraint many_to_one_main_pk
            primary key nonclustered,
    name                 nvarchar(45) not null,
    other                nvarchar(45) not null,
    many_to_one_child_id nvarchar(25)
        constraint many_to_one_main_many_to_one_child_fk
            references many_to_one_child
)
go

-- exec sp_addextendedproperty 'MS_Description', 'many_to_one_main comment', 'SCHEMA', 'test', 'TABLE', 'many_to_one_main'
-- go


-- relation of many to many

create table parent
(
    id    nvarchar(25)                           not null
        constraint parent_pk
            primary key nonclustered,
    name  nvarchar(45) collate Chinese_PRC_CI_AS not null,
    other nvarchar(45)                           not null
)
go

create table child
(
    id    nvarchar(25)                                                 not null
        constraint child_pk
            primary key nonclustered,
    name  nvarchar(45) collate French_CI_AS default 'my default value' not null,
    other nvarchar(45)                                                 not null
)
go

create table parent_has_child
(
    parent_id nvarchar(25) not null
        constraint parent_has_child_parent_id_fk
            references parent,
    child_id  nvarchar(25) not null
        constraint parent_has_child_child_id_fk
            references child,
    constraint parent_has_child_pk
        primary key nonclustered (parent_id, child_id)
)
go

-- exec sp_addextendedproperty 'MS_Description', 'parent_has_child comment', 'SCHEMA', 'test', 'TABLE', 'parent_has_child'
-- go

-- relation to an enum table

create table city_status
(
    id   nvarchar(25)  not null
        constraint city_status_pk
            primary key nonclustered,
    name nvarchar(255) not null
)
go

create unique index city_status_name_uindex
    on city_status (name)
go

INSERT INTO city_status (id, name)
VALUES (N'1', N'MY_CITY_ONE');
INSERT INTO city_status (id, name)
VALUES (N'2', N'MY_city_two_lower');

create table city
(
    id             nvarchar(25) not null
        constraint city_pk
            primary key nonclustered,
    name           nvarchar(45) not null,
    city_type      VARCHAR(255) not null check (city_type in ('small', 'big', 'mega')),
    city_status_id nvarchar(25) not null
        constraint city_city_status_id_fk
            references city_status,
)
go

-- exec sp_addextendedproperty 'MS_Description', 'native enum comment', 'SCHEMA', 'test', 'TABLE', 'city', 'COLUMN', 'city_type'
-- go

--  Logic to handle views

create table order_details
(
    order_number     nvarchar(255)  not null,
    product_code     nvarchar(50)   not null,
    quantity_ordered int            not null,
    price_each       decimal(19, 5) not null,
    constraint order_details_pk
        primary key nonclustered (order_number, product_code)
)
go

create view sale_per_order as
select order_number,
       SUM(quantity_ordered * price_each) total
FROM order_details
GROUP by order_number
go

