CREATE TABLE all_type
(
    my_id               nvarchar(25)      NOT NULL,
    my_uniqueidentifier uniqueidentifier  NOT NULL,
    my_int              int               NOT NULL,
    my_int_null         int,
    my_varchar_50       varchar(50)       NOT NULL,
    my_varchar_50_null  nvarchar(50),
    my_varchar_512      nvarchar(512)     NOT NULL,
--     my_tinytext        tinytext         NOT NULL,
--     my_mediumtext      mediumtext       NOT NULL,
--     my_longtext        longtext         NOT NULL,
    my_text             text              NOT NULL,
    my_char             char(5)           NOT NULL,
    my_date             date              NOT NULL,
    my_datetime         datetime          NOT NULL,
    my_datetimeoffset   datetimeoffset(6) NOT NULL,
    my_timestamp        timestamp         NOT NULL,
    my_time             time              NOT NULL,
--     my_year            year(4)          NOT NULL,
    my_bigint           bigint            NOT NULL,
    my_tinyint          tinyint           NOT NULL,
    my_smallint         smallint          NOT NULL,
--     my_mediumint       mediumint         NOT NULL,
    my_decimal          decimal(19, 5)    NOT NULL,
    my_float            float             NOT NULL,
--     my_double          double            NOT NULL,
--     my_enum_sql        enum('value_one', 'value_two', 'value_three') NOT NULL,
--     my_set             set ('set_one','set_two','set_three')        NOT NULL,
    my_bit              bit               NOT NULL,
    my_bool             tinyint           NOT NULL,
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

-- TODO add more types
-- TODO add foreign key tables, etc
